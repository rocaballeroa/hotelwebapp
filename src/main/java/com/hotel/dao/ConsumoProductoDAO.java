/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.dao;

import com.hotel.util.Conexion;
import com.hotel.interfaces.IConsumoProductoDAO;
import com.hotel.modelo.ConsumoProducto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsumoProductoDAO implements IConsumoProductoDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // ✅ Listar consumos por habitación con JOIN a producto
    @Override
    public List<ConsumoProducto> listarPorHabitacion(int idHabitacion) {
        List<ConsumoProducto> lista = new ArrayList<>();
        String sql = "SELECT cp.*, p.Nombre AS nombreProducto, p.Precio " +
                     "FROM CONSUMO_PRODUCTO cp " +
                     "JOIN PRODUCTO p ON cp.IdProducto = p.IdProducto " +
                     "WHERE cp.IdHabitacion = ?";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idHabitacion);
            rs = ps.executeQuery();

            while (rs.next()) {
                ConsumoProducto cp = new ConsumoProducto();
                cp.setIdConsumo(rs.getInt("IdConsumo"));
                cp.setIdHabitacion(rs.getInt("IdHabitacion"));
                cp.setIdProducto(rs.getInt("IdProducto"));
                cp.setCantidad(rs.getInt("Cantidad"));
                cp.setFechaConsumo(rs.getTimestamp("FechaConsumo"));
                cp.setEstado(rs.getBoolean("Estado"));
                cp.setNombreProducto(rs.getString("nombreProducto"));
                cp.setPrecio(rs.getDouble("Precio"));
                cp.setTotal(cp.getCantidad() * cp.getPrecio());
                lista.add(cp);
            }

        } catch (Exception e) {
            System.err.println("Error al listar consumos por habitación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                System.err.println("Error al cerrar recursos: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return lista;
    }

    // ✅ Agregar un nuevo consumo
   @Override
public boolean agregar(ConsumoProducto consumo) {
    String sql = "INSERT INTO CONSUMO_PRODUCTO (IdHabitacion, IdProducto, Cantidad, Precio, FechaConsumo, Estado, IdCliente, IdRecepcion) VALUES (?, ?, ?, ?, NOW(), ?, ?, ?)";
    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        ps.setInt(1, consumo.getIdHabitacion());
        ps.setInt(2, consumo.getIdProducto());
        ps.setInt(3, consumo.getCantidad());
        ps.setDouble(4, consumo.getPrecio()); // ✅ Asegúrate que se esté seteando antes
        ps.setBoolean(5, consumo.isEstado());
        ps.setInt(6, consumo.getIdCliente());

        if (consumo.getIdRecepcion() > 0) {
            ps.setInt(7, consumo.getIdRecepcion());
        } else {
            ps.setNull(7, java.sql.Types.INTEGER);
        }

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        System.err.println("❌ Error al agregar consumo: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    return false;
}


    // ✅ Eliminar un consumo por ID
    @Override
    public boolean eliminar(int idConsumo) {
        String sql = "DELETE FROM CONSUMO_PRODUCTO WHERE IdConsumo = ?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idConsumo);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error al eliminar consumo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                System.err.println("Error al cerrar recursos: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return false;
    }
    
    @Override
public List<ConsumoProducto> listarPorCliente(int idPersona) {
    List<ConsumoProducto> lista = new ArrayList<>();
    String sql = "SELECT c.*, p.Nombre AS nombreProducto, p.Precio, (p.Precio * c.Cantidad) AS total \n" +
"FROM CONSUMO_PRODUCTO c \n" +
"JOIN PRODUCTO p ON c.IdProducto = p.IdProducto \n" +
"WHERE c.IdCliente = ? AND c.Estado = 1";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idPersona);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ConsumoProducto c = new ConsumoProducto();
            c.setIdConsumo(rs.getInt("IdConsumo"));
            c.setIdHabitacion(rs.getInt("IdHabitacion"));
            c.setIdProducto(rs.getInt("IdProducto"));
            c.setCantidad(rs.getInt("Cantidad"));
            c.setNombreProducto(rs.getString("nombreProducto"));
            c.setPrecio(rs.getDouble("Precio"));
            c.setTotal(rs.getDouble("total"));
            c.setEstado(rs.getBoolean("Estado"));
            c.setFechaConsumo(rs.getTimestamp("FechaConsumo"));
            lista.add(c);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}

@Override
public List<ConsumoProducto> listarPorRecepcion(int idRecepcion) {
    List<ConsumoProducto> lista = new ArrayList<>();
    String sql = "SELECT * FROM CONSUMO_PRODUCTO WHERE IdRecepcion = ?";
    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idRecepcion);
        rs = ps.executeQuery();

        while (rs.next()) {
            ConsumoProducto cp = new ConsumoProducto();
            cp.setIdConsumo(rs.getInt("IdConsumo"));
            cp.setIdProducto(rs.getInt("IdProducto"));
            cp.setCantidad(rs.getInt("Cantidad"));
            cp.setPrecio(rs.getDouble("Precio"));
            cp.setIdRecepcion(rs.getInt("IdRecepcion")); // ← Asegúrate que lo tengas
            lista.add(cp);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    return lista;
}

@Override
public boolean eliminarPorRecepcion(int idRecepcion) {
    String sql = "DELETE FROM CONSUMO_PRODUCTO WHERE IdRecepcion = ?";
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idRecepcion);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

@Override
public List<ConsumoProducto> listarTodo() {
    List<ConsumoProducto> lista = new ArrayList<>();
    String sql = "SELECT c.*, p.Nombre AS nombreProducto, (p.Precio * c.Cantidad) AS total " +
                 "FROM CONSUMO_PRODUCTO c " +
                 "JOIN PRODUCTO p ON c.IdProducto = p.IdProducto";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            ConsumoProducto c = new ConsumoProducto();
            c.setIdConsumo(rs.getInt("IdConsumo"));
            c.setIdHabitacion(rs.getInt("IdHabitacion"));
            c.setNombreProducto(rs.getString("nombreProducto"));
            c.setCantidad(rs.getInt("Cantidad"));
            c.setPrecio(rs.getDouble("total") / rs.getInt("Cantidad"));
            c.setTotal(rs.getDouble("total"));
            c.setFechaConsumo(rs.getTimestamp("FechaConsumo"));
            lista.add(c);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}

@Override
public List<ConsumoProducto> listarHoy() {
    List<ConsumoProducto> lista = new ArrayList<>();
    String sql = "SELECT c.*, p.Nombre AS nombreProducto, (p.Precio * c.Cantidad) AS total " +
                 "FROM CONSUMO_PRODUCTO c " +
                 "JOIN PRODUCTO p ON c.IdProducto = p.IdProducto " +
                 "WHERE DATE(c.FechaConsumo) = CURDATE()";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            ConsumoProducto c = new ConsumoProducto();
            c.setIdConsumo(rs.getInt("IdConsumo"));
            c.setIdHabitacion(rs.getInt("IdHabitacion"));
            c.setNombreProducto(rs.getString("nombreProducto"));
            c.setCantidad(rs.getInt("Cantidad"));
            c.setPrecio(rs.getDouble("total") / rs.getInt("Cantidad"));
            c.setTotal(rs.getDouble("total"));
            c.setFechaConsumo(rs.getTimestamp("FechaConsumo"));
            lista.add(c);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}


}
