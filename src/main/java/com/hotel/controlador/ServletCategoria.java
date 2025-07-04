/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel.controlador;

import com.hotel.dao.CategoriaDAO;
import com.hotel.modelo.Categoria;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/CategoriaServlet")
public class ServletCategoria extends HttpServlet {

    private final CategoriaDAO dao = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "listar";

        switch (accion) {
            case "listar":
                List<Categoria> lista = dao.listar();
                request.setAttribute("categorias", lista);
                request.getRequestDispatcher("categoria.jsp").forward(request, response);
                break;

            case "editar":
                int id = Integer.parseInt(request.getParameter("id"));
                Categoria c = dao.obtener(id);
                request.setAttribute("categoria", c);
                request.getRequestDispatcher("editar_categoria.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(idEliminar);
                response.sendRedirect("CategoriaServlet?accion=listar");
                break;
            default:
                response.sendRedirect("CategoriaServlet?accion=listar");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String descripcion = request.getParameter("descripcion");
        boolean estado = request.getParameter("estado") != null;

        Categoria c = new Categoria();
        c.setDescripcion(descripcion);
        c.setEstado(estado);

        if (idStr == null || idStr.isEmpty()) {
            dao.agregar(c); // Registro
        } else {
            int id = Integer.parseInt(idStr);
            c.setIdCategoria(id);
            dao.editar(c); // Edición
        }

        response.sendRedirect("CategoriaServlet?accion=listar");
    }
}
