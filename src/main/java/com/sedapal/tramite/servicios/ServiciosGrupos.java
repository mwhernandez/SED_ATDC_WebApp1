package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.GrupoBean;
import com.sedapal.tramite.dao.IGruposDAO;

public class ServiciosGrupos implements IServiciosGrupos {	
	private IGruposDAO gruposDAO;


	public List<GrupoBean> catalogo() {
		return gruposDAO.grupos();
		
	}
	

	public void setGruposDAO(IGruposDAO gruposDAO) {
		this.gruposDAO = gruposDAO;
	}

	@Override
	public String nuevoGrupos(GrupoBean grupoBean) {		
		return gruposDAO.nuevoGruposSP(grupoBean);
		
	}
	
	public void actualizarGrupos(GrupoBean grupoBean) {
		gruposDAO.actualizarGruposSP(grupoBean);
				
	}
	
	public void eliminarGrupos(int codigo) {
		gruposDAO.eliminarGrupoSP(codigo);		
		
	}


}
