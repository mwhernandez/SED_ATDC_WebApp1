package com.sedapal.tramite.dao;

import java.util.List;

import com.sedapal.tramite.beans.CentroBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.TrabajadorBean;

public interface IDocumentoGerenciaDAO {
	 
	 public List<TiposBean> consultaSP();

	 public List<TiposBean> consulta_anno();
	 
	

}
