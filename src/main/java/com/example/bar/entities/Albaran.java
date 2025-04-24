package com.example.bar.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "albaran")
public class Albaran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String CIF;
    private Integer cantidad;
    private String estado;
    private LocalDate fecha;
    
    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCIF() { return CIF; }
    public void setCIF(String CIF) { this.CIF = CIF; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
}
