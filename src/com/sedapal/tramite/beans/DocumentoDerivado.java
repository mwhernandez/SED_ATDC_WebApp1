package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class DocumentoDerivado implements Serializable{
	private int correlativo;
	private String numeroDocumento;
	private Date fechaDoc;
	private Date fechaDerivacion;
	private String asunto;
	private String observacion;
	private String nombre;
	private String areaDerivado;
	private String ubicacion; 
	private String estado;
	private String indicador;
	private boolean atachment;
		
	public int getCorrelativo() {
		return correlativo;
	}
	public void setCorrelativo(int correlativo) {
		this.correlativo = correlativo;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String obervacion) {
		this.observacion = obervacion;
	}
			
	public Date getFechaDoc() {
		return fechaDoc;
	}
	public void setFechaDoc(Date fechaDoc) {
		this.fechaDoc = fechaDoc;
	}
	public Date getFechaDerivacion() {
		return fechaDerivacion;
	}
	public void setFechaDerivacion(Date fechaDerivacion) {
		this.fechaDerivacion = fechaDerivacion;
	}
	
	public String getAreaDerivado() {
		return areaDerivado;
	}
	public void setAreaDerivado(String areaDerivado) {
		this.areaDerivado = areaDerivado;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getIndicador() {
		return indicador;
	}
	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	public boolean isAtachment() {
		return atachment;
	}
	public void setAtachment(boolean atachment) {
		this.atachment = atachment;
	}
	
	

}
