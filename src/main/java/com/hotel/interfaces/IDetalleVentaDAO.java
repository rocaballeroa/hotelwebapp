/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hotel.interfaces;

import com.hotel.modelo.DetalleVenta;
import java.util.List;

/**
 *
 * @author User
 */
public interface IDetalleVentaDAO {
    List<DetalleVenta> listarPorVenta(int idVenta);
}
