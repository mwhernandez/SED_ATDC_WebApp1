package com.sedapal.tramite.beans;

import java.io.Serializable;

public class AnioBean implements Serializable{

	private String codigo;
	private String descripcion;
	private String estado;
	
	
	public AnioBean(String codigo, String descripcion, String Origen) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		
		
	}
	
	public AnioBean(String codigo, String descripcion, String estado, String fecha) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.estado = estado;
		
		
	}
	public AnioBean() {
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

	
	

}
