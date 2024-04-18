package com.sedapal.tramite.dao;

import java.util.List;


import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;

public interface ISeguimientoEntranteDAO {

	 public List seguimientoSP();
	 public List<SeguimientoEntranteBean> seguimientoSP(int anio, int origenes, String tipodocumentos, long correlativos, String area);	
	 //, String derivado
	 public String actualizarSeguimientoSP(SeguimientoEntranteBean seguimientoBean);
	 public String nuevoSPSeguimiento(SeguimientoEntranteBean seguimientoBean);
	 public void storedDerivado (int anio, int origen, String tipodoc, long correlativo, int area, String login,long ficha);
	 public	String EliminarSeguimiento(String correlativo, String codseg, String login, String area);
	
	
	
	 

}
