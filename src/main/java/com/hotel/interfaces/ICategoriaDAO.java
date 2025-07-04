/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hotel.interfaces;

import com.hotel.modelo.Categoria;
import java.util.List;

public interface ICategoriaDAO {
    List<Categoria> listar();
    Categoria obtener(int id);
    boolean agregar(Categoria c);
    boolean editar(Categoria c);
    boolean eliminar(int id);
}
