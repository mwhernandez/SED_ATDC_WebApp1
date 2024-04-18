package com.sedapal.tramite.beans;

import java.io.Serializable;

public class Area implements Serializable{

	private String codigo;
	private String descripcion;
	private String estado;
	private String fecha;
	private String origen;
	private int indicador;
	
	public Area(String codigo, String descripcion, String Origen) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.origen = origen;
		
	}
	
	public Area(String codigo, String descripcion, String estado, String fecha, int indicador) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.fecha = fecha;
		this.indicador = indicador;
	}
	public Area() {
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public int getIndicador() {
		return indicador;
	}

	public void setIndicador(int indicador) {
		this.indicador = indicador;
	}
	
	
	

}
