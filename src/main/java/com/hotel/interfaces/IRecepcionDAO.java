/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hotel.interfaces;

import com.hotel.modelo.Recepcion;
import java.util.List;

public interface IRecepcionDAO {
    int obtenerIdRecepcionPorHabitacion(int idHabitacion);
    boolean actualizarEstadoReserva(int idRecepcion, int idEstadoReserva);
    List<Recepcion> filtrarRecepciones(String estado, String cliente, String orden);
}
