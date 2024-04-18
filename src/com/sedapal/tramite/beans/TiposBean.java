package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class TiposBean implements Serializable{
	private int  ncodigo;
	private String codigo;
	private String Vcodtipo;
	private String tipo;	
    private String descripcion;
    private String Vdescrip;
    private Date fecha;
    private String responsable;
    private String estado;   
    private String abreviatura;
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
	public Date getFecha(){
		return fecha;
	}
	public void setFecha(Date fecha){
		this.fecha = fecha;
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
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getVcodtipo() {
		return Vcodtipo;
	}


	public void setVcodtipo(String vcodtipo) {
		Vcodtipo = vcodtipo;
	}


	public String getVdescrip() {
		return Vdescrip;
	}


	public void setVdescrip(String vdescrip) {
		Vdescrip = vdescrip;
	}


	public int getNcodigo() {
		return ncodigo;
	}


	public void setNcodigo(int ncodigo) {
		this.ncodigo = ncodigo;
	}


		
	
	

}
