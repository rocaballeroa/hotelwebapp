/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.reporte;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JRException;

public class CompiladorReporte {
    public static void main(String[] args) {
        try {
            // Ruta al archivo .jrxml
            String jrxmlPath = "src/main/resources/reportes/venta_exitosa.jrxml";

            // Ruta donde se guardará el .jasper compilado
            String jasperPath = "src/main/resources/reportes/venta_exitosa.jasper";

            // Compila el archivo .jrxml en .jasper
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);

            System.out.println("✅ Reporte compilado exitosamente en: " + jasperPath);
        } catch (JRException e) {
            System.err.println("❌ Error al compilar el reporte:");
            e.printStackTrace();
        }
    }
}
