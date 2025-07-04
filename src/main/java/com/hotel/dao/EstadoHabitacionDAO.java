/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.dao;

import com.hotel.interfaces.IEstadoHabitacionDAO;
import com.hotel.modelo.EstadoHabitacion;
import com.hotel.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoHabitacionDAO implements IEstadoHabitacionDAO {

    @Override
    public List<EstadoHabitacion> listar() {
        List<EstadoHabitacion> lista = new ArrayList<>();
        String sql = "SELECT IdEstadoHabitacion, Descripcion FROM ESTADO_HABITACION";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EstadoHabitacion estado = new EstadoHabitacion();
                estado.setIdEstadoHabitacion(rs.getInt("IdEstadoHabitacion"));
                estado.setDescripcion(rs.getString("Descripcion"));
                lista.add(estado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
