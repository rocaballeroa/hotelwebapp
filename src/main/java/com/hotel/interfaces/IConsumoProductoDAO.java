/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hotel.interfaces;

import java.util.List;
import com.hotel.modelo.ConsumoProducto;

public interface IConsumoProductoDAO {
    public List<ConsumoProducto> listarPorHabitacion(int idHabitacion);
    public boolean agregar(ConsumoProducto consumo);
    public boolean eliminar(int idConsumo);
    public List<ConsumoProducto> listarPorCliente(int idCliente);
    public List<ConsumoProducto> listarTodo();  
    public List<ConsumoProducto> listarHoy();  
    public List<ConsumoProducto> listarPorRecepcion(int idRecepcion);
    boolean eliminarPorRecepcion(int idRecepcion);

}
