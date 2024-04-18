package com.sedapal.tramite.dao;

import java.util.List;



import com.sedapal.tramite.beans.CorrelativosBean;


public interface ICorrelativosDAO {	
	 public List<CorrelativosBean> correlativos();	 
	 public List correlativosSP();
	 public  void actualizarCorrelativosSP(CorrelativosBean correlativosBean);
	 
	 /*public  String nuevoSP(TiposBean tiposBean);
	  * public void eliminarSP(String codigo);
	  * */
	 	 
	 
	

}
