package com.sedapal.tramite.beans;

import java.sql.Time;
import java.util.Date;

public class EntranteMesaVirtualBean {
	private boolean selected;
	private int nano;
	private int norigen;
	private String origen;
	private String vtipodoc;
	private String tipodoc;
	private long ncorrelativo;
	private String vnumdoc;
	private String vmesaparte;
	private int nremitente;
	private String vremitente;
	private int ndirigido;
	private String vdirigido;
	private int ncodcentro;
	private String centro;
	private int ncodarea;
	private String area;
    private Date dfecdoc;
    private Time dhoradoc;
    private Date dfecregistro;    
	private String vasunto;
	private String vreferencia;
	private String vobservacion;
	private String vaccion;
	private int naccion;
	private String accion;
	private String vprioridad;
	private String vubiarchivo;
	private Date dfecplazo;	
	private int ndiasplazo;
	private String vestado;
	private int nindicador;
	private Date dfeccre;
	private Date dfecact;
	private String vrescre;
	private String vresact;
	private long ficha_dirigido;
	private String area_origen; 
	private int ncodarea_origen;
	private long ficha_derivado;
	private String nestado;	
	private String empresa;
	private String respresentante;
	private boolean atachment = false;
	private String nombreremitente;
	//agrego nuevas variables 09/03/2017
	private String vanoentrada;
	private String opcion_seguimiento;
	private String doc_entrada;
	// agrega Eli Diaz 14/11/2019
	private String ubicacion_mesa;
	private String correo;
	private int ntelefono;
	private String direccion;
	private String vreferenciadireccion;
	private String Area_dirigido_correo;
	private String tipodocumento;
	private String tipoingreso;
	private String motivoingreso;
	private Date dfecingresodoc;
	private Double ndirefenciahora;
	private Time dhoraregistro;
    private Time dhoraderivacion;
    private Time dhoradeatencion;
	
	
	
	// agrega Eli Diaz 14/05/2020
	private String sistema;
    private int nfolio;
    
    //agrega Eli Diaz 07/10/2021
    private String vtipoingreso;
    private String vmotivoingreso;
	
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getNano() {
		return nano;
	}
	public void setNano(int nano) {
		this.nano = nano;
	}
	public int getNorigen() {
		return norigen;
	}
	public void setNorigen(int norigen) {
		this.norigen = norigen;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getVtipodoc() {
		return vtipodoc;
	}
	public void setVtipodoc(String vtipodoc) {
		this.vtipodoc = vtipodoc;
	}
	public String getTipodoc() {
		return tipodoc;
	}
	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}
	
	public long getNcorrelativo() {
		return ncorrelativo;
	}
	public void setNcorrelativo(long ncorrelativo) {
		this.ncorrelativo = ncorrelativo;
	}
	public String getVnumdoc() {
		return vnumdoc;
	}
	public void setVnumdoc(String vnumdoc) {
		this.vnumdoc = vnumdoc;
	}
	public String getVmesaparte() {
		return vmesaparte;
	}
	public void setVmesaparte(String vmesaparte) {
		this.vmesaparte = vmesaparte;
	}
	public int getNremitente() {
		return nremitente;
	}
	public void setNremitente(int nremitente) {
		this.nremitente = nremitente;
	}
	public String getVremitente() {
		return vremitente;
	}
	public void setVremitente(String vremitente) {
		this.vremitente = vremitente;
	}
	public int getNdirigido() {
		return ndirigido;
	}
	public void setNdirigido(int ndirigido) {
		this.ndirigido = ndirigido;
	}
	public String getVdirigido() {
		return vdirigido;
	}
	public void setVdirigido(String vdirigido) {
		this.vdirigido = vdirigido;
	}
	public int getNcodcentro() {
		return ncodcentro;
	}
	public void setNcodcentro(int ncodcentro) {
		this.ncodcentro = ncodcentro;
	}
	public String getCentro() {
		return centro;
	}
	public void setCentro(String centro) {
		this.centro = centro;
	}
	public int getNcodarea() {
		return ncodarea;
	}
	public void setNcodarea(int ncodarea) {
		this.ncodarea = ncodarea;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	
	public Date getDfecregistro() {
		return dfecregistro;
	}
	
	public String getVasunto() {
		return vasunto;
	}
	public void setVasunto(String vasunto) {
		this.vasunto = vasunto;
	}
	public String getVreferencia() {
		return vreferencia;
	}
	public void setVreferencia(String vreferencia) {
		this.vreferencia = vreferencia;
	}
	public String getVobservacion() {
		return vobservacion;
	}
	public void setVobservacion(String vobservacion) {
		this.vobservacion = vobservacion;
	}
	public String getVaccion() {
		return vaccion;
	}
	public void setVaccion(String vaccion) {
		this.vaccion = vaccion;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getVprioridad() {
		return vprioridad;
	}
	public void setVprioridad(String vprioridad) {
		this.vprioridad = vprioridad;
	}
	public String getVubiarchivo() {
		return vubiarchivo;
	}
	public void setVubiarchivo(String vubiarchivo) {
		this.vubiarchivo = vubiarchivo;
	}
	public Date getDfecplazo() {
		return dfecplazo;
	}
	public void setDfecplazo(Date dfecplazo) {
		this.dfecplazo = dfecplazo;
	}
	public int getNdiasplazo() {
		return ndiasplazo;
	}
	public void setNdiasplazo(int ndiasplazo) {
		this.ndiasplazo = ndiasplazo;
	}
	public String getVestado() {
		return vestado;
	}
	public void setVestado(String vestado) {
		this.vestado = vestado;
	}
	public int getNindicador() {
		return nindicador;
	}
	public void setNindicador(int nindicador) {
		this.nindicador = nindicador;
	}
	public Date getDfeccre() {
		return dfeccre;
	}
	public void setDfeccre(Date dfeccre) {
		this.dfeccre = dfeccre;
	}
	public Date getDfecact() {
		return dfecact;
	}
	public void setDfecact(Date dfecact) {
		this.dfecact = dfecact;
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
	public long getFicha_dirigido() {
		return ficha_dirigido;
	}
	public void setFicha_dirigido(long fichaDirigido) {
		ficha_dirigido = fichaDirigido;
	}
	public String getArea_origen() {
		return area_origen;
	}
	public void setArea_origen(String areaOrigen) {
		area_origen = areaOrigen;
	}
	public int getNcodarea_origen() {
		return ncodarea_origen;
	}
	public void setNcodarea_origen(int ncodareaOrigen) {
		ncodarea_origen = ncodareaOrigen;
	}
	public long getFicha_derivado() {
		return ficha_derivado;
	}
	public void setFicha_derivado(long fichaDerivado) {
		ficha_derivado = fichaDerivado;
	}
	public String getNestado() {
		return nestado;
	}
	public void setNestado(String nestado) {
		this.nestado = nestado;
	}
	public int getNaccion() {
		return naccion;
	}
	public void setNaccion(int naccion) {
		this.naccion = naccion;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getRespresentante() {
		return respresentante;
	}
	public void setRespresentante(String respresentante) {
		this.respresentante = respresentante;
	}
	public boolean isAtachment() {
		if(this.nindicador==0)
			atachment = false;
		else
			atachment = true;
		return atachment;
	}
	public void setAtachment(boolean atachment) {
		this.atachment = atachment;
	}
	public String getNombreremitente() {
		return nombreremitente;
	}
	public void setNombreremitente(String nombreremitente) {
		this.nombreremitente = nombreremitente;
	}
	public String getVanoentrada() {
		return vanoentrada;
	}
	public void setVanoentrada(String vanoentrada) {
		this.vanoentrada = vanoentrada;
	}
	public String getOpcion_seguimiento() {
		return opcion_seguimiento;
	}
	public void setOpcion_seguimiento(String opcion_seguimiento) {
		this.opcion_seguimiento = opcion_seguimiento;
	}
	public String getDoc_entrada() {
		return doc_entrada;
	}
	public void setDoc_entrada(String doc_entrada) {
		this.doc_entrada = doc_entrada;
	}
	public String getUbicacion_mesa() {
		return ubicacion_mesa;
	}
	public void setUbicacion_mesa(String ubicacion_mesa) {
		this.ubicacion_mesa = ubicacion_mesa;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public int getNfolio() {
		return nfolio;
	}
	public void setNfolio(int nfolio) {
		this.nfolio = nfolio;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public int getNtelefono() {
		return ntelefono;
	}
	public void setNtelefono(int ntelefono) {
		this.ntelefono = ntelefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public void setDfecregistro(Date dfecregistro) {
		this.dfecregistro = dfecregistro;
	}
	public Date getDfecdoc() {
		return dfecdoc;
	}
	public void setDfecdoc(Date dfecdoc) {
		this.dfecdoc = dfecdoc;
	}
	public Time getDhoradoc() {
		return dhoradoc;
	}
	public void setDhoradoc(Time dhoradoc) {
		this.dhoradoc = dhoradoc;
	}
	public String getArea_dirigido_correo() {
		return Area_dirigido_correo;
	}
	public void setArea_dirigido_correo(String area_dirigido_correo) {
		Area_dirigido_correo = area_dirigido_correo;
	}
	public String getVtipoingreso() {
		return vtipoingreso;
	}
	public void setVtipoingreso(String vtipoingreso) {
		this.vtipoingreso = vtipoingreso;
	}
	public String getVmotivoingreso() {
		return vmotivoingreso;
	}
	public void setVmotivoingreso(String vmotivoingreso) {
		this.vmotivoingreso = vmotivoingreso;
	}
	public String getVreferenciadireccion() {
		return vreferenciadireccion;
	}
	public void setVreferenciadireccion(String vreferenciadireccion) {
		this.vreferenciadireccion = vreferenciadireccion;
	}
	public String getTipodocumento() {
		return tipodocumento;
	}
	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}
	public String getTipoingreso() {
		return tipoingreso;
	}
	public void setTipoingreso(String tipoingreso) {
		this.tipoingreso = tipoingreso;
	}
	public String getMotivoingreso() {
		return motivoingreso;
	}
	public void setMotivoingreso(String motivoingreso) {
		this.motivoingreso = motivoingreso;
	}
	public Date getDfecingresodoc() {
		return dfecingresodoc;
	}
	public void setDfecingresodoc(Date dfecingresodoc) {
		this.dfecingresodoc = dfecingresodoc;
	}
	public Double getNdirefenciahora() {
		return ndirefenciahora;
	}
	public void setNdirefenciahora(Double ndirefenciahora) {
		this.ndirefenciahora = ndirefenciahora;
	}
	public Time getDhoraregistro() {
		return dhoraregistro;
	}
	public void setDhoraregistro(Time dhoraregistro) {
		this.dhoraregistro = dhoraregistro;
	}
	public Time getDhoraderivacion() {
		return dhoraderivacion;
	}
	public void setDhoraderivacion(Time dhoraderivacion) {
		this.dhoraderivacion = dhoraderivacion;
	}
	public Time getDhoradeatencion() {
		return dhoradeatencion;
	}
	public void setDhoradeatencion(Time dhoradeatencion) {
		this.dhoradeatencion = dhoradeatencion;
	}
	
	
		
	
		
}