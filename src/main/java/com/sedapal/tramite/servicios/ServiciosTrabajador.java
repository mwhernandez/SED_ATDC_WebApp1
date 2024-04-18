package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.TrabajadorBean;
import com.sedapal.tramite.dao.ITrabajadorDAO;

public class ServiciosTrabajador implements IServiciosTrabajador{
	private ITrabajadorDAO trabajadorDAO;
	
	public List<TrabajadorBean> catalogo(){    	
    	return trabajadorDAO.trabajadorSP();
    	}

	public void setTrabajadorDAO(ITrabajadorDAO trabajadorDAO) {
		this.trabajadorDAO = trabajadorDAO;
	}

}
