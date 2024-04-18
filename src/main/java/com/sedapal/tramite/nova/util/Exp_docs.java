package com.sedapal.tramite.nova.util;

//SED-FON-161
public class Exp_docs {
	private String ruta;
	private Integer idDocumento;
	private String nomDocumento;
	private String tipoDocumental;
	private Integer principal;
	
	public Exp_docs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Exp_docs(String ruta, String documento){
		super();
		this.nomDocumento = documento;
		this.ruta = ruta;
		this.idDocumento=0;
		this.tipoDocumental="";
		this.principal=1;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNomDocumento() {
		return nomDocumento;
	}

	public void setNomDocumento(String nomDocumento) {
		this.nomDocumento = nomDocumento;
	}

	public String getTipoDocumental() {
		return tipoDocumental;
	}

	public void setTipoDocumental(String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}

	public Integer getPrincipal() {
		return principal;
	}

	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}

	
	  
}
