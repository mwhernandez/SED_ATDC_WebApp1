package com.sedapal.tramite.beans;

import java.sql.Date;

public class CentroBean {
	private int codigo;
	private String nombre;
    private String direccion;
    private String abreviatura;
    private Date fecha;
    private String estado;
    private boolean selected;
    
    public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}  
	
	
	public String getDireccion() {
		return direccion;
	}	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getAbreviatura() {
		return abreviatura;
	}	
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	
	public Date getFecha(){
		return fecha;
	}
	public void setFecha(Date fecha){
		this.fecha= fecha;
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
