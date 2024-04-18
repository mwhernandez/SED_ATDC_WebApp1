package com.sedapal.tramite.beans;

public class AreaBean {
	private int codigo;
	private String centro;
    private String nombre;
    private String abreviatura;
    private String tipo;
    private String estado;
    private long ficha;
    private boolean selected;
    
    public AreaBean() {
		// TODO Auto-generated constructor stub
	}
    
    public AreaBean(String codigo, String nombre) {
		// TODO Auto-generated constructor stub
    	this.codigo = Integer.parseInt(codigo);
    	this.nombre = nombre;
	}
    
    public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getCentro(){
		return centro;
	}
	public void setCentro(String centro) {
		this.centro = centro;
	}
	public String getNombre(){
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	public String getAbreviatura(){
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	
	public String getTipo(){
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getEstado(){
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public long getFicha() {
		return ficha;
	}

	public void setFicha(long ficha) {
		this.ficha = ficha;
	}
	
}
