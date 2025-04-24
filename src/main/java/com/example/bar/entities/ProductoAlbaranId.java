package com.example.bar.entities;

import java.io.Serializable;
import java.util.Objects;

public class ProductoAlbaranId implements Serializable {
    private Integer productoId;
    private Integer albaranId;

    public ProductoAlbaranId() {}

    public ProductoAlbaranId(Integer productoId, Integer albaranId) {
        this.productoId = productoId;
        this.albaranId = albaranId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductoAlbaranId)) return false;
        ProductoAlbaranId that = (ProductoAlbaranId) o;
        return Objects.equals(productoId, that.productoId) &&
                Objects.equals(albaranId, that.albaranId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productoId, albaranId);
    }
}
