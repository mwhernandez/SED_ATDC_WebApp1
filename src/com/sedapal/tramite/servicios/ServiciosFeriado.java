package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.FeriadosBean;
import com.sedapal.tramite.dao.IFeriadoDAO;


public class ServiciosFeriado implements IServiciosFeriado{
	private IFeriadoDAO feriadoDAO;


	public List<FeriadosBean> catalogo() {
		return feriadoDAO.feriadoSP();
		
	}


	public void setFeriadoDAO(IFeriadoDAO feriadoDAO) {
		this.feriadoDAO = feriadoDAO;
	}
	
	

	@Override
	public void eliminarFeriado(int codigo) {		
		feriadoDAO.eliminarSP(codigo);
		
	}

	@Override
	public void nuevoFeriado(FeriadosBean feriadosBean) {		
		feriadoDAO.nuevoSP(feriadosBean);
		}

	@Override
	public void actualizarFeriado(FeriadosBean feriadosBean) {
		feriadoDAO.actualizarFeriadoSP(feriadosBean);
	}

	

}
