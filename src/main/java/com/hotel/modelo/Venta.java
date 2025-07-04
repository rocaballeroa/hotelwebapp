/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.modelo;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author User
 */
public class Venta {
    private int idVenta;
    private int idRecepcion;
    private double total;
    private String estado;
private List<DetalleVenta> detalles;
private Timestamp fechaVenta;
    // Getters y Setters

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdRecepcion() {
        return idRecepcion;
    }

    public void setIdRecepcion(int idRecepcion) {
        this.idRecepcion = idRecepcion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
  public List<DetalleVenta> getDetalles() {
    return detalles;
}

public void setDetalles(List<DetalleVenta> detalles) {
    this.detalles = detalles;
}

 public Timestamp getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(Timestamp fechaVenta) { this.fechaVenta = fechaVenta; }
}
