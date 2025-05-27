package com.example.myapplication.Modelo;

import java.util.Date;

public class PerfilAlbaran {

    private Integer perfilId;
    private Integer albaranId;
    private Date fecha;

    // Constructor vacío
    public PerfilAlbaran() {
    }

    // Constructor con parámetros
    public PerfilAlbaran(Integer perfilId, Integer albaranId, Date fecha) {
        this.perfilId = perfilId;
        this.albaranId = albaranId;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Integer getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Integer perfilId) {
        this.perfilId = perfilId;
    }

    public Integer getAlbaranId() {
        return albaranId;
    }

    public void setAlbaranId(Integer albaranId) {
        this.albaranId = albaranId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "PerfilAlbaran{" +
                "perfilId=" + perfilId +
                ", albaranId=" + albaranId +
                ", fecha=" + fecha +
                '}';
    }
}
