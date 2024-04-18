package com.sedapal.tramite.dao;

import java.util.List;


import com.sedapal.tramite.beans.JefeBean;
import com.sedapal.tramite.beans.TiposBean;


public interface IJefesEquiposDAO {	
	 public List<JefeBean> jefes();	 
	 public List jefesSP();
	 public  String nuevoSP(JefeBean jefesBean);
	 public  void actualizarJefesSP(JefeBean jefesBean);	 
	 //public void eliminarSP(String codigo);
	  

}
