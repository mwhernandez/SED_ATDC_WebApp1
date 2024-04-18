package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.dao.IAccionDAO;


public class ServiciosAccion implements IServiciosAccion{
	private IAccionDAO accionDAO;


	public List<AccionBean> catalogo() {
		return accionDAO.accionSP();
		
	}
	
	public void setAccionDAO(IAccionDAO accionDAO) {
		this.accionDAO = accionDAO;
	}

	@Override
	public void eliminarAccion(int codigo) {
		
		//accionDAO.eliminarSP(codigo);
		
	}

	@Override
	public void nuevoAccion(AccionBean accionBean) {
		
		accionDAO.nuevoSP(accionBean);
	
		
	}

	@Override
	public void actualizarAccion(AccionBean accionBean) {
		accionDAO.actualizarAccionSP(accionBean);
		
	}



}
