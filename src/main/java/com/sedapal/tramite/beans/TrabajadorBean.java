package com.sedapal.tramite.beans;

public class TrabajadorBean {
	private int codigo;
	private long ficha;
	private String nombre;
	private String apellidopaterno;
	private String apellidomaterno;
	private String nombre_completo;	
	private int anexo;
	private int onomastico;	
	private String mes;
	private String cargo;
	private String correo;
	private int area;
	private String equipo;
	private int ncodlocal;
	private String local;
	private String tipo;
	  
    private boolean selected;
    
    
    
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public long getFicha() {
		return ficha;
	}
	public void setFicha(long ficha) {
		this.ficha = ficha;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidopaterno() {
		return apellidopaterno;
	}
	public void setApellidopaterno(String apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}
	public String getApellidomaterno() {
		return apellidomaterno;
	}
	public void setApellidomaterno(String apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
	}
	public String getNombre_completo() {
		return nombre_completo;
	}
	public void setNombre_completo(String nombreCompleto) {
		nombre_completo = nombreCompleto;
	}
	public int getAnexo() {
		return anexo;
	}
	public void setAnexo(int anexo) {
		this.anexo = anexo;
	}
	
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public int getNcodlocal() {
		return ncodlocal;
	}
	public void setNcodlocal(int ncodlocal) {
		this.ncodlocal = ncodlocal;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public int getOnomastico() {
		return onomastico;
	}
	public void setOnomastico(int onomastico) {
		this.onomastico = onomastico;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
