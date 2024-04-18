package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class SecuencialBean {
	//implements Serializable
	private int ano;
    private int origen;  
    private int correlativo;
    
    
    
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
	
	public int getCorrelativo() {
		return correlativo;
	}
	public void setCorrelativo(int correlativo) {
		this.correlativo = correlativo;
	}
	
	
	

}
