package com.sedapal.tramite.dao;

import java.util.List;
import java.util.Map;


import com.sedapal.tramite.beans.TiposBean;


public interface IAsuntoDAO {	
	 public List<TiposBean> asunto();
	 public Map nuevoSP(TiposBean tiposBean);	 	 
	 public void eliminarSP(String codigo);
	 public Map actualizarTipoSP(TiposBean tiposBean);


}
