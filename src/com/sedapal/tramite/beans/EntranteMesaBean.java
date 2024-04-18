package com.sedapal.tramite.beans;

import java.sql.Time;
import java.util.Date;

public class EntranteMesaBean {
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
    private Date dfecregistro; 
    private Time dhoraregistro;
    private Time dhoraderivacion;
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
	
	// agrega Eli Diaz 14/05/2020
	private String sistema;
    private int nfolio;
    // agregamos variables 21/10/2020
    private int nrecepcion;
	private Date dfecharecepcion;
	// agregamos nuevas variables 18/05/2021
	private String vtipoorigen;
	private String vdato1;
	private int ndato1;	
	// agregamos nuevas linea digitalizacion 07/09/2022
	private String vlote;
	private String vnumero_disco;
	private String vresdisco;
	private Date dfecdisco;
	private int nvalor_legal;
	
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
	public Date getDfecdoc() {
		return dfecdoc;
	}
	public void setDfecdoc(Date dfecdoc) {
		this.dfecdoc = dfecdoc;
	}
	public Date getDfecregistro() {
		return dfecregistro;
	}
	public void setDfecregistro(Date dfecregistro) {
		this.dfecregistro = dfecregistro;
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
	public Time getDhoraregistro() {
		return dhoraregistro;
	}
	public void setDhoraregistro(Time dhoraregistro) {
		this.dhoraregistro = dhoraregistro;
	}
	public int getNrecepcion() {
		return nrecepcion;
	}
	public void setNrecepcion(int nrecepcion) {
		this.nrecepcion = nrecepcion;
	}
	public Date getDfecharecepcion() {
		return dfecharecepcion;
	}
	public void setDfecharecepcion(Date dfecharecepcion) {
		this.dfecharecepcion = dfecharecepcion;
	}
	public Time getDhoraderivacion() {
		return dhoraderivacion;
	}
	public void setDhoraderivacion(Time dhoraderivacion) {
		this.dhoraderivacion = dhoraderivacion;
	}
	public String getVtipoorigen() {
		return vtipoorigen;
	}
	public void setVtipoorigen(String vtipoorigen) {
		this.vtipoorigen = vtipoorigen;
	}
	public String getVdato1() {
		return vdato1;
	}
	public void setVdato1(String vdato1) {
		this.vdato1 = vdato1;
	}
	public int getNdato1() {
		return ndato1;
	}
	public void setNdato1(int ndato1) {
		this.ndato1 = ndato1;
	}
	
	
	public String getVlote() {
		return vlote;
	}
	public void setVlote(String vlote) {
		this.vlote = vlote;
	}
	public String getVnumero_disco() {
		return vnumero_disco;
	}
	public void setVnumero_disco(String vnumero_disco) {
		this.vnumero_disco = vnumero_disco;
	}
	public String getVresdisco() {
		return vresdisco;
	}
	public void setVresdisco(String vresdisco) {
		this.vresdisco = vresdisco;
	}
	public Date getDfecdisco() {
		return dfecdisco;
	}
	public void setDfecdisco(Date dfecdisco) {
		this.dfecdisco = dfecdisco;
	}
	public int getNvalor_legal() {
		return nvalor_legal;
	}
	public void setNvalor_legal(int nvalor_legal) {
		this.nvalor_legal = nvalor_legal;
	}
	
		
	
	
	
	
	
		
}