package com.sedapal.tramite.dao;

import java.util.List;

import com.sedapal.tramite.beans.RepresentanteBean;


public interface IRepresentanteDAO {
	 public List<RepresentanteBean> representante();	 
	 public List representanteSP(int codigo);	
	 public void actualizarRepresentanteSP(RepresentanteBean representanteBean);	 
	 public String nuevoSP(RepresentanteBean representante);	 
	 public void eliminarSP(int codremite, int codrepresentante);
	
		

}
