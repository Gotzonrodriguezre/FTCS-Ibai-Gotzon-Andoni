package com.example.bar.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "perfil_albaran")
@IdClass(PerfilAlbaranId.class)
public class PerfilAlbaran {

    @Id
    @Column(name = "id_perfil")
    private Integer perfilId;

    @Id
    @Column(name = "id_albaran")
    private Integer albaranId;

    private Date fecha;

    // Getters y Setters
    public Integer getPerfilId() { return perfilId; }
    public void setPerfilId(Integer perfilId) { this.perfilId = perfilId; }

    public Integer getAlbaranId() { return albaranId; }
    public void setAlbaranId(Integer albaranId) { this.albaranId = albaranId; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}
