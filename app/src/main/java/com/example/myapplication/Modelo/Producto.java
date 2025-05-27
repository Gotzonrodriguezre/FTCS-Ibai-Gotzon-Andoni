package com.example.myapplication.Modelo;

import java.io.Serializable;
import java.sql.Timestamp;
public class Producto {
    private int id;
    private String foto;
    private String nombre;
    private int cantidad;
    private int cantidadMinima;
    private String estado;
    private String fechaInteraccion;

    // Constructor
    public Producto(int id, String foto, String nombre, int cantidad, int cantidadMinima, String estado, String fechaInteraccion) {
        this.id = id;
        this.foto = foto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.cantidadMinima = cantidadMinima;
        this.estado = estado;
        this.fechaInteraccion = fechaInteraccion;
    }

    public Producto() {

    }
    public Producto(String nombre, int cantidad, int cantidadMinima, String foto) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.cantidadMinima = cantidadMinima;
        this.foto = foto;
    }
    public Producto(String nombre, int cantidad, int cantidadMinima) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.cantidadMinima = cantidadMinima;

    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaInteraccion() {
        return fechaInteraccion;
    }

    public void setFechaInteraccion(String fechaInteraccion) {
        this.fechaInteraccion = fechaInteraccion;
    }
}
