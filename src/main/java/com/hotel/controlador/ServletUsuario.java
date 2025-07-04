/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel.controlador;

import com.hotel.dao.UsuarioDAO;
import com.hotel.interfaces.IUsuarioDAO;
import com.hotel.modelo.Usuario;
import java.io.File;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;


@MultipartConfig
@WebServlet("/UsuarioServlet")
public class ServletUsuario extends HttpServlet {

    IUsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "listar";

        switch (accion) {
    case "listar":
        List<Usuario> lista = dao.listar();
        request.setAttribute("usuarios", lista);
        request.getRequestDispatcher("usuario.jsp").forward(request, response);
        break;
    case "editar":
    int idEditar = Integer.parseInt(request.getParameter("id"));
    Usuario usuarioEditar = dao.obtener(idEditar);


    request.setAttribute("usuario", usuarioEditar);
    request.setAttribute("modo", "edicion"); // ESTO ES IMPORTANTE
    request.setAttribute("esCliente", usuarioEditar.getIdTipoPersona() == 3); // si aplica
     request.setAttribute("esEmpleado", usuarioEditar.getIdTipoPersona() == 2); // <-- agrega aquí
    request.getRequestDispatcher("usuario_form.jsp").forward(request, response);
    break;


   case "nuevo":
    String esClienteParam = request.getParameter("esCliente");
    boolean esCliente = esClienteParam != null && esClienteParam.equals("true");
    
    // Asegura que el formulario no reciba un objeto usuario por error
    request.setAttribute("usuario", null);
    request.setAttribute("esCliente", esCliente);
    request.setAttribute("modo", "registro"); // opcional pero útil
    request.getRequestDispatcher("usuario_form.jsp").forward(request, response);
    break;



    case "eliminar":
        int idEliminar = Integer.parseInt(request.getParameter("id"));
        dao.eliminar(idEliminar);
        response.sendRedirect("UsuarioServlet?accion=listar");
        break;
        
        case "editarPerfil":
    HttpSession sesion = request.getSession();
    Usuario clienteLogueado = (Usuario) sesion.getAttribute("usuario");

    if (clienteLogueado != null && clienteLogueado.getIdTipoPersona() == 3) {
        Usuario cliente = dao.obtener(clienteLogueado.getIdPersona());
        request.setAttribute("usuario", cliente);
        request.setAttribute("esCliente", true);
        request.getRequestDispatcher("perfilCliente.jsp").forward(request, response);
    } else {
        response.sendRedirect("login.jsp");
    }
    break;

case "guardarPerfil":
    doPost(request, response);
    break;


}

    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    int id = request.getParameter("idPersona") != null && !request.getParameter("idPersona").isEmpty()
            ? Integer.parseInt(request.getParameter("idPersona")) : 0;

    Usuario u = new Usuario();
    u.setTipoDocumento(request.getParameter("tipoDocumento"));
    u.setDocumento(request.getParameter("documento"));
    u.setNombre(request.getParameter("nombre"));
    u.setApellido(request.getParameter("apellido"));
    u.setCorreo(request.getParameter("correo"));
    u.setClave(request.getParameter("clave"));
    u.setIdTipoPersona(Integer.parseInt(request.getParameter("tipoPersona")));
    u.setEstado(Boolean.parseBoolean(request.getParameter("estado")));

    // Procesar imagen (si se envió)
    Part filePart = request.getPart("imagen");
    if (filePart != null && filePart.getSize() > 0) {
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Ruta física hacia WebContent o WebPages (dependiendo del IDE)
        String uploadPath = getServletContext().getRealPath("/imagenes/perfiles");
        System.out.println("Ruta física de imagen: " + uploadPath);

        File uploadDir = new File(uploadPath);
if (!uploadDir.exists()) {
    uploadDir.mkdirs(); // Crea la carpeta si no existe
}

// Guarda la imagen en la ruta física
String fullPath = uploadPath + File.separator + fileName;
filePart.write(fullPath);

// Guarda la ruta relativa (accesible desde navegador) en la base de datos
u.setImagen("imagenes/perfiles/" + fileName);

    } else {
        // En edición, conservar imagen actual si no se sube una nueva
        if (id > 0) {
            Usuario existente = dao.obtener(id);
            if (existente != null) {
                u.setImagen(existente.getImagen());
            }
        }
    }

// Guardar en BD
if (id > 0) {
    u.setIdPersona(id);
    dao.editar(u);

    // 🔄 Si el usuario editado es el mismo en sesión, actualizar la sesión
    HttpSession session = request.getSession();
    Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
    if (usuarioSesion != null && usuarioSesion.getIdPersona() == u.getIdPersona()) {
        session.setAttribute("usuario", dao.obtener(u.getIdPersona()));
    }

    // 🔁 Redireccionar según tipo de usuario
    int tipoUsuario = usuarioSesion != null ? usuarioSesion.getIdTipoPersona() : u.getIdTipoPersona();
    switch (tipoUsuario) {
        case 1:
            response.sendRedirect("UsuarioServlet?accion=listar");
            break;
        case 2:
            response.sendRedirect("empleado.jsp");
            break;
        case 3:
            response.sendRedirect("cliente.jsp"); // si no tienes, puedes dejarlo en login
            break;
        default:
            response.sendRedirect("login.jsp");
            break;
    }

} else {
    dao.agregar(u);
if (u.getIdTipoPersona() == 3) {
    // Obtener sesión sin crear una nueva
    HttpSession session = request.getSession(false);
    Usuario usuarioSesion = null;

    if (session != null) {
        Object obj = session.getAttribute("usuario");
        if (obj instanceof Usuario) {
            usuarioSesion = (Usuario) obj;
        }
    }

    // Verificar si quien está registrando es un empleado
    if (usuarioSesion != null && usuarioSesion.getIdTipoPersona() == 2) {
        response.sendRedirect("ClienteServlet"); // Empleado registró un cliente, redirige a lista
    } else {
        // Cliente se registró por sí mismo
        response.sendRedirect("login.jsp?mensaje=Registro exitoso, ahora inicia sesión");
    }



} else {
    response.sendRedirect("UsuarioServlet?accion=listar");
}

}
}}