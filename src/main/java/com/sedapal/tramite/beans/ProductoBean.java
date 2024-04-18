package com.sedapal.tramite.beans;

import java.io.Serializable;

public class ProductoBean implements Serializable{
  private int codigo;
  private int centro;  
  private int tipo;
  private String descripcion;
  private double precio;
  private int stock;
  private String status;
  private boolean selected;

public int getCodigo() {
	return codigo;
}
public void setCodigo(int codigo) {
	this.codigo = codigo;
}
public int getCentro(){
	return centro;
}
public void setCentro(int centro){
	this.centro=centro;
}
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
public double getPrecio() {
	return precio;
}
public void setPrecio(double precio) {
	this.precio = precio;
}

public int getStock() {
	return stock;
}
public void setStock(int stock) {
	this.stock = stock;
}
public int getTipo() {
	return tipo;
}

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public boolean isSelected() {
	return selected;
}
public void setSelected(boolean selected) {
	this.selected = selected;
}


  
}
