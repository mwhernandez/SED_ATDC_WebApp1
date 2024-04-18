package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.sql.Date;

public class Usuario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6464459953976753166L;
	
	private String login;
	private int ficha;
	private String nombre;
	private String estado;
	private String password;
	private int ncodarea;
	private String nomequipo;
	private String perfil;	
    private String sistema;
    private int ncodperfil;
    private String centro;
    private int ncodcentro;
    private int conexion;
    private String correo;
    private String annio;
    private Date fecha;
    private String direccionip;
    private String ip_desktop;
   
    
    
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getFicha() {
		return ficha;
	}
	public void setFicha(int ficha) {
		this.ficha = ficha;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getCodarea() {
		return ncodarea;
	}
	public void setCodarea(int ncodarea) {
		this.ncodarea = ncodarea;
	}
	
	public String getNomequipo() {
		return nomequipo;
	}
	public void setNomequipo(String nomequipo) {
		this.nomequipo = nomequipo;
	}	
	
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
		
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public int getNcodarea() {
		return ncodarea;
	}
	public void setNcodarea(int ncodarea) {
		this.ncodarea = ncodarea;
	}
	public int getNcodperfil() {
		return ncodperfil;
	}
	public void setNcodperfil(int ncodperfil) {
		this.ncodperfil = ncodperfil;
	}
	public String getCentro() {
		return centro;
	}
	public void setCentro(String centro) {
		this.centro = centro;
	}
	public int getNcodcentro() {
		return ncodcentro;
	}
	public void setNcodcentro(int ncodcentro) {
		this.ncodcentro = ncodcentro;
	}
	public int getConexion() {
		return conexion;
	}
	public void setConexion(int conexion) {
		this.conexion = conexion;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getAnnio() {
		return annio;
	}
	public void setAnnio(String annio) {
		this.annio = annio;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getDireccionip() {
		return direccionip;
	}
	public void setDireccionip(String direccionip) {
		this.direccionip = direccionip;
	}
	public String getIp_desktop() {
		return ip_desktop;
	}
	public void setIp_desktop(String ip_desktop) {
		this.ip_desktop = ip_desktop;
	}
	
	
	
	

}
