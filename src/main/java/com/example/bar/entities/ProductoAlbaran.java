package com.example.bar.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "producto_albaran")
@IdClass(ProductoAlbaranId.class)
public class ProductoAlbaran {

    @Id
    @Column(name = "id_producto")
    private Integer productoId;

    @Id
    @Column(name = "id_albaran")
    private Integer albaranId;

    private Integer cantidadProducto;

    // Getters y Setters
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }

    public Integer getAlbaranId() { return albaranId; }
    public void setAlbaranId(Integer albaranId) { this.albaranId = albaranId; }

    public Integer getCantidadProducto() { return cantidadProducto; }
    public void setCantidadProducto(Integer cantidadProducto) { this.cantidadProducto = cantidadProducto; }
}
