package com.sedapal.tramite.dao;

import java.util.List;

import com.sedapal.tramite.beans.EntranteBean;

public interface IEntranteDerivadoDAO {
	   public List<EntranteBean> entrantes();
	   public List entrantesSP(String ncodarea, int ficha);
	   public void nuevoSPEnt(EntranteBean entranteBean);
	   public void actualizarSPEnt(EntranteBean entranteBean);
	   public void eliminarSPEnt(String anno, String origen, String tipodoc, String codigo, String login);
	
}
