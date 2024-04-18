package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.dao.IEntranteGerenciasDAO;
import com.sedapal.tramite.dao.IEntranteGrupoDAO;

public class ServiciosEntranteGerencias implements IServiciosEntranteGerencias{
	
	private IEntranteGerenciasDAO entranteGerenciasDAO;
	
    public List<EntranteBean> catalogo(String ncodarea){
    	return entranteGerenciasDAO.entrantesSP(ncodarea);
    	
    }


	public void setEntranteGerenciasDAO(IEntranteGerenciasDAO entranteGerenciasDAO) {
		this.entranteGerenciasDAO = entranteGerenciasDAO;
	}

	@Override
	public void nuevoEntrante(EntranteBean entranteBean) {			
		entranteGerenciasDAO.nuevoSPEnt(entranteBean);
	}

	@Override
	public void actualizarEntrante(EntranteBean entranteBean) {
		entranteGerenciasDAO.actualizarSPEnt(entranteBean);
	}
	
	@Override
	public void eliminarEntrante(String anno, String origen, String tipodoc, String codigo, String login) {
		entranteGerenciasDAO.eliminarSPEnt(anno, origen, tipodoc, codigo, login);
		
	}	


    
}
