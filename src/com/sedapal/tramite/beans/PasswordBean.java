package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class PasswordBean implements Serializable{
	
	private String login;
	private int ficha;
	private String nombre;
	private String estado;
	private String indicador;
    private String password; 
    private String passwordNuevo;
    private String passwordConfirmar;
    private Date fecha;
    private int zona;
    private int ncodarea;
    private String nomequipo;    
    private String perfil;
    private String sistema;
    private String reponsable;
    private int ncodperfil;
    //private String baja;
    private boolean selected;
    
    
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
	public String getIndicador() {
		return indicador;
	}
	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}  
		
	public Date getFecha(){
		return fecha;
	}
	public void setFecha(Date fecha){
		this.fecha= fecha;
	}
	public int getZona() {
		return zona;
	}
	public void setZona(int zona) {
		this.zona = zona;
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
	
	public int getNcodarea() {
		return ncodarea;
	}
	public void setNcodarea(int ncodarea) {
		this.ncodarea = ncodarea;
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
	
	public String getReponsable() {
		return reponsable;
	}
	public void setReponsable(String reponsable) {
		this.reponsable = reponsable;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getNcodperfil() {
		return ncodperfil;
	}
	public void setNcodperfil(int ncodperfil) {
		this.ncodperfil = ncodperfil;
	}
	public String getPasswordNuevo() {
		return passwordNuevo;
	}
	public void setPasswordNuevo(String passwordNuevo) {
		this.passwordNuevo = passwordNuevo;
	}
	public String getPasswordConfirmar() {
		return passwordConfirmar;
	}
	public void setPasswordConfirmar(String passwordConfirmar) {
		this.passwordConfirmar = passwordConfirmar;
	}
	

}
