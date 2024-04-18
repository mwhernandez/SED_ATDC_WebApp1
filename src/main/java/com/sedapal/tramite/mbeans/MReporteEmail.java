package com.sedapal.tramite.mbeans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class MReporteEmail {

	public String codigo;
	
	public MReporteEmail() {
		// TODO Auto-generated constructor stub
	}

	public String getCodigo() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String sCodigo =(String)session.getAttribute("sCod");
		this.codigo = sCodigo;
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	

}
