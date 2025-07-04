/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel.controlador;

import com.hotel.dao.UsuarioDAO;
import com.hotel.modelo.Usuario;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {

    UsuarioDAO dao = new UsuarioDAO();

  @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(false);
    Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

    // Validación: solo empleados (2) y admins (1) pueden ver esta lista
    if (usuario == null || (usuario.getIdTipoPersona() != 1 && usuario.getIdTipoPersona() != 2)) {
        response.sendRedirect("login.jsp?mensaje=No autorizado");
        return;
    }

    List<Usuario> clientes = dao.listarClientes();
    request.setAttribute("clientes", clientes);
    request.getRequestDispatcher("clientes.jsp").forward(request, response);
}

}
