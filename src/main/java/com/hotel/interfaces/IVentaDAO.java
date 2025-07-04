/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hotel.interfaces;

import com.hotel.modelo.DetalleVenta;
import com.hotel.modelo.Venta;
import java.util.List;

/**
 *
 * @author User
 */
public interface IVentaDAO {
    boolean registrarVenta(Venta venta, List<DetalleVenta> detalles);
    boolean registrarVenta(Venta venta); // ✅ Este método debe existir aquí
    
    boolean existeVentaPorRecepcion(int idRecepcion);
    public List<Venta> listar();       
    public List<Venta> listarHoy();  
public List<Venta> listarPorRango(String inicio, String fin);
}



