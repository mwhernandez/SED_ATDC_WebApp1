package com.sedapal.tramite.dao;

import java.util.List;


import com.sedapal.tramite.beans.TiposBean;


public interface ITiposDAO {	
	 public List<TiposBean> tipos();	 
	 public List tipoSP();
	 public  String nuevoSP(TiposBean tiposBean);
	 public  void actualizarTipoSP(TiposBean tiposBean);	 
	 public void eliminarSP(String codigo);
	

}
