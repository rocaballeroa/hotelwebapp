/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.modelo;

public class Habitacion {
    private int idHabitacion;
    private String numero;
    private String detalle;
    private double precio;
    private int idEstadoHabitacion;
    private int idPiso;
    private int idCategoria;
    private boolean estado;

    // ✅ Nuevo campo para la imagen
    private String imagen;
    private String categoriaDescripcion;
    private String estadoHabitacionDescripcion;
    
    // Getters y Setters
    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdEstadoHabitacion() {
        return idEstadoHabitacion;
    }

    public void setIdEstadoHabitacion(int idEstadoHabitacion) {
        this.idEstadoHabitacion = idEstadoHabitacion;
    }

    public int getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(int idPiso) {
        this.idPiso = idPiso;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // ✅ Getters y Setters para la imagen
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public String getCategoriaDescripcion() {
    return categoriaDescripcion;
}

public void setCategoriaDescripcion(String categoriaDescripcion) {
    this.categoriaDescripcion = categoriaDescripcion;
}

public String getEstadoHabitacionDescripcion() {
    return estadoHabitacionDescripcion;
}

public void setEstadoHabitacionDescripcion(String estadoHabitacionDescripcion) {
    this.estadoHabitacionDescripcion = estadoHabitacionDescripcion;
}
}
