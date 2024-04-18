package com.sedapal.tramite.beans;

import java.io.Serializable;

public class ProductoDisponibleBean implements Serializable{
  private int ncodproducto;
  private int ncodempresa;
  private int ncodcentro;  
  private int nsecuencial;
  private int nobjeto;
  private double medida;
  private int estado;  
  private String usuario;   
  private boolean selected;


public boolean isSelected() {
	return selected;
}
public void setSelected(boolean selected) {
	this.selected = selected;
}
public int getNcodproducto() {
	return ncodproducto;
}
public void setNcodproducto(int ncodproducto) {
	this.ncodproducto = ncodproducto;
}
public int getNcodempresa() {
	return ncodempresa;
}
public void setNcodempresa(int ncodempresa) {
	this.ncodempresa = ncodempresa;
}
public int getNcodcentro() {
	return ncodcentro;
}
public void setNcodcentro(int ncodcentro) {
	this.ncodcentro = ncodcentro;
}
public int getNsecuencial() {
	return nsecuencial;
}
public void setNsecuencial(int nsecuencial) {
	this.nsecuencial = nsecuencial;
}
public int getNobjeto() {
	return nobjeto;
}
public void setNobjeto(int nobjeto) {
	this.nobjeto = nobjeto;
}
public double getMedida() {
	return medida;
}
public void setMedida(double medida) {
	this.medida = medida;
}
public int getEstado() {
	return estado;
}
public void setEstado(int estado) {
	this.estado = estado;
}
public String getUsuario() {
	return usuario;
}
public void setUsuario(String usuario) {
	this.usuario = usuario;
}


  
}
