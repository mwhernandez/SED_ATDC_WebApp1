package com.sedapal.tramite.dao;

import java.util.List;

import com.sedapal.tramite.beans.FeriadosBean;


public interface IFeriadoDAO {
	 public List<FeriadosBean> feriado();	 
	 public List feriadoSP();	 
	 public void nuevoSP(FeriadosBean feriadosBean);
	 public void actualizarFeriadoSP(FeriadosBean feriadosBean);
	 public void eliminarSP(int codigo);	 

}
