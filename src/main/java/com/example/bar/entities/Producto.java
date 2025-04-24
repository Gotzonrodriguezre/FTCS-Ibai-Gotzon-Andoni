package com.example.bar.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private byte[] foto;
    private String nombre;
    private Integer cantidad;
    private Integer cantidadMinima;
    private String estado;

    @Column(name = "fecha_interaccion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaInteraccion;
    public Producto() {

    }
    public Producto(String nombre, int cantidad, int cantidadMinima) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.cantidadMinima = cantidadMinima;

    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public byte[] getFoto() { return foto; }
    public void setFoto(byte[] foto) { this.foto = foto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Integer getCantidadMinima() { return cantidadMinima; }
    public void setCantidadMinima(Integer cantidadMinima) { this.cantidadMinima = cantidadMinima; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaInteraccion() { return fechaInteraccion; }
    public void setFechaInteraccion(LocalDateTime fechaInteraccion) { this.fechaInteraccion = fechaInteraccion; }
}
