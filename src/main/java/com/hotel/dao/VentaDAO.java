/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.dao;

import com.hotel.interfaces.IVentaDAO;
import com.hotel.modelo.DetalleVenta;
import com.hotel.modelo.Venta;
import com.hotel.util.Conexion;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class VentaDAO implements IVentaDAO {
    @Override
    public boolean registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        String sqlVenta = "INSERT INTO VENTA (IdRecepcion, Total, Estado) VALUES (?, ?, ?)";
        String sqlDetalle = "INSERT INTO DETALLE_VENTA (IdVenta, IdProducto, Cantidad, SubTotal, PrecioUnitario, NombreProducto) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement psVenta = null;
        PreparedStatement psDetalle = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false); // 🔒 Iniciar transacción

            // Insertar VENTA
            psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setInt(1, venta.getIdRecepcion());
            psVenta.setDouble(2, venta.getTotal());
            psVenta.setString(3, venta.getEstado());
            System.out.println("💰 Total que se va a registrar: " + venta.getTotal());
            psVenta.executeUpdate();

            rs = psVenta.getGeneratedKeys();
            int idVenta = 0;
            if (rs.next()) {
                idVenta = rs.getInt(1);
            }

            // Insertar DETALLE_VENTA
            psDetalle = con.prepareStatement(sqlDetalle);
            for (DetalleVenta d : detalles) {
                psDetalle.setInt(1, idVenta);
                psDetalle.setInt(2, d.getIdProducto()); // Puede ser 0 para hospedaje
                psDetalle.setInt(3, d.getCantidad());
                psDetalle.setDouble(4, d.getSubTotal());
                psDetalle.setDouble(5, d.getPrecioUnitario());
                psDetalle.setString(6, d.getNombreProducto());
                psDetalle.addBatch();
            }

            psDetalle.executeBatch();
            con.commit(); // ✅ Confirmar transacción
            return true;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback(); // 🔁 Deshacer si hay error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (psVenta != null) psVenta.close(); } catch (Exception ignored) {}
            try { if (psDetalle != null) psDetalle.close(); } catch (Exception ignored) {}
            try { if (con != null) con.setAutoCommit(true); con.close(); } catch (Exception ignored) {}
        }

        return false;
    }

    @Override
    public boolean registrarVenta(Venta venta) {
        return registrarVenta(venta, venta.getDetalles());
    }

    
 @Override
public boolean existeVentaPorRecepcion(int idRecepcion) {
    String sql = "SELECT COUNT(*) FROM VENTA WHERE IdRecepcion = ?";
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idRecepcion);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
@Override
public List<Venta> listarHoy() {
    List<Venta> lista = new ArrayList<>();
    String sql = "SELECT * FROM VENTA WHERE DATE(FechaVenta) = CURDATE()";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Venta v = new Venta();
            v.setIdVenta(rs.getInt("IdVenta"));
            v.setIdRecepcion(rs.getInt("IdRecepcion"));
            v.setTotal(rs.getDouble("Total"));
            v.setEstado(rs.getString("Estado"));
            v.setFechaVenta(rs.getTimestamp("FechaVenta"));
            lista.add(v);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}


@Override
public List<Venta> listar() {
    List<Venta> lista = new ArrayList<>();
    String sql = "SELECT * FROM VENTA";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Venta v = new Venta();
            v.setIdVenta(rs.getInt("IdVenta"));
            v.setIdRecepcion(rs.getInt("IdRecepcion"));
            v.setTotal(rs.getDouble("Total"));
            v.setEstado(rs.getString("Estado"));
            v.setFechaVenta(rs.getTimestamp("FechaVenta"));  // Asegúrate de esto
            lista.add(v);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}

@Override
public List<Venta> listarPorRango(String inicio, String fin) {
    List<Venta> lista = new ArrayList<>();
    String sql = "SELECT * FROM VENTA WHERE DATE(FechaVenta) BETWEEN ? AND ?";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, inicio);
        ps.setString(2, fin);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Venta v = new Venta();
            v.setIdVenta(rs.getInt("IdVenta"));
            v.setIdRecepcion(rs.getInt("IdRecepcion"));
            v.setTotal(rs.getDouble("Total"));
            v.setEstado(rs.getString("Estado"));
            v.setFechaVenta(rs.getTimestamp("FechaVenta"));
            lista.add(v);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}

}
