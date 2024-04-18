package com.sedapal.tramite.beans;

import java.io.Serializable;

//SED-FON-161
public class RemitenteBPM  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8863007276300754533L;

	private Long ncorrelativo;
	private String tipoDocumento;
	private String nroDocumento;
	private String nombre;
	private String userCrea;
	private String userAct;
	private String correo;
	private String telefono;
	private String direccion;
	private int indCasilla;
	
	
	public int getIndCasilla() {
		return indCasilla;
	}
	public void setIndCasilla(int indCasilla) {
		this.indCasilla = indCasilla;
	}
	public String getUserCrea() {
		return userCrea;
	}
	public void setUserCrea(String userCrea) {
		this.userCrea = userCrea;
	}
	public String getUserAct() {
		return userAct;
	}
	public void setUserAct(String userAct) {
		this.userAct = userAct;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public Long getNcorrelativo() {
		return ncorrelativo;
	}
	public void setNcorrelativo(Long ncorrelativo) {
		this.ncorrelativo = ncorrelativo;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
