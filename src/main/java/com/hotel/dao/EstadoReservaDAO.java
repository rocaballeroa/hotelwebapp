/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.dao;

import com.hotel.modelo.EstadoReserva;
import com.hotel.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class EstadoReservaDAO {
    public List<EstadoReserva> listar() {
        List<EstadoReserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM ESTADO_RESERVA WHERE Estado = 1";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EstadoReserva e = new EstadoReserva();
                e.setIdEstadoReserva(rs.getInt("IdEstadoReserva"));
                e.setDescripcion(rs.getString("Descripcion"));
                lista.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}

