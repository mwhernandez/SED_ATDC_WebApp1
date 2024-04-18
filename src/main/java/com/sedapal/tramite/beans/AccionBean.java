package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class AccionBean implements Serializable{
	private int codigo;
    private String descripcion;
    private Date fecha;
    private String responsable;
    private String estado;    
    private boolean selected;
    
	
    
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}  
	public Date getFecha(){
		return fecha;
	}
	public void setFecha(Date fecha){
		this.fecha= fecha;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}


}
