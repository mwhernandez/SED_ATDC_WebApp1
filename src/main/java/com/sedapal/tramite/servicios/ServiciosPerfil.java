package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.PerfilBean;
import com.sedapal.tramite.dao.IPerfilDAO;

public class ServiciosPerfil implements IServiciosPerfil{
	private IPerfilDAO perfilDAO;
	
    public List<PerfilBean> catalogo(){    	
    	return perfilDAO.perfilesSP();
    	}

	public void setPerfilDAO(IPerfilDAO perfilDAO) {
		this.perfilDAO = perfilDAO;
	}

	@Override
	public void actualizarPerfil(PerfilBean perfilBean) {
		perfilDAO.actualizarPerfilSP(perfilBean);
		
	}

	@Override
	public void nuevoPerfil(PerfilBean perfilBean) {		
		perfilDAO.nuevoSP(perfilBean);
		
	}

	@Override
	public void eliminarPerfil(int codigo) {
		// TODO Auto-generated method stub
		perfilDAO.eliminarSP(codigo);
		
	}
    
}
