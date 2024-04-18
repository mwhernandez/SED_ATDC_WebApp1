package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.dao.IAreaDAO;

public class ServiciosArea implements IServiciosArea{
	private IAreaDAO areaDAO;
	
	public List<AreaBean> catalogo(){    	
    	return areaDAO.areasSP();
    	}

	public void setAreaDAO(IAreaDAO areaDAO) {
		this.areaDAO = areaDAO;
	}

}
