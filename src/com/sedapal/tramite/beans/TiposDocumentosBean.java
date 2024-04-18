package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class TiposDocumentosBean implements Serializable{
	private String codigo;
    private String descripcion;
    private String abreviatura; //SED-FON-161
    
    private boolean selected;
    
    
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
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
	
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
