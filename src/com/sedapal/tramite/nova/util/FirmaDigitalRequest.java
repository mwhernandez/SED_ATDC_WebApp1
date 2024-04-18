package com.sedapal.tramite.nova.util;

import java.util.ArrayList;
import java.util.List;

public class FirmaDigitalRequest {

	private List<String >archivos;
	private String imagen;
	private String rutaOrigen;
	private String rutaImagenes;
	private String rutaDestino;
	private String listarArchivos;
	private String activarDescripcion;
	private String visible;
	private String posicionFirma;
	private String ubicacionPagina;
	private String numeroPagina;
	private String estiloFirma;
	private String altoRubrica;
	private String anchoRubrica;
	private String aplicarImagen;
	private String tamanoFuente;
	private String tipoFirma;
	private String razon;
	private String cargo;
	private String comentario;
	private String nombreImagen;
	private Boolean invisible;
	private String alias;
	
	
	public FirmaDigitalRequest() {
		super();
		// TODO Auto-generated constructor stub
		this.archivos= new ArrayList<String>();
		this.setEstiloFirma("ID");
		this.setPosicionFirma("SD");
		this.setRazon("Firma");
		this.setTamanoFuente("8");
		this.setTipoFirma("1");
		this.setUbicacionPagina("PP");
		this.setInvisible(false);	
		this.setImagen("sedapal-logo.png");
		this.setListarArchivos("1");
		this.setActivarDescripcion("0");
		this.setVisible("1");
		this.setAplicarImagen("0");
	}

	public List<String> getArchivos() {
		return archivos;
	}


	public void setArchivos(List<String> archivos) {
		this.archivos = archivos;
	}


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	public String getRutaOrigen() {
		return rutaOrigen;
	}


	public void setRutaOrigen(String rutaOrigen) {
		this.rutaOrigen = rutaOrigen;
	}


	public String getRutaImagenes() {
		return rutaImagenes;
	}


	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}


	public String getRutaDestino() {
		return rutaDestino;
	}


	public void setRutaDestino(String rutaDestino) {
		this.rutaDestino = rutaDestino;
	}


	public String getListarArchivos() {
		return listarArchivos;
	}


	public void setListarArchivos(String listarArchivos) {
		this.listarArchivos = listarArchivos;
	}


	public String getActivarDescripcion() {
		return activarDescripcion;
	}


	public void setActivarDescripcion(String activarDescripcion) {
		this.activarDescripcion = activarDescripcion;
	}


	public String getVisible() {
		return visible;
	}


	public void setVisible(String visible) {
		this.visible = visible;
	}


	public String getPosicionFirma() {
		return posicionFirma;
	}


	public void setPosicionFirma(String posicionFirma) {
		this.posicionFirma = posicionFirma;
	}


	public String getUbicacionPagina() {
		return ubicacionPagina;
	}


	public void setUbicacionPagina(String ubicacionPagina) {
		this.ubicacionPagina = ubicacionPagina;
	}


	public String getNumeroPagina() {
		return numeroPagina;
	}


	public void setNumeroPagina(String numeroPagina) {
		this.numeroPagina = numeroPagina;
	}


	public String getEstiloFirma() {
		return estiloFirma;
	}


	public void setEstiloFirma(String estiloFirma) {
		this.estiloFirma = estiloFirma;
	}


	public String getAltoRubrica() {
		return altoRubrica;
	}


	public void setAltoRubrica(String altoRubrica) {
		this.altoRubrica = altoRubrica;
	}


	public String getAnchoRubrica() {
		return anchoRubrica;
	}


	public void setAnchoRubrica(String anchoRubrica) {
		this.anchoRubrica = anchoRubrica;
	}


	public String getAplicarImagen() {
		return aplicarImagen;
	}


	public void setAplicarImagen(String aplicarImagen) {
		this.aplicarImagen = aplicarImagen;
	}


	public String getTamanoFuente() {
		return tamanoFuente;
	}


	public void setTamanoFuente(String tamanoFuente) {
		this.tamanoFuente = tamanoFuente;
	}


	public String getTipoFirma() {
		return tipoFirma;
	}


	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}


	public String getRazon() {
		return razon;
	}


	public void setRazon(String razon) {
		this.razon = razon;
	}


	public String getCargo() {
		return cargo;
	}


	public void setCargo(String cargo) {
		this.cargo = cargo;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	public String getNombreImagen() {
		return nombreImagen;
	}


	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}


	public Boolean getInvisible() {
		return invisible;
	}


	public void setInvisible(Boolean invisible) {
		this.invisible = invisible;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}

	
    
    
}
