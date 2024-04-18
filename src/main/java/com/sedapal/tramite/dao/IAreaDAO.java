package com.sedapal.tramite.dao;

import java.util.List;
import com.sedapal.tramite.beans.AreaBean;

public interface IAreaDAO {

	public List<AreaBean> areas();	
	public List areasSP();	 
	public void nuevoSP(AreaBean areaBean);
	public List<AreaBean> busquedaArea(String optFiltro, String txtFiltro);
}
