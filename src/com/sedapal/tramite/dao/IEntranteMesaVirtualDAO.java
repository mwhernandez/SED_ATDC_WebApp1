package com.sedapal.tramite.dao;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.beans.EntranteMesaVirtualBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TrabajadorBean;

public interface IEntranteMesaVirtualDAO {
	   public List<EntranteMesaVirtualBean> entrantesmesavirtual();
	   public List entrantesSP(String ncodarea);	   
	   public Map actualizarSPEntMesaVitual(EntranteMesaVirtualBean entrantemesavirtualBean);
	   public void eliminarSPEntVirtual(String anno, String origen, String codigo, String login, String tipoingreso, String motivoingreso);
	   List<TrabajadorBean> trabajador(int area);
	   public String actualizaCombos(String nombreEmpresa, String codArea, String login);
	   public List<RemitenteBean> personaExterna(String cadenaDigitada);
}
