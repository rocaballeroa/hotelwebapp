/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hotel.interfaces;

import com.hotel.modelo.Producto;
import java.util.List;

public interface IProductoDAO {
    List<Producto> listar();
    Producto obtener(int id);
    boolean agregar(Producto producto);
    boolean editar(Producto producto);
    boolean eliminar(int id);
    boolean actualizarCantidad(int idProducto, int nuevaCantidad);

}
