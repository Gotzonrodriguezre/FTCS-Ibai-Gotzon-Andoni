package com.example.bar.entities;

import java.io.Serializable;
import java.util.Objects;

public class PerfilProductoId implements Serializable {
    private Integer perfilId;
    private Integer productoId;

    public PerfilProductoId() {}

    public PerfilProductoId(Integer perfilId, Integer productoId) {
        this.perfilId = perfilId;
        this.productoId = productoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PerfilProductoId)) return false;
        PerfilProductoId that = (PerfilProductoId) o;
        return Objects.equals(perfilId, that.perfilId) &&
                Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(perfilId, productoId);
    }
}
