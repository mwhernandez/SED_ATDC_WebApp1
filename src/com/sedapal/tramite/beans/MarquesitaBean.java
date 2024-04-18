package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class MarquesitaBean implements Serializable{
	private String mensajeuno;
    private String mensajedos;
    private String mensajetres;
    
    private boolean selected;
    
    	
	
	public String getMensajeuno() {
		return mensajeuno;
	}
	public void setMensajeuno(String mensajeuno) {
		this.mensajeuno = mensajeuno;
	}
	public String getMensajedos() {
		return mensajedos;
	}
	public void setMensajedos(String mensajedos) {
		this.mensajedos = mensajedos;
	}
	
	
	public String getMensajetres() {
		return mensajetres;
	}
	public void setMensajetres(String mensajetres) {
		this.mensajetres = mensajetres;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
