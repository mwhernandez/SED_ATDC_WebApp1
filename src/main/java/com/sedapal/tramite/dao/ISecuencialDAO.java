package com.sedapal.tramite.dao;

import java.util.List;
import com.sedapal.tramite.beans.SecuencialBean;
import com.sedapal.tramite.beans.admin.SecuencialAdmBean;

public interface ISecuencialDAO {

	public List<SecuencialBean> secuencial();	
	public String correlativo();
	
	public List<SecuencialAdmBean> getListadoSecuenciales(String annio, String optFiltro, String txtFiltro);
}
