package com.sedapal.tramite.beans;

import java.sql.Time;
import java.util.Date;

public class DocumentoSalidaBean {
	private int ano;
	private String anio;
	private int origen;
	private String origenes;
	private String tipodoc;
	private String ntipodoc;
	private int codigo;
	private String numerodoc;
	private int ndirigido;
	private String dirigido;
	private String vdirigidos;
	private String asunto;
	private String observacion;
	private String estado;
	private String nestado;
	private String trabajador;
	private int area;
	private String nom_area;
	private Date fecha;
	private String referencia;
	private String doc_entrada;
	private long ficha_dirigido;
	private long ficha_remitente;
	private String vrescre;
	private String vresact;
	private Date dfeccre;
	private Date dfecact;
	private String prioridad;
	private String ubiarchivo;
	private int indicador;
	private int remitente;
	private Date dfecplazo;
	private int dias;
	private String vaccion;
	private String areaseleccionadas;
	private String quitarareas;
	private String quitarfichas;
	private int indicaadjunto;
	private boolean atachment = false;
	private String nombreremitente;
	///
	private String areas;
	private String indica;
	private String indicadores;
	private String opcion_seguimiento;
	private String codigos;
	///
	private String vanoentrada;
	// agrega Eli Diaz 06/11/2012
	private String valor_entrada;
	// agrega Eli Diaz 14/11/2019
	private String ubicacion_salida;
	private String vcodigo_verificador;
	private int nficha_jefe_equipo;
	private String vnotificador;
	private int ncasilla;
	private Date dfeccasilla;
	private Time dhoracasilla;
	private int fichaVisador;
	private int anexo;

	//
	private String nombrearea;
	private String nombrecompleto;
	private int ficha;
	private String tipodocumento;
	private int ind_remitente;

	private boolean selected;

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getOrigen() {
		return origen;
	}

	public void setOrigen(int origen) {
		this.origen = origen;
	}

	public String getTipodoc() {
		return tipodoc;
	}

	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNumerodoc() {
		return numerodoc;
	}

	public void setNumerodoc(String numerodoc) {
		this.numerodoc = numerodoc;
	}

	public int getNdirigido() {
		return ndirigido;
	}

	public void setNdirigido(int ndirigido) {
		this.ndirigido = ndirigido;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getDirigido() {
		return dirigido;
	}

	public void setDirigido(String dirigido) {
		this.dirigido = dirigido;
	}

	public String getOrigenes() {
		return origenes;
	}

	public void setOrigenes(String origenes) {
		this.origenes = origenes;
	}

	public String getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(String trabajador) {
		this.trabajador = trabajador;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNom_area() {
		return nom_area;
	}

	public void setNom_area(String nomArea) {
		nom_area = nomArea;
	}

	public String getDoc_entrada() {
		return doc_entrada;
	}

	public void setDoc_entrada(String docEntrada) {
		doc_entrada = docEntrada;
	}

	public long getFicha_dirigido() {
		return ficha_dirigido;
	}

	public void setFicha_dirigido(long fichaDirigido) {
		ficha_dirigido = fichaDirigido;
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

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getNtipodoc() {
		return ntipodoc;
	}

	public void setNtipodoc(String ntipodoc) {
		this.ntipodoc = ntipodoc;
	}

	public String getNestado() {
		return nestado;
	}

	public void setNestado(String nestado) {
		this.nestado = nestado;
	}

	public String getUbiarchivo() {
		return ubiarchivo;
	}

	public void setUbiarchivo(String ubiarchivo) {
		this.ubiarchivo = ubiarchivo;
	}

	public int getIndicador() {
		return indicador;
	}

	public void setIndicador(int indicador) {
		this.indicador = indicador;
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

	public int getRemitente() {
		return remitente;
	}

	public void setRemitente(int remitente) {
		this.remitente = remitente;
	}

	public Date getDfecplazo() {
		return dfecplazo;
	}

	public void setDfecplazo(Date dfecplazo) {
		this.dfecplazo = dfecplazo;
	}

	public long getFicha_remitente() {
		return ficha_remitente;
	}

	public void setFicha_remitente(long fichaRemitente) {
		ficha_remitente = fichaRemitente;
	}

	public String getVdirigidos() {
		return vdirigidos;
	}

	public void setVdirigidos(String vdirigidos) {
		this.vdirigidos = vdirigidos;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public String getVaccion() {
		return vaccion;
	}

	public void setVaccion(String vaccion) {
		this.vaccion = vaccion;
	}

	public String getAreaseleccionadas() {
		return areaseleccionadas;
	}

	public void setAreaseleccionadas(String areaseleccionadas) {
		this.areaseleccionadas = areaseleccionadas;
	}

	public int getIndicaadjunto() {
		return indicaadjunto;
	}

	public void setIndicaadjunto(int indicaadjunto) {
		this.indicaadjunto = indicaadjunto;
	}

	public boolean isAtachment() {
		if (this.indicador == 0)
			atachment = false;
		else
			atachment = true;
		return atachment;
	}

	public void setAtachment(boolean atachment) {
		this.atachment = atachment;
	}

	public String getQuitarareas() {
		return quitarareas;
	}

	public void setQuitarareas(String quitarareas) {
		this.quitarareas = quitarareas;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getIndica() {
		return indica;
	}

	public void setIndica(String indica) {
		this.indica = indica;
	}

	public String getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(String indicadores) {
		this.indicadores = indicadores;
	}

	public String getOpcion_seguimiento() {
		return opcion_seguimiento;
	}

	public void setOpcion_seguimiento(String opcionSeguimiento) {
		opcion_seguimiento = opcionSeguimiento;
	}

	public String getCodigos() {
		return codigos;
	}

	public void setCodigos(String codigos) {
		this.codigos = codigos;
	}

	public String getQuitarfichas() {
		return quitarfichas;
	}

	public void setQuitarfichas(String quitarfichas) {
		this.quitarfichas = quitarfichas;
	}

	public String getVanoentrada() {
		return vanoentrada;
	}

	public void setVanoentrada(String vanoentrada) {
		this.vanoentrada = vanoentrada;
	}

	public String getValor_entrada() {
		return valor_entrada;
	}

	public void setValor_entrada(String valorEntrada) {
		valor_entrada = valorEntrada;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getNombreremitente() {
		return nombreremitente;
	}

	public void setNombreremitente(String nombreremitente) {
		this.nombreremitente = nombreremitente;
	}

	public String getUbicacion_salida() {
		return ubicacion_salida;
	}

	public void setUbicacion_salida(String ubicacion_salida) {
		this.ubicacion_salida = ubicacion_salida;
	}

	public String getVcodigo_verificador() {
		return vcodigo_verificador;
	}

	public void setVcodigo_verificador(String vcodigo_verificador) {
		this.vcodigo_verificador = vcodigo_verificador;
	}

	public int getNficha_jefe_equipo() {
		return nficha_jefe_equipo;
	}

	public void setNficha_jefe_equipo(int nficha_jefe_equipo) {
		this.nficha_jefe_equipo = nficha_jefe_equipo;
	}

	public String getVnotificador() {
		return vnotificador;
	}

	public void setVnotificador(String vnotificador) {
		this.vnotificador = vnotificador;
	}

	public int getNcasilla() {
		return ncasilla;
	}

	public void setNcasilla(int ncasilla) {
		this.ncasilla = ncasilla;
	}

	public Date getDfeccasilla() {
		return dfeccasilla;
	}

	public void setDfeccasilla(Date dfeccasilla) {
		this.dfeccasilla = dfeccasilla;
	}

	public Time getDhoracasilla() {
		return dhoracasilla;
	}

	public void setDhoracasilla(Time dhoracasilla) {
		this.dhoracasilla = dhoracasilla;
	}

	public String getNombrearea() {
		return nombrearea;
	}

	public void setNombrearea(String nombrearea) {
		this.nombrearea = nombrearea;
	}

	public String getNombrecompleto() {
		return nombrecompleto;
	}

	public void setNombrecompleto(String nombrecompleto) {
		this.nombrecompleto = nombrecompleto;
	}

	public int getFicha() {
		return ficha;
	}

	public void setFicha(int ficha) {
		this.ficha = ficha;
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public int getInd_remitente() {
		return ind_remitente;
	}

	public void setInd_remitente(int ind_remitente) {
		this.ind_remitente = ind_remitente;
	}

	public int getFichaVisador() {
		return fichaVisador;
	}

	public void setFichaVisador(int fichaVisador) {
		this.fichaVisador = fichaVisador;
	}

	public int getAnexo() {
		return anexo;
	}

	public void setAnexo(int anexo) {
		this.anexo = anexo;
	}

}
