package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;


import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.dao.IAsuntoDAO;
import com.sedapal.tramite.dao.ITiposDAO;

public class ServiciosAsunto implements IServiciosAsunto {	
	private IAsuntoDAO asuntoDAO;
	
	

	public List<TiposBean> catalogo() {
		return asuntoDAO.asunto();		
	}
	
	public void setAsuntoDAO(IAsuntoDAO asuntoDAO) {
		this.asuntoDAO = asuntoDAO;
	}


	@Override
	public Map actualizarAsunto(TiposBean tiposBean) {
		return asuntoDAO.actualizarTipoSP(tiposBean);
		
	}

	@Override
	public void eliminarAsunto(String codigo) {
		asuntoDAO.eliminarSP(codigo);		
	}

	@Override
	public Map nuevoAsunto(TiposBean tiposBean) {
		return asuntoDAO.nuevoSP(tiposBean);
	}


}
