/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel.controlador;

import com.hotel.dao.EstadoReservaDAO;
import com.hotel.dao.RecepcionDAO;
import com.hotel.dao.HabitacionDAO;
import com.hotel.dao.UsuarioDAO;
import com.hotel.modelo.EstadoReserva;
import com.hotel.modelo.Habitacion;
import com.hotel.modelo.Recepcion;
import com.hotel.modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/RecepcionServlet")
public class RecepcionServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final HabitacionDAO habitacionDAO = new HabitacionDAO();
    private final RecepcionDAO recepcionDAO = new RecepcionDAO();  // CORREGIDO: nombre coherente

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null) {
            response.sendRedirect("login.jsp?mensaje=SesionCaducada");
            return;
        }

        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "listar";

        switch (accion) {

            case "nuevo":
                List<Usuario> clientes = usuarioDAO.listarPorTipo(3);
                List<Habitacion> habitaciones = habitacionDAO.listarDisponibles();
                EstadoReservaDAO estadoReservaDAO = new EstadoReservaDAO();
                List<EstadoReserva> estadosReserva = estadoReservaDAO.listar();
                request.setAttribute("clientes", clientes);
                request.setAttribute("habitaciones", habitaciones);
                request.setAttribute("estadosReserva", estadosReserva);
                request.getRequestDispatcher("recepcion_form.jsp").forward(request, response);
                break;

            case "listar":
                List<Recepcion> recepciones = recepcionDAO.listar();
                request.setAttribute("recepciones", recepciones);
                request.getRequestDispatcher("recepcion_listar.jsp").forward(request, response);
                break;

            case "detalle":
                int idDetalle = Integer.parseInt(request.getParameter("id"));
                Recepcion detalle = recepcionDAO.obtener(idDetalle);
                request.setAttribute("recepcion", detalle);
                request.getRequestDispatcher("recepcion_detalle.jsp").forward(request, response);
                break;

            case "anular":
                int idRecepcion = Integer.parseInt(request.getParameter("id"));
                Recepcion recepcion = recepcionDAO.obtener(idRecepcion);
                if (recepcion != null) {
                    recepcionDAO.anular(idRecepcion);
                    habitacionDAO.actualizarEstado(recepcion.getIdHabitacion(), 1); // DISPONIBLE
                    redirigirPorRol(usuario, response, "anular");
                } else {
                    response.sendRedirect("RecepcionServlet?accion=listar&error=noEncontrado");
                }
                break;

            case "checkout":
                try {
                    int idRecep = Integer.parseInt(request.getParameter("id"));
                    Recepcion r = recepcionDAO.obtener(idRecep);
                    if (r != null) {
                        recepcionDAO.actualizarEstadoReserva(idRecep, 4); // FINALIZADA
                        recepcionDAO.actualizarEstado(idRecep, false);    // Estado false (inactiva)
                        habitacionDAO.actualizarEstado(r.getIdHabitacion(), 1); // DISPONIBLE
                        redirigirPorRol(usuario, response, "checkout", idRecep);
                    } else {
                        response.sendRedirect("RecepcionServlet?accion=listar");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    response.sendRedirect("RecepcionServlet?accion=listar");
                }
                break;

            case "reactivar":
                int idReactivar = Integer.parseInt(request.getParameter("id"));
                Recepcion rReactivar = recepcionDAO.obtener(idReactivar);
                if (rReactivar != null && rReactivar.getIdEstadoReserva() != 3) {
                    recepcionDAO.reactivar(idReactivar, rReactivar.getIdHabitacion());
                }
                response.sendRedirect("RecepcionServlet?accion=listar");
                break;

            case "empleado": {
    String estado = request.getParameter("estado");
    String cliente = request.getParameter("cliente");
    String orden = request.getParameter("orden");

    List<Recepcion> recepcionesEmpleado;
    boolean hayFiltros = (estado != null && !estado.isEmpty())
                        || (cliente != null && !cliente.isEmpty())
                        || (orden != null && !orden.isEmpty());

    if (hayFiltros) {
        recepcionesEmpleado = recepcionDAO.filtrarRecepciones(estado, cliente, orden);
    } else {
        recepcionesEmpleado = recepcionDAO.listar();
    }

    request.setAttribute("recepciones", recepcionesEmpleado);
    request.getRequestDispatcher("recepcion_listar_empleado.jsp").forward(request, response);
    break;
}


            case "solicitar":
                if (usuario.getIdTipoPersona() == 3) {
                    List<Habitacion> disponibles = habitacionDAO.listarDisponibles();
                    request.setAttribute("habitaciones", disponibles);
                    request.getRequestDispatcher("solicitar_habitacion.jsp").forward(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
                break;

            case "verReservas":
                if (usuario.getIdTipoPersona() == 3) {
                    List<Recepcion> reservasCliente = recepcionDAO.listarPorCliente(usuario.getIdPersona());
                    request.setAttribute("reservas", reservasCliente);
                    request.getRequestDispatcher("reservas_cliente.jsp").forward(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
                break;

            case "filtrar":
                String estado = request.getParameter("estado");
                String cliente = request.getParameter("cliente");
                String orden = request.getParameter("orden");

                List<Recepcion> filtradas = recepcionDAO.filtrarRecepciones(estado, cliente, orden);

                request.setAttribute("recepciones", filtradas);
                request.getRequestDispatcher("recepcion_listar.jsp").forward(request, response);
                break;
            
        }
    }

    private void redirigirPorRol(Usuario usuario, HttpServletResponse response, String tipo) throws IOException {
        redirigirPorRol(usuario, response, tipo, -1);
    }

    private void redirigirPorRol(Usuario usuario, HttpServletResponse response, String tipo, int idRecep) throws IOException {
        if (usuario.getIdTipoPersona() == 3) {
            if ("checkout".equals(tipo)) {
                response.sendRedirect("VentaServlet?accion=preparar&idRecepcion=" + idRecep);
            } else {
                response.sendRedirect("RecepcionServlet?accion=verReservas&success=" + tipo);
            }
        } else if (usuario.getIdTipoPersona() == 2) {
            response.sendRedirect("RecepcionServlet?accion=empleado");
        } else {
            response.sendRedirect("RecepcionServlet?accion=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("guardar".equals(accion)) {
            HttpSession session = request.getSession(true);
            Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

            if (usuario == null || (usuario.getIdTipoPersona() < 1 || usuario.getIdTipoPersona() > 3)) {
                response.sendRedirect("login.jsp");
                return;
            }

            try {
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                int idHabitacion = Integer.parseInt(request.getParameter("idHabitacion"));
                String fechaSalidaStr = request.getParameter("fechaSalida");
                double precioInicial = Double.parseDouble(request.getParameter("precioInicial"));
                double adelanto = Double.parseDouble(request.getParameter("adelanto"));

                if (adelanto > precioInicial) {
                    List<Habitacion> habitaciones = habitacionDAO.listarDisponibles();
                    request.setAttribute("habitaciones", habitaciones);
                    request.setAttribute("error", "El adelanto no puede ser mayor al precio.");
                    request.getRequestDispatcher("solicitar_habitacion.jsp").forward(request, response);
                    return;
                }

                double restante = precioInicial - adelanto;
                double totalPagado = adelanto;

                Timestamp fechaSalida = Timestamp.valueOf(fechaSalidaStr.replace("T", " ") + ":00");

                Recepcion r = new Recepcion();
                r.setIdCliente(idCliente);
                r.setIdHabitacion(idHabitacion);
                r.setFechaSalida(fechaSalida);
                r.setPrecioInicial(precioInicial);
                r.setAdelanto(adelanto);
                r.setPrecioRestante(restante);
                r.setTotalPagado(totalPagado);
                r.setEstado(true);
                r.setIdEstadoReserva(3); // ACTIVA

                recepcionDAO.agregar(r);
                habitacionDAO.actualizarEstado(idHabitacion, 2); // OCUPADO

                if (usuario.getIdTipoPersona() == 3) {
                    response.sendRedirect("cliente.jsp?reserva=ok");
                } else if (usuario.getIdTipoPersona() == 2) {
                    response.sendRedirect("RecepcionServlet?accion=empleado");
                } else {
                    response.sendRedirect("RecepcionServlet?accion=listar");
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error al procesar la recepción.");
                request.getRequestDispatcher("solicitar_habitacion.jsp").forward(request, response);
            }

        } else {
            response.sendRedirect("solicitar_habitacion.jsp");
        }
    }
}
