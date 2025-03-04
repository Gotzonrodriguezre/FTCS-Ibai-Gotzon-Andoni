package modelo;
// Generated 4 mar 2025, 10:26:20 by Hibernate Tools 6.5.1.Final

import java.sql.Date;

/**
 * Albaran generated by hbm2java
 */
public class Albaran implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private String cif;
	private String nombreProducto;
	private int cantidad;
	private Date fecha;
	private byte[] foto;

	public Albaran() {
	}

	public Albaran(String nombre, String cif, String nombreProducto, int cantidad, Date fecha) {
		this.nombre = nombre;
		this.cif = cif;
		this.nombreProducto = nombreProducto;
		this.cantidad = cantidad;
		this.fecha = fecha;
	}

	public Albaran(String nombre, String cif, String nombreProducto, int cantidad, Date fecha, byte[] foto) {
		this.nombre = nombre;
		this.cif = cif;
		this.nombreProducto = nombreProducto;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.foto = foto;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCif() {
		return this.cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNombreProducto() {
		return this.nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

}
