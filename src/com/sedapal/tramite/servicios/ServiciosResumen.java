package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.ResumenBean;
import com.sedapal.tramite.dao.IResumenDAO;


public class ServiciosResumen implements IServiciosResumen{
	private IResumenDAO resumenDAO;
	
	public List<ResumenBean> catalogo(){    	
    	return resumenDAO.resumenSP();
    	}

	
	public void setResumenDAO(IResumenDAO resumenDAO) {
		this.resumenDAO = resumenDAO;
	}
	
	

}
