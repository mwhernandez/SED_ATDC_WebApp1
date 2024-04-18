package com.sedapal.tramite.beans;

public class GrupoBean {
	private int codigo;
	private String centro;
    private String nombre;
    private String abreviatura;
    private String tipo;
    private int nestado;
    private int indicador;
    private long ficha;
    private String descripcion;
    private String estado;
    private String respcrea;
    private String respact;
    private String vdirigido;
    private boolean selected;
    
    public GrupoBean() {
		// TODO Auto-generated constructor stub
	}
    
    public GrupoBean(String codigo, String nombre) {
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

	public int getNestado() {
		return nestado;
	}

	public void setNestado(int nestado) {
		this.nestado = nestado;
	}

	public int getIndicador() {
		return indicador;
	}

	public void setIndicador(int indicador) {
		this.indicador = indicador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRespcrea() {
		return respcrea;
	}

	public void setRespcrea(String respcrea) {
		this.respcrea = respcrea;
	}

	public String getRespact() {
		return respact;
	}

	public void setRespact(String respact) {
		this.respact = respact;
	}

	public String getVdirigido() {
		return vdirigido;
	}

	public void setVdirigido(String vdirigido) {
		this.vdirigido = vdirigido;
	}
	
	
	
}
