package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class LlavesBean {
	//implements Serializable
	private int ano;
    private int origen;
    private String tipodoc;
    private long correlativo;
    private String numdoc;
    private Date fechaplazo;
    private int diasplazo;
    private int codigo;
    private int ficha;
    private int codigo_representante;
    private int verifica;
    private Date fecharecepcion;
    private String prioridad;
    private String tipo_reporte;
    private String asunto_documento;
    private String ubicacion;
    
    
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getOrigen() {
		return origen;
	}
	public void setOrigen(int origen) {
		this.origen = origen;
	}
	public String getTipodoc() {
		return tipodoc;
	}
	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}
	
	public String getNumdoc() {
		return numdoc;
	}
	public void setNumdoc(String numdoc) {
		this.numdoc = numdoc;
	}
	public Date getFechaplazo() {
		return fechaplazo;
	}
	public void setFechaplazo(Date fechaplazo) {
		this.fechaplazo = fechaplazo;
	}
	public int getDiasplazo() {
		return diasplazo;
	}
	public void setDiasplazo(int diasplazo) {
		this.diasplazo = diasplazo;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getFicha() {
		return ficha;
	}
	public void setFicha(int ficha) {
		this.ficha = ficha;
	}
	public int getCodigo_representante() {
		return codigo_representante;
	}
	public void setCodigo_representante(int codigoRepresentante) {
		codigo_representante = codigoRepresentante;
	}
	public long getCorrelativo() {
		return correlativo;
	}
	public void setCorrelativo(long correlativo) {
		this.correlativo = correlativo;
	}
	public int getVerifica() {
		return verifica;
	}
	public void setVerifica(int verifica) {
		this.verifica = verifica;
	}
	public Date getFecharecepcion() {
		return fecharecepcion;
	}
	public void setFecharecepcion(Date fecharecepcion) {
		this.fecharecepcion = fecharecepcion;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getTipo_reporte() {
		return tipo_reporte;
	}
	public void setTipo_reporte(String tipoReporte) {
		tipo_reporte = tipoReporte;
	}
	public String getAsunto_documento() {
		return asunto_documento;
	}
	public void setAsunto_documento(String asunto_documento) {
		this.asunto_documento = asunto_documento;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
    
    
    
	

}
