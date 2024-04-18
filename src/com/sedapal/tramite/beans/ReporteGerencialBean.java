package com.sedapal.tramite.beans;

import java.util.Date;

/**
 *
 * @author Eli Diaz Horna
 * Fecha 01/06/2012
 * Sedapal
 */
public class ReporteGerencialBean {

    private int    ncorrelativo;
    private String vnumdoc;
    private String arearemitente;
    private String areaderivado;
    private String asunto;
    private String prioridad;
    private String tipDocumento;
    private Date   fechadoc;
    private Date   fechacrea;
    private Date   fechaderivacion;
    private Date   fechaplazo;
    private Date   fechaatencion;
    private Date   fecharecepcion;
    private int    diasplazo;
    private int    diastransc;
    private String estado;    
    private String situacion_documento;
    private String comentario;
    private String derivacion;
    private boolean atachment = false;
    private String vubiarchivo;
    private String vestado;
    private int    indicador;
    private boolean selected;
    
    //Atributos de consulta
    private int    codArea;
    private String fechaIni;
    private String fechaFin;
    
    
    
	public int getNcorrelativo() {
		return ncorrelativo;
	}
	public void setNcorrelativo(int ncorrelativo) {
		this.ncorrelativo = ncorrelativo;
	}
	public String getVnumdoc() {
		return vnumdoc;
	}
	public void setVnumdoc(String vnumdoc) {
		this.vnumdoc = vnumdoc;
	}
	public String getArearemitente() {
		return arearemitente;
	}
	public void setArearemitente(String arearemitente) {
		this.arearemitente = arearemitente;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getTipDocumento() {
		return tipDocumento;
	}
	public void setTipDocumento(String tipDocumento) {
		this.tipDocumento = tipDocumento;
	}
	
	public Date getFechadoc() {
		return fechadoc;
	}
	public void setFechadoc(Date fechadoc) {
		this.fechadoc = fechadoc;
	}
	public Date getFechacrea() {
		return fechacrea;
	}
	public void setFechacrea(Date fechacrea) {
		this.fechacrea = fechacrea;
	}
	public Date getFechaderivacion() {
		return fechaderivacion;
	}
	public void setFechaderivacion(Date fechaderivacion) {
		this.fechaderivacion = fechaderivacion;
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
	public int getDiastransc() {
		return diastransc;
	}
	public void setDiastransc(int diastransc) {
		this.diastransc = diastransc;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getDerivacion() {
		return derivacion;
	}
	public void setDerivacion(String derivacion) {
		this.derivacion = derivacion;
	}
	public int getCodArea() {
		return codArea;
	}
	public void setCodArea(int codArea) {
		this.codArea = codArea;
	}
	public String getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getVubiarchivo() {
		return vubiarchivo;
	}
	public void setVubiarchivo(String vubiarchivo) {
		this.vubiarchivo = vubiarchivo;
	}
	public String getVestado() {
		return vestado;
	}
	public void setVestado(String vestado) {
		this.vestado = vestado;
	}
	public int getIndicador() {
		return indicador;
	}
	public void setIndicador(int indicador) {
		this.indicador = indicador;
	}
	public boolean isAtachment() {
		return atachment;
	}
	public void setAtachment(boolean atachment) {
		this.atachment = atachment;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Date getFechaatencion() {
		return fechaatencion;
	}
	public void setFechaatencion(Date fechaatencion) {
		this.fechaatencion = fechaatencion;
	}
	public String getSituacion_documento() {
		return situacion_documento;
	}
	public void setSituacion_documento(String situacionDocumento) {
		situacion_documento = situacionDocumento;
	}
	public String getAreaderivado() {
		return areaderivado;
	}
	public void setAreaderivado(String areaderivado) {
		this.areaderivado = areaderivado;
	}
	public Date getFecharecepcion() {
		return fecharecepcion;
	}
	public void setFecharecepcion(Date fecharecepcion) {
		this.fecharecepcion = fecharecepcion;
	}
	
	
    
    
    

}
