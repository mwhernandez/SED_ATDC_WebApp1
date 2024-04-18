package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.dao.ITiposDAO;

public class ServiciosTipos implements IServiciosTipos {	
	private ITiposDAO tiposDAO;


	public List<TiposBean> catalogo() {
		return tiposDAO.tipoSP();
		
	}
	
	public void setTipoDAO(ITiposDAO tiposDAO) {
		this.tiposDAO = tiposDAO;
	}

	@Override
	public String nuevoTipos(TiposBean tiposBean) {		
		return tiposDAO.nuevoSP(tiposBean);
		
	}
	
	public void actualizarTipos(TiposBean tiposBean) {
		tiposDAO.actualizarTipoSP(tiposBean);
				
	}
	
	public void eliminarTipos(String codigo) {
		tiposDAO.eliminarSP(codigo);		
		
	}


}
