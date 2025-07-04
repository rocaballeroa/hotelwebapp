/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel.controlador;

import com.hotel.dao.ConsumoProductoDAO;
import com.hotel.dao.ProductoDAO;
import com.hotel.dao.RecepcionDAO;
import com.hotel.interfaces.IConsumoProductoDAO;
import com.hotel.interfaces.IProductoDAO;
import com.hotel.interfaces.IRecepcionDAO;
import com.hotel.modelo.ConsumoProducto;
import com.hotel.modelo.Producto;
import com.hotel.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ConsumoProductoServlet")
public class ServletConsumoProducto extends HttpServlet {

    IConsumoProductoDAO consumoDAO = new ConsumoProductoDAO();
    IProductoDAO productoDAO = new ProductoDAO();
    IRecepcionDAO recepcionDAO = new RecepcionDAO();

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // 🔐 Validar sesión
    HttpSession session = request.getSession(false);
    Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

    if (usuario == null || (usuario.getIdTipoPersona() != 1 && usuario.getIdTipoPersona() != 2 && usuario.getIdTipoPersona() != 3)) {
        response.sendRedirect("login.jsp");
        return;
    }

    // 📌 Variables de control y depuración
    String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "listar";
    String idHabitacionParam = request.getParameter("idHabitacion");
    String origen = request.getParameter("origen");

    System.out.println("🟡 Acción: " + accion);
    System.out.println("🟡 Origen: " + origen);
    System.out.println("🟡 Usuario en sesión: " + usuario.getNombre());
    System.out.println("🟡 Tipo de persona: " + usuario.getIdTipoPersona());
    System.out.println("🟡 ID Habitación: " + idHabitacionParam);

    try {
        switch (accion) {
            case "listar":
            case "nuevo":
            case "eliminar":
            case "ver": {

                if (idHabitacionParam == null || idHabitacionParam.isEmpty()) {
                    throw new IllegalArgumentException("ID de habitación inválido");
                }

                int idHabitacion = Integer.parseInt(idHabitacionParam);
                request.setAttribute("origen", origen);
                request.setAttribute("idHabitacion", idHabitacion);

                if ("listar".equals(accion)) {
                    List<ConsumoProducto> consumos = consumoDAO.listarPorHabitacion(idHabitacion);
                    request.setAttribute("consumos", consumos);

                    if (usuario.getIdTipoPersona() == 3) {
                        request.getRequestDispatcher("consumo_cliente.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("consumo_producto.jsp").forward(request, response);
                    }

                } else if ("nuevo".equals(accion)) {
                    List<Producto> productos = productoDAO.listar();
                    request.setAttribute("productos", productos);

                    // 🟢 Obtener ID de Recepción con control
                    System.out.println("🔍 Buscando ID de recepción para habitación: " + idHabitacion);
                    int idRecepcion = recepcionDAO.obtenerIdRecepcionPorHabitacion(idHabitacion);
                    System.out.println("✅ ID de recepción encontrado: " + idRecepcion);

                    request.setAttribute("idRecepcion", idRecepcion);
                    request.getRequestDispatcher("consumo_producto_form.jsp").forward(request, response);

                } else if ("eliminar".equals(accion)) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    consumoDAO.eliminar(id);
                    response.sendRedirect("ConsumoProductoServlet?accion=listar&idHabitacion=" + idHabitacion + "&origen=" + origen);

     } else if ("ver".equals(accion)) {
    List<ConsumoProducto> misConsumos = consumoDAO.listarPorHabitacion(idHabitacion);
    request.setAttribute("consumos", misConsumos);
    request.setAttribute("idHabitacion", idHabitacion); // ✅ FALTA ESTO
    request.setAttribute("origen", origen);              // ✅ FALTA ESTO
    request.getRequestDispatcher("consumo_cliente.jsp").forward(request, response);
}

                break;
            }

            case "cancelar": {
                System.out.println("🟠 Cancelar desde origen: " + origen);
                if ("empleado".equals(origen)) {
                    response.sendRedirect("RecepcionServlet?accion=empleado");
                } else if ("cliente".equals(origen)) {
                    response.sendRedirect("RecepcionServlet?accion=verReservas");
                } else {
                    response.sendRedirect("RecepcionServlet?accion=listar");
                }
                break;
            }

            case "volver": {
                if ("empleado".equals(origen) && usuario.getIdTipoPersona() == 2) {
                    response.sendRedirect("RecepcionServlet?accion=empleado");
                } else if ("cliente".equals(origen)) {
                    response.sendRedirect("RecepcionServlet?accion=verReservas");
                } else {
                    response.sendRedirect("RecepcionServlet?accion=listar");
                }
                break;
            }
case "verTodo": {
    HttpSession sesion = request.getSession(false);
    Usuario usuarioSesion = (sesion != null) ? (Usuario) sesion.getAttribute("usuario") : null;

    if (usuarioSesion == null || usuarioSesion.getIdTipoPersona() != 3) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<ConsumoProducto> consumos = consumoDAO.listarPorCliente(usuarioSesion.getIdPersona());
    request.setAttribute("consumos", consumos);
    request.getRequestDispatcher("consumo_cliente_total.jsp").forward(request, response);
    break;
}


            default:
                response.sendRedirect("error.jsp");
                break;
        }

    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("error.jsp");
    }
}


    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // 🔒 Validar sesión existente, sin crear nueva
    HttpSession session = request.getSession(false);
    Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

    if (usuario == null || (usuario.getIdTipoPersona() != 1 && usuario.getIdTipoPersona() != 2 && usuario.getIdTipoPersona() != 3)) {
        response.sendRedirect("login.jsp");
        return;
    }

    try {
        int idHabitacion = Integer.parseInt(request.getParameter("idHabitacion"));
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        String origen = request.getParameter("origen");

        // Verificación visual en consola
        System.out.println("Usuario en sesión: " + usuario.getNombre());
        System.out.println("Tipo de persona: " + usuario.getIdTipoPersona());
        System.out.println("Origen: " + origen);

        Producto producto = productoDAO.obtener(idProducto);

        if (producto.getCantidad() < cantidad) {
            request.setAttribute("error", "No hay suficiente stock del producto.");
            request.setAttribute("idHabitacion", idHabitacion);
            request.setAttribute("productos", productoDAO.listar());
            request.setAttribute("origen", origen);
            request.getRequestDispatcher("consumo_producto_form.jsp").forward(request, response);
            return;
        }

        int idRecepcion = recepcionDAO.obtenerIdRecepcionPorHabitacion(idHabitacion);

        ConsumoProducto consumo = new ConsumoProducto();
        consumo.setIdHabitacion(idHabitacion);
        consumo.setIdProducto(idProducto);
        consumo.setCantidad(cantidad);
        consumo.setEstado(true);
        consumo.setIdCliente(usuario.getIdPersona());
        consumo.setPrecio(producto.getPrecio());
        consumo.setIdRecepcion(idRecepcion);

        consumoDAO.agregar(consumo);
        productoDAO.actualizarCantidad(idProducto, producto.getCantidad() - cantidad);

        // 🔁 Redirige según el origen
        if ("cliente".equals(origen)) {
    response.sendRedirect("RecepcionServlet?accion=verReservas&consumo=ok");
} else if ("empleado".equals(origen)) {
    response.sendRedirect("RecepcionServlet?accion=empleado&consumo=ok");
} else {
    response.sendRedirect("RecepcionServlet?accion=listar&consumo=ok");
}

    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("error.jsp");
    }
}

}
