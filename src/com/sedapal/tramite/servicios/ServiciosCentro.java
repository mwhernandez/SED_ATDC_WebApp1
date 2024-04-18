package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.CentroBean;
import com.sedapal.tramite.dao.ICentroDAO;

public class ServiciosCentro implements IServiciosCentro{
	private ICentroDAO centroDAO;
	
	public List<CentroBean> catalogo(){    	
    	return centroDAO.centroSP();
    	}

	public void setCentroDAO(ICentroDAO centroDAO) {
		this.centroDAO = centroDAO;
	}

}
