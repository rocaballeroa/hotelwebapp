/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel.controlador;

import com.hotel.dao.CategoriaDAO;
import com.hotel.dao.EstadoHabitacionDAO;
import com.hotel.dao.HabitacionDAO;
import com.hotel.interfaces.IHabitacionDAO;
import com.hotel.modelo.Categoria;
import com.hotel.modelo.EstadoHabitacion;
import com.hotel.modelo.Habitacion;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/HabitacionServlet")
public class ServletHabitacion extends HttpServlet {

    IHabitacionDAO dao = new HabitacionDAO();

    private void cargarListas(HttpServletRequest request) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        EstadoHabitacionDAO estadoDAO = new EstadoHabitacionDAO();

        List<Categoria> categorias = categoriaDAO.listar();
        List<EstadoHabitacion> estados = estadoDAO.listar();

        request.setAttribute("categorias", categorias);
        request.setAttribute("estados", estados);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "listar";

        switch (accion) {

            case "listar":
                List<Habitacion> habitaciones = dao.listar();
                cargarListas(request);
                request.setAttribute("habitaciones", habitaciones);
                request.getRequestDispatcher("habitacion.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Habitacion habEditar = dao.obtener(idEditar);

                cargarListas(request);
                request.setAttribute("habitacion", habEditar);
                request.getRequestDispatcher("habitacion_form.jsp").forward(request, response);
                break;

            case "nuevo":
                cargarListas(request);
                request.getRequestDispatcher("habitacion_form.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(idEliminar);
                response.sendRedirect("HabitacionServlet?accion=listar");
                break;

            case "filtrar":
    String[] estadosSeleccionados = request.getParameterValues("estadosSeleccionados");
    String categoria = request.getParameter("categoria");
    String precioMaxStr = request.getParameter("precioMax");

    double precioMax = (precioMaxStr != null && !precioMaxStr.isEmpty())
            ? Double.parseDouble(precioMaxStr)
            : Double.MAX_VALUE;

    // Convertir array de String a List<Integer>
    List<Integer> listaEstados = new ArrayList<>();
    if (estadosSeleccionados != null) {
        for (String estadoId : estadosSeleccionados) {
            try {
                listaEstados.add(Integer.parseInt(estadoId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    // Consultar resultados filtrados
    HabitacionDAO habitacionDAOFiltro = new HabitacionDAO();
    List<Habitacion> habitacionesFiltradas = habitacionDAOFiltro.filtrarMultiple(listaEstados, categoria, precioMax);

    // Reenviar los datos
    request.setAttribute("habitaciones", habitacionesFiltradas);
    request.setAttribute("categorias", new CategoriaDAO().listar());
    request.setAttribute("estados", new EstadoHabitacionDAO().listar());
    request.getRequestDispatcher("habitacion.jsp").forward(request, response);
    break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = request.getParameter("idHabitacion") != null && !request.getParameter("idHabitacion").isEmpty()
                ? Integer.parseInt(request.getParameter("idHabitacion")) : 0;

        Habitacion h = new Habitacion();
        h.setNumero(request.getParameter("numero"));
        h.setDetalle(request.getParameter("detalle"));
        h.setPrecio(Double.parseDouble(request.getParameter("precio")));
        h.setIdEstadoHabitacion(Integer.parseInt(request.getParameter("estadoHabitacion")));
        h.setIdPiso(Integer.parseInt(request.getParameter("piso")));
        h.setIdCategoria(Integer.parseInt(request.getParameter("categoria")));
        h.setImagen(request.getParameter("imagen"));
        h.setEstado(Boolean.parseBoolean(request.getParameter("estado")));

        if (id > 0) {
            h.setIdHabitacion(id);
            dao.editar(h);
        } else {
            dao.agregar(h);
        }

        response.sendRedirect("HabitacionServlet?accion=listar");
    }
}
