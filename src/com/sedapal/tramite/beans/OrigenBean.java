package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class OrigenBean implements Serializable{
	private String codigo;
    private String descripcion;   
    private boolean selected;
    
    
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
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
