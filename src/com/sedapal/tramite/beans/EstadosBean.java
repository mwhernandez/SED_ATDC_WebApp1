package com.sedapal.tramite.beans;

import java.io.Serializable;

public class EstadosBean implements Serializable{
	
	private String codigo;
    private String descripcion;
    
    
	
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
    
    

}
