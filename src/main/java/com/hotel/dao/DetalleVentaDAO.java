/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.dao;

import com.hotel.interfaces.IDetalleVentaDAO;
import com.hotel.modelo.DetalleVenta;
import com.hotel.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAO implements IDetalleVentaDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<DetalleVenta> listarPorVenta(int idVenta) {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = """
            SELECT dv.*, p.Nombre AS nombreProducto, p.Precio AS precioUnitario
            FROM DETALLE_VENTA dv
            JOIN PRODUCTO p ON dv.IdProducto = p.IdProducto
            WHERE dv.IdVenta = ?
        """;

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();

            while (rs.next()) {
                DetalleVenta dv = new DetalleVenta();
                dv.setIdDetalleVenta(rs.getInt("IdDetalleVenta"));
                dv.setIdVenta(rs.getInt("IdVenta"));
                dv.setIdProducto(rs.getInt("IdProducto"));
                dv.setCantidad(rs.getInt("Cantidad"));
                dv.setSubTotal(rs.getDouble("SubTotal"));
                dv.setNombreProducto(rs.getString("nombreProducto")); 
                dv.setPrecioUnitario(rs.getDouble("precioUnitario"));
                lista.add(dv);
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
}
