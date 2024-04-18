package com.sedapal.tramite.dao;

import java.util.List;


import com.sedapal.tramite.beans.PerfilBean;


public interface IPerfilDAO {
	 public List<PerfilBean> perfiles();	
	 public List perfilesSP();	 
	 public void nuevoSP(PerfilBean perfilBean);
	 public void actualizarPerfilSP(PerfilBean perfilBean);
	 public void eliminarSP(int codigo);
	void eliminarSP(PerfilBean perfilBean);
	
	
	   
}
