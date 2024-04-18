package com.sedapal.tramite.beans;

import java.util.Date;
import java.util.List;

public class SeguimientoEntranteBean {
	private boolean selected;	
	private String fichas;
	private String areas;
	private int ano;
	private int norigen;	
	private String origen;	
	private String tipodoc;	
	private String destipodoc;	
	private long correlativo;
	private String numdoc;	
	private String asunto;	
	private long ficha_dirigido;
	private long derivado;
	private Date fecderivado;
	private String observacion;	
	private Date fecrecepcion;
	private Date fecplazo;
	private int ndiasplazo;
	private String estado;
	private Date feccre;
	private Date fecact;
	private String rescre;
	private String resact;
	private String nombre_derivado;
	private String accion;
	private String comentario;
	private int seguimiento;
	private String vestado;
	private String vaccion;
	private int areaderivado;
	private int arearemitente;
	private int ficharemitente;
	private String nombre_remitente;
	private String abrevderivado;
	private String abrevremite;
	private int indicador;
	//
	private long seguimiento_doc;
	private String numero_doc;
	private Date feccha_doc;
	private String abreviatura_doc;
	private String nombre_deriva_doc;
	private String accion_doc;
	private String comentario_doc;
	private String abreviaturare_doc;
	private String nombre_remite_doc;
	private String estado_doc;
	//
	private String ubicaarchivo;
	private int indicaarchivo;	
	private boolean atachment = false;
	//
	private int nrecepcion;
	private int nplazo;
	private String vprioridad;
	//agrega Eli Diaz 14/11/2019
	private String ubicacion_seguimiento;
		
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getTipodoc() {
		return tipodoc;
	}
	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}
	
	public String getNumdoc() {
		return numdoc;
	}
	public void setNumdoc(String numdoc) {
		this.numdoc = numdoc;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public long getFicha_dirigido() {
		return ficha_dirigido;
	}
	public void setFicha_dirigido(long fichaDirigido) {
		ficha_dirigido = fichaDirigido;
	}
	public long getDerivado() {
		return derivado;
	}
	public void setDerivado(long derivado) {
		this.derivado = derivado;
	}
	public Date getFecderivado() {
		return fecderivado;
	}
	public void setFecderivado(Date fecderivado) {
		this.fecderivado = fecderivado;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Date getFecrecepcion() {
		return fecrecepcion;
	}
	public void setFecrecepcion(Date fecrecepcion) {
		this.fecrecepcion = fecrecepcion;
	}
	public Date getFecplazo() {
		return fecplazo;
	}
	public void setFecplazo(Date fecplazo) {
		this.fecplazo = fecplazo;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
	public String getRescre() {
		return rescre;
	}
	public void setRescre(String rescre) {
		this.rescre = rescre;
	}
	public String getResact() {
		return resact;
	}
	public void setResact(String resact) {
		this.resact = resact;
	}
	public String getNombre_derivado() {
		return nombre_derivado;
	}
	public void setNombre_derivado(String nombreDerivado) {
		nombre_derivado = nombreDerivado;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
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
	public long getCorrelativo() {
		return correlativo;
	}
	public void setCorrelativo(long correlativo) {
		this.correlativo = correlativo;
	}
	
	public int getSeguimiento() {
		return seguimiento;
	}
	public void setSeguimiento(int seguimiento) {
		this.seguimiento = seguimiento;
	}
	public String getVaccion() {
		return vaccion;
	}
	public void setVaccion(String vaccion) {
		this.vaccion = vaccion;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public int getAreaderivado() {
		return areaderivado;
	}
	public void setAreaderivado(int areaderivado) {
		this.areaderivado = areaderivado;
	}
	public int getArearemitente() {
		return arearemitente;
	}
	public void setArearemitente(int arearemitente) {
		this.arearemitente = arearemitente;
	}
	public int getFicharemitente() {
		return ficharemitente;
	}
	public void setFicharemitente(int ficharemitente) {
		this.ficharemitente = ficharemitente;
	}
	public String getNombre_remitente() {
		return nombre_remitente;
	}
	public void setNombre_remitente(String nombreRemitente) {
		nombre_remitente = nombreRemitente;
	}
	public String getDestipodoc() {
		return destipodoc;
	}
	public void setDestipodoc(String destipodoc) {
		this.destipodoc = destipodoc;
	}
	public String getAbrevderivado() {
		return abrevderivado;
	}
	public void setAbrevderivado(String abrevderivado) {
		this.abrevderivado = abrevderivado;
	}
	public String getAbrevremite() {
		return abrevremite;
	}
	public void setAbrevremite(String abrevremite) {
		this.abrevremite = abrevremite;
	}
	public long getSeguimiento_doc() {
		return seguimiento_doc;
	}
	public void setSeguimiento_doc(long seguimientoDoc) {
		seguimiento_doc = seguimientoDoc;
	}
	public String getNumero_doc() {
		return numero_doc;
	}
	public void setNumero_doc(String numeroDoc) {
		numero_doc = numeroDoc;
	}
	public Date getFeccha_doc() {
		return feccha_doc;
	}
	public void setFeccha_doc(Date fecchaDoc) {
		feccha_doc = fecchaDoc;
	}
	public String getAbreviatura_doc() {
		return abreviatura_doc;
	}
	public void setAbreviatura_doc(String abreviaturaDoc) {
		abreviatura_doc = abreviaturaDoc;
	}
	public String getNombre_deriva_doc() {
		return nombre_deriva_doc;
	}
	public void setNombre_deriva_doc(String nombreDerivaDoc) {
		nombre_deriva_doc = nombreDerivaDoc;
	}
	public String getAccion_doc() {
		return accion_doc;
	}
	public void setAccion_doc(String accionDoc) {
		accion_doc = accionDoc;
	}
	public String getComentario_doc() {
		return comentario_doc;
	}
	public void setComentario_doc(String comentarioDoc) {
		comentario_doc = comentarioDoc;
	}
	public String getAbreviaturare_doc() {
		return abreviaturare_doc;
	}
	public void setAbreviaturare_doc(String abreviaturareDoc) {
		abreviaturare_doc = abreviaturareDoc;
	}
	public String getNombre_remite_doc() {
		return nombre_remite_doc;
	}
	public void setNombre_remite_doc(String nombreRemiteDoc) {
		nombre_remite_doc = nombreRemiteDoc;
	}
	public String getEstado_doc() {
		return estado_doc;
	}
	public void setEstado_doc(String estadoDoc) {
		estado_doc = estadoDoc;
	}
	public int getNorigen() {
		return norigen;
	}
	public void setNorigen(int norigen) {
		this.norigen = norigen;
	}
	public int getIndicador() {
		return indicador;
	}
	public void setIndicador(int indicador) {
		this.indicador = indicador;
	}
	public String getUbicaarchivo() {
		return ubicaarchivo;
	}
	public void setUbicaarchivo(String ubicaarchivo) {
		this.ubicaarchivo = ubicaarchivo;
	}
	public int getIndicaarchivo() {
		return indicaarchivo;
	}
	public void setIndicaarchivo(int indicaarchivo) {
		this.indicaarchivo = indicaarchivo;
	}
	
	public boolean isAtachment() {
		if(this.indicaarchivo==0)
			atachment = false;
		else
			atachment = true;
		return atachment;
	}
	public void setAtachment(boolean atachment) {
		this.atachment = atachment;
	}
	public String getFichas() {
		return fichas;
	}
	public void setFichas(String fichas) {
		this.fichas = fichas;
	}
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public int getNrecepcion() {
		return nrecepcion;
	}
	public void setNrecepcion(int nrecepcion) {
		this.nrecepcion = nrecepcion;
	}
	public int getNplazo() {
		return nplazo;
	}
	public void setNplazo(int nplazo) {
		this.nplazo = nplazo;
	}
	public String getVprioridad() {
		return vprioridad;
	}
	public void setVprioridad(String vprioridad) {
		this.vprioridad = vprioridad;
	}
	public String getUbicacion_seguimiento() {
		return ubicacion_seguimiento;
	}
	public void setUbicacion_seguimiento(String ubicacion_seguimiento) {
		this.ubicacion_seguimiento = ubicacion_seguimiento;
	}
	
	
	
	
	
		
}