package com.sedapal.tramite.dao;

import java.util.List;

import com.sedapal.tramite.beans.RemitenteBean;


public interface IRemitenteDAO {
	 public List<RemitenteBean> remitente();	 
	 public List remitenteSP();	 
	 public String nuevoSP(RemitenteBean remitenteBean);
	 public void actualizarRemitenteSP(RemitenteBean remitenteBean);
	 public void eliminarSP(int codigo);	

}
