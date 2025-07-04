/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.modelo;

import java.sql.Timestamp;

public class Recepcion {
    private int idRecepcion;
    private int idCliente;
    private int idHabitacion;
    private Timestamp fechaEntrada; // se genera automáticamente en la BD
    private Timestamp fechaSalida;
    private Timestamp fechaSalidaConfirmacion;
    private double precioInicial;
    private double adelanto;
    private double precioRestante;
    private double totalPagado;
    private double costoPenalidad;
    private String observacion;
    private boolean estado;
    private String nombreCliente;
    private String numeroHabitacion;
    private String categoriaDescripcion;
    private int idEstadoReserva;
    private String estadoReservaDescripcion;

   



    // Getters y Setters
    public int getIdRecepcion() {
        return idRecepcion;
    }

    public void setIdRecepcion(int idRecepcion) {
        this.idRecepcion = idRecepcion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public Timestamp getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Timestamp fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Timestamp getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Timestamp fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Timestamp getFechaSalidaConfirmacion() {
        return fechaSalidaConfirmacion;
    }

    public void setFechaSalidaConfirmacion(Timestamp fechaSalidaConfirmacion) {
        this.fechaSalidaConfirmacion = fechaSalidaConfirmacion;
    }

    public double getPrecioInicial() {
        return precioInicial;
    }

    public void setPrecioInicial(double precioInicial) {
        this.precioInicial = precioInicial;
    }

    public double getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(double adelanto) {
        this.adelanto = adelanto;
    }

    public double getPrecioRestante() {
        return precioRestante;
    }

    public void setPrecioRestante(double precioRestante) {
        this.precioRestante = precioRestante;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public double getCostoPenalidad() {
        return costoPenalidad;
    }

    public void setCostoPenalidad(double costoPenalidad) {
        this.costoPenalidad = costoPenalidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public String getNombreCliente() {
    return nombreCliente;
}

public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
}

public String getNumeroHabitacion() {
    return numeroHabitacion;
}

public void setNumeroHabitacion(String numeroHabitacion) {
    this.numeroHabitacion = numeroHabitacion;
}
public String getCategoriaDescripcion() {
    return categoriaDescripcion;
}

public void setCategoriaDescripcion(String categoriaDescripcion) {
    this.categoriaDescripcion = categoriaDescripcion;
}

public int getIdEstadoReserva() {
    return idEstadoReserva;
}
public void setIdEstadoReserva(int idEstadoReserva) {
    this.idEstadoReserva = idEstadoReserva;
}
public String getEstadoReservaDescripcion() {
    return estadoReservaDescripcion;
}

public void setEstadoReservaDescripcion(String estadoReservaDescripcion) {
    this.estadoReservaDescripcion = estadoReservaDescripcion;
}

}
