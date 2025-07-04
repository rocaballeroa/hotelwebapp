/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.dao;

import com.hotel.interfaces.IUsuarioDAO;
import com.hotel.modelo.Usuario;
import com.hotel.util.Conexion;

import java.sql.*;
import java.util.*;

public class UsuarioDAO implements IUsuarioDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM PERSONA WHERE IdTipoPersona IN (1, 2) AND Estado = 1";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdPersona(rs.getInt("IdPersona"));
                u.setTipoDocumento(rs.getString("TipoDocumento"));
                u.setDocumento(rs.getString("Documento"));
                u.setNombre(rs.getString("Nombre"));
                u.setApellido(rs.getString("Apellido"));
                u.setCorreo(rs.getString("Correo"));
                u.setClave(rs.getString("Clave"));
                u.setIdTipoPersona(rs.getInt("IdTipoPersona"));
                u.setEstado(rs.getBoolean("Estado"));
                u.setImagen(rs.getString("Imagen")); 
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Usuario obtener(int id) {
        String sql = "SELECT * FROM PERSONA WHERE IdPersona=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdPersona(rs.getInt("IdPersona"));
                u.setTipoDocumento(rs.getString("TipoDocumento"));
                u.setDocumento(rs.getString("Documento"));
                u.setNombre(rs.getString("Nombre"));
                u.setApellido(rs.getString("Apellido"));
                u.setCorreo(rs.getString("Correo"));
                u.setClave(rs.getString("Clave"));
                u.setIdTipoPersona(rs.getInt("IdTipoPersona"));
                u.setEstado(rs.getBoolean("Estado"));
                u.setImagen(rs.getString("Imagen")); // ✅ Añadido
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregar(Usuario u) {
        String sql = "INSERT INTO PERSONA (TipoDocumento, Documento, Nombre, Apellido, Correo, Clave, IdTipoPersona, Estado, Imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getTipoDocumento());
            ps.setString(2, u.getDocumento());
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getApellido());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getClave());
            ps.setInt(7, u.getIdTipoPersona());
            ps.setBoolean(8, u.isEstado());
            ps.setString(9, u.getImagen()); 
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editar(Usuario u) {
        String sql = "UPDATE PERSONA SET TipoDocumento=?, Documento=?, Nombre=?, Apellido=?, Correo=?, Clave=?, IdTipoPersona=?, Estado=?,Imagen=? WHERE IdPersona=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getTipoDocumento());
            ps.setString(2, u.getDocumento());
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getApellido());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getClave());
            ps.setInt(7, u.getIdTipoPersona());
            ps.setBoolean(8, u.isEstado());
            ps.setString(9, u.getImagen());
            ps.setInt(10, u.getIdPersona());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "UPDATE PERSONA SET Estado=0 WHERE IdPersona=?";
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
    
    public Usuario validar(String correo, String clave) {
    Usuario u = null;
    String sql = "SELECT * FROM PERSONA WHERE Correo=? AND Clave=? AND Estado=1";
    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, correo);
        ps.setString(2, clave);
        rs = ps.executeQuery();
        if (rs.next()) {
            u = new Usuario();
            u.setIdPersona(rs.getInt("IdPersona"));
            u.setTipoDocumento(rs.getString("TipoDocumento"));
            u.setDocumento(rs.getString("Documento"));
            u.setNombre(rs.getString("Nombre"));
            u.setApellido(rs.getString("Apellido"));
            u.setCorreo(rs.getString("Correo"));
            u.setClave(rs.getString("Clave"));
            u.setIdTipoPersona(rs.getInt("IdTipoPersona"));
            u.setEstado(rs.getBoolean("Estado"));
            u.setImagen(rs.getString("Imagen"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return u;
}

    public List<Usuario> listarClientes() {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT * FROM PERSONA WHERE IdTipoPersona = 3 AND Estado = 1";
    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Usuario u = new Usuario();
            u.setIdPersona(rs.getInt("IdPersona"));
            u.setTipoDocumento(rs.getString("TipoDocumento"));
            u.setDocumento(rs.getString("Documento"));
            u.setNombre(rs.getString("Nombre"));
            u.setApellido(rs.getString("Apellido"));
            u.setCorreo(rs.getString("Correo"));
            u.setClave(rs.getString("Clave"));
            u.setIdTipoPersona(rs.getInt("IdTipoPersona"));
            u.setEstado(rs.getBoolean("Estado"));
            u.setImagen(rs.getString("Imagen")); // ✅ Añadido
            lista.add(u);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

    public List<Usuario> listarPorTipo(int tipo) {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT * FROM PERSONA WHERE IdTipoPersona = ? AND Estado = 1";
    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        ps.setInt(1, tipo);
        rs = ps.executeQuery();
        while (rs.next()) {
            Usuario u = new Usuario();
            u.setIdPersona(rs.getInt("IdPersona"));
            u.setNombre(rs.getString("Nombre"));
            u.setApellido(rs.getString("Apellido"));
            u.setDocumento(rs.getString("Documento"));
            
            lista.add(u);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

}

