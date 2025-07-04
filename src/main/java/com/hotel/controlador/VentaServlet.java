/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel.controlador;

import com.hotel.dao.ConsumoProductoDAO;
import com.hotel.dao.ProductoDAO;
import com.hotel.dao.RecepcionDAO;
import com.hotel.dao.VentaDAO;
import com.hotel.interfaces.IConsumoProductoDAO;
import com.hotel.interfaces.IProductoDAO;
import com.hotel.interfaces.IVentaDAO;
import com.hotel.modelo.ConsumoProducto;
import com.hotel.modelo.DetalleVenta;
import com.hotel.modelo.Producto;
import com.hotel.modelo.Recepcion;
import com.hotel.modelo.Venta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/VentaServlet")
public class VentaServlet extends HttpServlet {

    private final IVentaDAO ventaDAO = new VentaDAO();
    private final IConsumoProductoDAO consumoDAO = new ConsumoProductoDAO();
    private final IProductoDAO productoDAO = new ProductoDAO();
    private final RecepcionDAO recepcionDAO = new RecepcionDAO();

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String accion = request.getParameter("accion");

    if ("preparar".equals(accion)) {
        try {
            int idRecepcion = Integer.parseInt(request.getParameter("idRecepcion"));

            // Verificar si ya existe venta
            if (ventaDAO.existeVentaPorRecepcion(idRecepcion)) {
                request.setAttribute("mensaje", "Ya se ha registrado una venta para esta recepción.");
                request.getRequestDispatcher("venta_ya_registrada.jsp").forward(request, response);
                return;
            }

            Recepcion recepcion = recepcionDAO.obtener(idRecepcion);
            double precioHabitacion = recepcion.getPrecioRestante();

            List<ConsumoProducto> consumos = consumoDAO.listarPorRecepcion(idRecepcion);
            List<DetalleVenta> detalles = new ArrayList<>();
            double total = 0;

            for (ConsumoProducto c : consumos) {
                Producto producto = productoDAO.obtener(c.getIdProducto());

                DetalleVenta d = new DetalleVenta();
                d.setIdProducto(c.getIdProducto());
                d.setCantidad(c.getCantidad());
                d.setNombreProducto(producto.getNombre());
                d.setPrecioUnitario(producto.getPrecio());
                d.setSubTotal(c.getCantidad() * producto.getPrecio());

                detalles.add(d);
                total += d.getSubTotal();
            }

            // Agregar hospedaje como consumo
            DetalleVenta detalleHabitacion = new DetalleVenta();
            detalleHabitacion.setIdProducto(1000); // ID del producto "Hospedaje"
            detalleHabitacion.setCantidad(1);
            detalleHabitacion.setNombreProducto("Habitación " + recepcion.getNumeroHabitacion());
            detalleHabitacion.setPrecioUnitario(precioHabitacion);
            detalleHabitacion.setSubTotal(precioHabitacion);

            detalles.add(detalleHabitacion);
            total += precioHabitacion;

            Venta venta = new Venta();
            venta.setIdRecepcion(idRecepcion);
            venta.setTotal(total);
            venta.setEstado("PENDIENTE");
            venta.setDetalles(detalles);

            request.setAttribute("venta", venta);
            request.setAttribute("detalles", detalles);
            request.setAttribute("recepcion", recepcion);
            request.getRequestDispatcher("vista_previa_venta.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al preparar la venta");
            request.getRequestDispatcher("venta_error.jsp").forward(request, response);
        }
    }
}

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String accion = request.getParameter("accion");

    if ("confirmar".equals(accion)) {
        try {
            int idRecepcion = Integer.parseInt(request.getParameter("idRecepcion"));

            // Recuperar datos
            Recepcion recepcion = recepcionDAO.obtener(idRecepcion);
            double precioHabitacion = recepcion.getPrecioRestante();
            List<ConsumoProducto> consumos = consumoDAO.listarPorRecepcion(idRecepcion);

            List<DetalleVenta> detalles = new ArrayList<>();
            double total = 0;

            // Detalles de productos consumidos
            for (ConsumoProducto c : consumos) {
                Producto producto = productoDAO.obtener(c.getIdProducto());

                DetalleVenta d = new DetalleVenta();
                d.setIdProducto(c.getIdProducto());
                d.setCantidad(c.getCantidad());
                d.setNombreProducto(producto.getNombre());
                d.setPrecioUnitario(producto.getPrecio());
                d.setSubTotal(c.getCantidad() * producto.getPrecio());

                detalles.add(d);
                total += d.getSubTotal();
            }

            // Agregar habitación como producto
            DetalleVenta detalleHabitacion = new DetalleVenta();
            detalleHabitacion.setIdProducto(1000); // ID fijo para "Hospedaje"
            detalleHabitacion.setCantidad(1);
            detalleHabitacion.setNombreProducto("Habitación " + recepcion.getNumeroHabitacion());
            detalleHabitacion.setPrecioUnitario(precioHabitacion);
            detalleHabitacion.setSubTotal(precioHabitacion);
            detalles.add(detalleHabitacion);
            total += precioHabitacion;

            // Crear venta
            Venta venta = new Venta();
            venta.setIdRecepcion(idRecepcion);
            venta.setTotal(total);
            venta.setEstado("COMPLETADA");
            venta.setDetalles(detalles);

            // Guardar venta
            boolean exito = ventaDAO.registrarVenta(venta);

            if (exito) {
                // ✅ Cambiar estado habitación (checkout)
                recepcionDAO.checkout(idRecepcion, recepcion.getIdHabitacion());

                // 🧹 Limpiar consumos procesados
                consumoDAO.eliminarPorRecepcion(idRecepcion);

                // Enviar datos a vista exitosa
                request.setAttribute("venta", venta);
                request.setAttribute("detalles", detalles);
                request.getRequestDispatcher("venta_exitosa.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "No se pudo confirmar la venta");
                request.getRequestDispatcher("venta_error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al confirmar la venta");
            request.getRequestDispatcher("venta_error.jsp").forward(request, response);
        }
    }
}

}
