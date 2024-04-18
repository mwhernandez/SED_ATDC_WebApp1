package com.sedapal.tramite.beans;

import java.io.Serializable;

public class ResumenBean implements Serializable{

	private int ncodarea;	
	private String estado;	
	private long resumen;
	private String area;
	 private boolean selected;
	
	public ResumenBean() {
		// TODO Auto-generated constructor stub
	}

	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}


	public int getNcodarea() {
		return ncodarea;
	}


	public void setNcodarea(int ncodarea) {
		this.ncodarea = ncodarea;
	}


	public long getResumen() {
		return resumen;
	}


	public void setResumen(long resumen) {
		this.resumen = resumen;
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	

	
	
	

}
