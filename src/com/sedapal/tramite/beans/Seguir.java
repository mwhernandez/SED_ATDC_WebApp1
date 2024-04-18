package com.sedapal.tramite.beans;

public class Seguir {

	private String area;
	private String codArea;
	private String nombreTrabajador;
	private String ficha;
	private boolean selected;
	private String estado;
	private int ncodarea;
	 //SED-FON-161 INI
	private int nIndRemitente; // 2=en bpm
	private String vtipdoc;
	private String vnumdoc;
	
	

	public String getVtipdoc() {
		return vtipdoc;
	}

	public void setVtipdoc(String vtipdoc) {
		this.vtipdoc = vtipdoc;
	}

	public String getVnumdoc() {
		return vnumdoc;
	}

	public void setVnumdoc(String vnumdoc) {
		this.vnumdoc = vnumdoc;
	}

	public int getnIndRemitente() {
		return nIndRemitente;
	}

	public void setnIndRemitente(int nIndRemitente) {
		this.nIndRemitente = nIndRemitente;
	}
	 //SED-FON-161 FIN
	public Seguir() {
		// TODO Auto-generated constructor stub
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFicha() {
		return ficha;
	}

	public void setFicha(String ficha) {
		this.ficha = ficha;
	}

	public String getCodArea() {
		return codArea;
	}

	public void setCodArea(String codArea) {
		this.codArea = codArea;
	}

	public String getNombreTrabajador() {
		return nombreTrabajador;
	}

	public void setNombreTrabajador(String nombreTrabajador) {
		this.nombreTrabajador = nombreTrabajador;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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
	
	

}
