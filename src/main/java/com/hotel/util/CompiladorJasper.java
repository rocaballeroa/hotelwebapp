/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.util;

import net.sf.jasperreports.engine.*;

import java.io.File;
import java.util.Objects;

public class CompiladorJasper {

    public static void main(String[] args) {
        try {
            // Ruta donde están los archivos .jrxml
            String rutaJRXML = "src/main/resources/reportes/";

            File carpeta = new File(rutaJRXML);
            File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".jrxml"));

            if (archivos == null || archivos.length == 0) {
                System.out.println("⚠️ No se encontraron archivos .jrxml en la carpeta.");
                return;
            }

            for (File archivo : archivos) {
                String jrxmlPath = archivo.getPath();
                String jasperPath = jrxmlPath.replace(".jrxml", ".jasper");

                JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
                System.out.println("✅ Compilado: " + archivo.getName());
            }

            System.out.println("🏁 Todos los reportes fueron compilados correctamente.");
        } catch (JRException e) {
            System.err.println("❌ Error al compilar los reportes:");
            e.printStackTrace();
        }
    }
}
