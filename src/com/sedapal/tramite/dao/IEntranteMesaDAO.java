package com.sedapal.tramite.dao;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TrabajadorBean;

public interface IEntranteMesaDAO {
	   public List<EntranteMesaBean> entrantesmesa();
	   public List entrantesSP(String ncodarea);
	   public Map nuevoSPEnt(EntranteMesaBean entrantemesaBean);
	   public Map actualizarSPEntMesa(EntranteMesaBean entrantemesaBean);
	   public void eliminarSPEnt(String anno, String origen, String tipodoc, String codigo, String login);
	   List<TrabajadorBean> trabajador(int area);
	   public String actualizaCombos(String nombreEmpresa, String codArea, String login);
	   public List<RemitenteBean> personaExterna(String cadenaDigitada);
}
