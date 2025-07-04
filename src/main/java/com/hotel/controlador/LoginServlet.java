/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel.controlador;

import com.hotel.dao.UsuarioDAO;
import com.hotel.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");

        Usuario usuario = dao.validar(correo, clave);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);


            // Puedes redirigir a un menú según el tipo de usuario
            switch (usuario.getIdTipoPersona()) {
                case 1 -> response.sendRedirect("admin.jsp");
                case 2 -> response.sendRedirect("empleado.jsp");
                case 3 -> response.sendRedirect("cliente.jsp");
                default -> response.sendRedirect("login.jsp");
            }


        } else {
            request.setAttribute("error", "Correo o clave incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
