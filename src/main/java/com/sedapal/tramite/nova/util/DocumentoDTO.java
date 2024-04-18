package com.sedapal.tramite.nova.util;

//SED-FON-161
public class DocumentoDTO {
	private String tipo_documento;
	private String numero_documento;
	
	
	public String getTipo_documento() {
		return tipo_documento;
	}
	public void setTipo_documento(String tipo_documento) {
		this.tipo_documento = tipo_documento;
	}
	public String getNumero_documento() {
		return numero_documento;
	}
	public void setNumero_documento(String numero_documento) {
		this.numero_documento = numero_documento;
	}
	
	public DocumentoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DocumentoDTO(String tipo_documento, String numero_documento) {
		super();
		this.tipo_documento = tipo_documento;
		this.numero_documento = numero_documento;
	}
	
	
}
