/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.dao;

import com.hotel.util.Conexion;
import com.hotel.interfaces.IProductoDAO;
import com.hotel.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements IProductoDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTO";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("IdProducto"));
                p.setNombre(rs.getString("Nombre"));
                p.setDetalle(rs.getString("Detalle"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setCantidad(rs.getInt("Cantidad"));
                p.setEstado(rs.getBoolean("Estado"));
                p.setFechaCreacion(rs.getTimestamp("FechaCreacion"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
public Producto obtener(int idProducto) {
    Producto p = new Producto();
    String sql = "SELECT * FROM PRODUCTO WHERE IdProducto = ?";
    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idProducto);
        rs = ps.executeQuery();

        if (rs.next()) {
            p.setIdProducto(rs.getInt("IdProducto"));
            p.setNombre(rs.getString("Nombre"));
            p.setDetalle(rs.getString("Detalle"));
            p.setPrecio(rs.getDouble("Precio")); // ✅ Asegúrate de esto
            p.setCantidad(rs.getInt("Cantidad"));
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
    return p;
}

    @Override
    public boolean agregar(Producto producto) {
        String sql = "INSERT INTO PRODUCTO (Nombre, Detalle, Precio, Cantidad, Estado) VALUES (?, ?, ?, ?, ?)";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDetalle());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getCantidad());
            ps.setBoolean(5, producto.isEstado());

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editar(Producto producto) {
        String sql = "UPDATE PRODUCTO SET Nombre=?, Detalle=?, Precio=?, Cantidad=?, Estado=? WHERE IdProducto=?";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDetalle());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getCantidad());
            ps.setBoolean(5, producto.isEstado());
            ps.setInt(6, producto.getIdProducto());

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM PRODUCTO WHERE IdProducto = ?";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    @Override
    public boolean actualizarCantidad(int idProducto, int nuevaCantidad) {
    String sql = "UPDATE PRODUCTO SET Cantidad = ? WHERE IdProducto = ?";

    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        ps.setInt(1, nuevaCantidad);
        ps.setInt(2, idProducto);

        return ps.executeUpdate() == 1;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
public double obtenerPrecioPorId(int idProducto) {
    String sql = "SELECT Precio FROM PRODUCTO WHERE IdProducto = ?";
    double precio = 0.0;

    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idProducto);
        rs = ps.executeQuery();

        if (rs.next()) {
            precio = rs.getDouble("Precio");
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

    return precio;
}

    
}

