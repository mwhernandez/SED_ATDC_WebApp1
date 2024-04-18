package com.sedapal.tramite.nova.util;

import java.util.List;

public class CasillaElectronicaResponse {

	
	private String idexp;
	private String numexpediente;
	private String numDocInteresado;
	private String numreg;
	private String codExpType;
	private String estado;
	private String observaciones;
	private String cuerpo;
	private List<Exp_docs> docs;
	
	
	
	public CasillaElectronicaResponse() {
		super();
		this.cuerpo = "Notificación de trámite";
		this.estado="ATENDIDO";
		
	}
	public String getIdexp() {
		return idexp;
	}
	public void setIdexp(String idexp) {
		this.idexp = idexp;
	}
	public String getNumexpediente() {
		return numexpediente;
	}
	public void setNumexpediente(String numexpediente) {
		this.numexpediente = numexpediente;
	}
	public String getNumDocInteresado() {
		return numDocInteresado;
	}
	public void setNumDocInteresado(String numDocInteresado) {
		this.numDocInteresado = numDocInteresado;
	}
	public String getCodExpType() {
		return codExpType;
	}
	public void setCodExpType(String codExpType) {
		this.codExpType = codExpType;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public List<Exp_docs> getDocs() {
		return docs;
	}
	public void setDocs(List<Exp_docs> docs) {
		this.docs = docs;
	}
	public String getNumreg() {
		return numreg;
	}
	public void setNumreg(String numreg) {
		this.numreg = numreg;
	}
	
	
	 
	
	
	
}
