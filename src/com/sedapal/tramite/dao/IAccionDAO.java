package com.sedapal.tramite.dao;

import java.util.List;

import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TiposBean;

public interface IAccionDAO {
	 public List<AccionBean> accion();	 
	 public List accionSP();	 
	 public  void nuevoSP(AccionBean accionBean);
	 public  void actualizarAccionSP(AccionBean accionBean);
	 public void eliminarSP(String codigo);
	 void eliminarSP(AccionBean accionBean);
	
	

}
