package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class RemitenteBean implements Serializable{
	private int codigo;
    private String descripcion;    
    private Integer area;
    private String estado;    
    private Date fecha;
    private String responsable;
    private String respactual; 
    private int indicador;
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
	
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	
	public Date getFecha(){
		return fecha;
	}
	public void setFecha(Date fecha){
		this.fecha= fecha;
	}
	public String getResponActual() {
		return respactual;
	}
	public void setResponActual(String respactual) {
		this.respactual = respactual;
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
	public int getIndicador() {
		return indicador;
	}
	public void setIndicador(int indicador) {
		this.indicador = indicador;
	}
	


}
