package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.dao.IEntranteGrupoDAO;

public class ServiciosEntranteGrupo implements IServiciosEntranteGrupo{
	private IEntranteGrupoDAO entranteGrupoDAO;
	
    public List<EntranteBean> catalogo(String ncodarea){    	
    	return entranteGrupoDAO.entrantesSP(ncodarea);
    }

	
	public void setEntranteGrupoDAO(IEntranteGrupoDAO entranteGrupoDAO) {
		this.entranteGrupoDAO = entranteGrupoDAO;
	}

	@Override
	public void nuevoEntrante(EntranteBean entranteBean) {
		entranteGrupoDAO.nuevoSPEnt(entranteBean);	
	}

	@Override
	public void actualizarEntrante(EntranteBean entranteBean) {
		entranteGrupoDAO.actualizarSPEnt(entranteBean);
	}
	
	@Override
	public void eliminarEntrante(String anno, String origen, String tipodoc, String codigo, String login) {		
		entranteGrupoDAO.eliminarSPEnt(anno, origen, tipodoc, codigo, login);
	}	


    
}
