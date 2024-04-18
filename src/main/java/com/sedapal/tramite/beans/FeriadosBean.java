package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class FeriadosBean implements Serializable{
	private int ncodigo;
	private Date dferiado;
    private String vdescripcion;    
    private String vtipoferiado;   
    private String vestado;    
    private Date dfeccrea;
    private Date dfecactual;
    private String responsable;
    private String respactual;    
    private boolean selected;
	public int getNcodigo() {
		return ncodigo;
	}
	public void setNcodigo(int ncodigo) {
		this.ncodigo = ncodigo;
	}
	public Date getDferiado() {
		return dferiado;
	}
	public void setDferiado(Date dferiado) {
		this.dferiado = dferiado;
	}
	public String getVdescripcion() {
		return vdescripcion;
	}
	public void setVdescripcion(String vdescripcion) {
		this.vdescripcion = vdescripcion;
	}
	public String getVtipoferiado() {
		return vtipoferiado;
	}
	public void setVtipoferiado(String vtipoferiado) {
		this.vtipoferiado = vtipoferiado;
	}
	public String getVestado() {
		return vestado;
	}
	public void setVestado(String vestado) {
		this.vestado = vestado;
	}
	
	
	
	public Date getDfeccrea() {
		return dfeccrea;
	}
	public void setDfeccrea(Date dfeccrea) {
		this.dfeccrea = dfeccrea;
	}
	public Date getDfecactual() {
		return dfecactual;
	}
	public void setDfecactual(Date dfecactual) {
		this.dfecactual = dfecactual;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getRespactual() {
		return respactual;
	}
	public void setRespactual(String respactual) {
		this.respactual = respactual;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
    
	
	


}
