package com.example.myapplication.Modelo;

public class Albaran {
    private Integer id;
    private String nombre;
    private String cif;
    private int cantidad;
    private String estado;
    private String fecha;
    private float precio;
    private String foto;
    public Albaran() {
    }

    public Albaran(Integer id, String nombre, String cif, int cantidad, String estado, String fecha, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.cif = cif;
        this.cantidad = cantidad;
        this.estado = estado;
        this.fecha = fecha;
        this.precio = precio;
    }
    public Albaran(Integer id,  String foto, String nombre, String cif, int cantidad, String estado, String fecha, float precio) {
        this.id = id;
        this.foto = foto;
        this.nombre = nombre;
        this.cif = cif;
        this.cantidad = cantidad;
        this.estado = estado;
        this.fecha = fecha;
        this.precio = precio;
    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCIF() { return cif; }
    public void setCIF(String cif) { this.cif = cif; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

}
