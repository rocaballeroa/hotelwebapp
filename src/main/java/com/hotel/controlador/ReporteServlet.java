/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel.controlador;

import com.hotel.util.Conexion;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/generarReporte")
public class ReporteServlet extends HttpServlet {

    // Ruta al reporte compilado (desde classpath)
    private static final String REPORTE_PATH = "/reportes/venta_exitosa.jasper";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conexion = null;
        InputStream jasperStream = null;
        
        try {
            // 1. Obtener conexión usando tu clase
            conexion = Conexion.getConexion();
            if (conexion == null || conexion.isClosed()) {
                throw new ServletException("No se pudo establecer conexión a la BD");
            }

            // 2. Cargar el reporte compilado
            jasperStream = getClass().getResourceAsStream(REPORTE_PATH);
            if (jasperStream == null) {
                throw new ServletException("No se encontró el reporte: " + REPORTE_PATH);
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(jasperStream);

            // 3. Preparar parámetros
            HashMap<String, Object> params = new HashMap<>();
            try {
                params.put("idVenta", Integer.parseInt(request.getParameter("idVenta")));
            } catch (NumberFormatException e) {
                throw new ServletException("El parámetro idVenta debe ser un número válido");
            }

            // 4. Generar el PDF
            JasperPrint print = JasperFillManager.fillReport(reporte, params, conexion);
            
            // 5. Configurar respuesta
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=reporte_venta.pdf");
            
            // 6. Enviar el PDF al cliente
            JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());

        } catch (Exception e) {
            // Manejo profesional de errores
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<h3>Error al generar el reporte</h3>");
            response.getWriter().println("<p>" + e.getMessage() + "</p>");
            response.getWriter().println("<a href='javascript:history.back()'>Volver</a>");
            
        } finally {
            // 7. Cerrar recursos en orden inverso a su apertura
            try {
                if (jasperStream != null) jasperStream.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el stream del reporte");
            }
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    System.out.println("✅ Conexión cerrada correctamente");
                }
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión");
            }
        }
    }
}