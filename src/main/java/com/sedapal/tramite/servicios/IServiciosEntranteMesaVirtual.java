package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;


import com.sedapal.tramite.beans.EntranteMesaVirtualBean;

public interface IServiciosEntranteMesaVirtual {
	public List<EntranteMesaVirtualBean> catalogo(String ncodarea);	
	public Map actualizarEntrantemesavirtual(EntranteMesaVirtualBean entrantemesavirtualBean);
	public void eliminarEntrantemesavirtual(String anno, String origen, String codigo, String login, String tipoingreso, String motivoingreso);
}
