package com.sedapal.tramite.dao;

import java.util.List;


import com.sedapal.tramite.beans.ResumenBean;

public interface IResumenDAO {
	 
	 public List<ResumenBean> resumenSP(String fecha1, String fecha2, String area);
	 List resumenSP();	 
	

}
