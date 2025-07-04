/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hotel.interfaces;

import com.hotel.modelo.Habitacion;
import java.util.List;

public interface IHabitacionDAO {
    List<Habitacion> listar();
    Habitacion obtener(int id);
    boolean agregar(Habitacion h);
    boolean editar(Habitacion h);
    boolean eliminar(int id);
}
