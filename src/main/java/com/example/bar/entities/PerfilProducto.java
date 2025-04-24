package com.example.bar.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "perfil_producto")
@IdClass(PerfilProductoId.class)
public class PerfilProducto {

    @Id
    @Column(name = "id_perfil")
    private Integer perfilId;

    @Id
    @Column(name = "id_producto")
    private Integer productoId;

    private String interaccion;

    // Getters y Setters
    public Integer getPerfilId() { return perfilId; }
    public void setPerfilId(Integer perfilId) { this.perfilId = perfilId; }

    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }

    public String getInteraccion() { return interaccion; }
    public void setInteraccion(String interaccion) { this.interaccion = interaccion; }
}
