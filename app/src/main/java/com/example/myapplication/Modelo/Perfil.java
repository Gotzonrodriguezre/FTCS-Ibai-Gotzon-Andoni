package com.example.myapplication.Modelo;

import java.io.Serializable;
import java.util.Objects;

public class Perfil {
    private int id;
    private String foto;  // La foto es una cadena Base64
    private String nombre;
    private String clave;
    private String estado;
    private String tipo;
    private String correo;

    // Constructor
    public Perfil(int id, String foto, String nombre, String clave, String estado, String tipo, String correo) {
        this.id = id;
        this.foto = foto;
        this.nombre = nombre;
        this.clave = clave;
        this.estado = estado;
        this.tipo = tipo;
        this.correo = correo;
    }

    public Perfil() {

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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return id == perfil.id && Objects.equals(foto, perfil.foto) && Objects.equals(nombre, perfil.nombre) && Objects.equals(clave, perfil.clave) && Objects.equals(estado, perfil.estado) && Objects.equals(tipo, perfil.tipo) && Objects.equals(correo, perfil.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foto, nombre, clave, estado, tipo, correo);
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", foto='" + foto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", clave='" + clave + '\'' +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
