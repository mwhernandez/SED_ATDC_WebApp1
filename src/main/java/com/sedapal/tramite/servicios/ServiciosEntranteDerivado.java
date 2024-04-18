package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.dao.IEntranteDerivadoDAO;


public class ServiciosEntranteDerivado implements IServiciosEntranteDerivado{
	private IEntranteDerivadoDAO entranteDerivadoDAO;
	
    public List<EntranteBean> catalogo(String ncodarea, int ficha){    	
    	return entranteDerivadoDAO.entrantesSP(ncodarea,ficha);
    }

	public void setEntranteDerivadoDAO(IEntranteDerivadoDAO entranteDerivadoDAO) {
		this.entranteDerivadoDAO = entranteDerivadoDAO;
	}

	@Override
	public void nuevoEntrante(EntranteBean entranteBean) {
		entranteDerivadoDAO.nuevoSPEnt(entranteBean);	
	}

	@Override
	public void actualizarEntrante(EntranteBean entranteBean) {
		entranteDerivadoDAO.actualizarSPEnt(entranteBean);
	}
	
	@Override
	public void eliminarEntrante(String anno, String origen, String tipodoc, String codigo, String login) {		
			entranteDerivadoDAO.eliminarSPEnt(anno, origen, tipodoc, codigo, login);
	}

	
	

    
}
