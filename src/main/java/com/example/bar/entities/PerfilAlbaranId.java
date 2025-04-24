package com.example.bar.entities;

import java.io.Serializable;
import java.util.Objects;

public class PerfilAlbaranId implements Serializable {
    private Integer perfilId;
    private Integer albaranId;

    public PerfilAlbaranId() {}

    public PerfilAlbaranId(Integer perfilId, Integer albaranId) {
        this.perfilId = perfilId;
        this.albaranId = albaranId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PerfilAlbaranId)) return false;
        PerfilAlbaranId that = (PerfilAlbaranId) o;
        return Objects.equals(perfilId, that.perfilId) &&
                Objects.equals(albaranId, that.albaranId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(perfilId, albaranId);
    }
}
