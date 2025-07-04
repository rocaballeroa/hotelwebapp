/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.dao;

import com.hotel.interfaces.ICategoriaDAO;
import com.hotel.modelo.Categoria;
import com.hotel.util.Conexion;
import java.sql.*;
import java.util.*;

public class CategoriaDAO implements ICategoriaDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM CATEGORIA";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("IdCategoria"));
                c.setDescripcion(rs.getString("Descripcion"));
                c.setEstado(rs.getBoolean("Estado"));
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Categoria obtener(int id) {
        String sql = "SELECT * FROM CATEGORIA WHERE IdCategoria=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Categoria(
                    rs.getInt("IdCategoria"),
                    rs.getString("Descripcion"),
                    rs.getBoolean("Estado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregar(Categoria c) {
        String sql = "INSERT INTO CATEGORIA (Descripcion, Estado) VALUES (?, ?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getDescripcion());
            ps.setBoolean(2, c.isEstado());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editar(Categoria c) {
        String sql = "UPDATE CATEGORIA SET Descripcion=?, Estado=? WHERE IdCategoria=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getDescripcion());
            ps.setBoolean(2, c.isEstado());
            ps.setInt(3, c.getIdCategoria());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM CATEGORIA WHERE IdCategoria=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
