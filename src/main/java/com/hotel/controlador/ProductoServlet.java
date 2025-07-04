/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel.controlador;

import com.hotel.dao.ProductoDAO;
import com.hotel.interfaces.IProductoDAO;
import com.hotel.modelo.Producto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {

    IProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "listar";

        switch (accion) {
            case "listar":
                List<Producto> productos = dao.listar();
                request.setAttribute("productos", productos);
                request.getRequestDispatcher("producto.jsp").forward(request, response);
                break;

            case "nuevo":
                request.getRequestDispatcher("producto_form.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Producto p = dao.obtener(idEditar);
                request.setAttribute("producto", p);
                request.getRequestDispatcher("producto_form.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(idEliminar);
                response.sendRedirect("ProductoServlet?accion=listar");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = request.getParameter("idProducto") != null && !request.getParameter("idProducto").isEmpty()
                ? Integer.parseInt(request.getParameter("idProducto")) : 0;

        Producto p = new Producto();
        p.setNombre(request.getParameter("nombre"));
        p.setDetalle(request.getParameter("detalle"));
        p.setPrecio(Double.parseDouble(request.getParameter("precio")));
        p.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
        p.setEstado(Boolean.parseBoolean(request.getParameter("estado")));

        if (id > 0) {
            p.setIdProducto(id);
            dao.editar(p);
        } else {
            dao.agregar(p);
        }

        response.sendRedirect("ProductoServlet?accion=listar");
    }
}
