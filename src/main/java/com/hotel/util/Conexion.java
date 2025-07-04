package com.hotel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Cambia los valores según tu configuración
    private static final String URL = "jdbc:mysql://localhost:3306/DB_HOTEL?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Método que devuelve la conexión
    public static Connection getConexion() {
        Connection conexion = null;
        try {
            // Registrar el driver (opcional desde Java 6, pero por compatibilidad lo incluimos)
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: No se encontró el driver MySQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos");
            e.printStackTrace();
        }
        return conexion;
    }
}
