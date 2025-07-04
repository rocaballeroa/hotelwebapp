/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hotel.interfaces;

import com.hotel.modelo.Usuario;
import java.util.List;

public interface IUsuarioDAO {
    List<Usuario> listar();
    Usuario obtener(int id);
    boolean agregar(Usuario u);
    boolean editar(Usuario u);
    boolean eliminar(int id);
}
