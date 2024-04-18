package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.dao.IRemitenteDAO;


public class ServiciosRemitente implements IServiciosRemitente{
	private IRemitenteDAO remitenteDAO;


	public List<RemitenteBean> catalogo() {
		return remitenteDAO.remitenteSP();
		
	}
	
	public void setRemitenteDAO(IRemitenteDAO remitenteDAO) {
		this.remitenteDAO = remitenteDAO;
	}

	@Override
	public void eliminarRemitente(int codigo) {		
		remitenteDAO.eliminarSP(codigo);
		
	}

	@Override
	public String nuevoRemitente(RemitenteBean remitenteBean) {		
		return remitenteDAO.nuevoSP(remitenteBean);
	
		
	}

	@Override
	public void actualizarRemitente(RemitenteBean remitenteBean) {
		remitenteDAO.actualizarRemitenteSP(remitenteBean);
		
	}



}
