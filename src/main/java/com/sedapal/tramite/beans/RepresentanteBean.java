package com.sedapal.tramite.beans;

import java.io.Serializable;
import java.util.Date;

public class RepresentanteBean implements Serializable{
	private int codremitente;
	private int codrepresentante;
    private String vtiporepresentante;    
    private String vtipodocumento;
    private String vnumerodocumento;
    private String vnombre;
    private String vdireccion;
    private String vcorreo;
    private String vtelefono;
    private String vfax;
    private String vcelular;
    private String vestado;
    private int indicador;
    private Date feccre;
    private Date fecact;
    private String vrescre;
    private String vresact;
    private boolean selected;
    private String estado;
    
    
	public int getCodremitente() {
		return codremitente;
	}
	public void setCodremitente(int codremitente) {
		this.codremitente = codremitente;
	}
	public int getCodrepresentante() {
		return codrepresentante;
	}
	public void setCodrepresentante(int codrepresentante) {
		this.codrepresentante = codrepresentante;
	}
	public String getVtiporepresentante() {
		return vtiporepresentante;
	}
	public void setVtiporepresentante(String vtiporepresentante) {
		this.vtiporepresentante = vtiporepresentante;
	}
	public String getVtipodocumento() {
		return vtipodocumento;
	}
	public void setVtipodocumento(String vtipodocumento) {
		this.vtipodocumento = vtipodocumento;
	}
	public String getVnumerodocumento() {
		return vnumerodocumento;
	}
	public void setVnumerodocumento(String vnumerodocumento) {
		this.vnumerodocumento = vnumerodocumento;
	}
	public String getVnombre() {
		return vnombre;
	}
	public void setVnombre(String vnombre) {
		this.vnombre = vnombre;
	}
	public String getVdireccion() {
		return vdireccion;
	}
	public void setVdireccion(String vdireccion) {
		this.vdireccion = vdireccion;
	}
	public String getVcorreo() {
		return vcorreo;
	}
	public void setVcorreo(String vcorreo) {
		this.vcorreo = vcorreo;
	}
	public String getVtelefono() {
		return vtelefono;
	}
	public void setVtelefono(String vtelefono) {
		this.vtelefono = vtelefono;
	}
	public String getVfax() {
		return vfax;
	}
	public void setVfax(String vfax) {
		this.vfax = vfax;
	}
	public String getVcelular() {
		return vcelular;
	}
	public void setVcelular(String vcelular) {
		this.vcelular = vcelular;
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
	public Date getFeccre() {
		return feccre;
	}
	public void setFeccre(Date feccre) {
		this.feccre = feccre;
	}
	public Date getFecact() {
		return fecact;
	}
	public void setFecact(Date fecact) {
		this.fecact = fecact;
	}
	public String getVrescre() {
		return vrescre;
	}
	public void setVrescre(String vrescre) {
		this.vrescre = vrescre;
	}
	public String getVresact() {
		return vresact;
	}
	public void setVresact(String vresact) {
		this.vresact = vresact;
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
	


}
