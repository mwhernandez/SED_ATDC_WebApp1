package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.EntranteMesaBean;

public interface IServiciosEntranteMesa {
	public List<EntranteMesaBean> catalogo(String ncodarea);
	public Map nuevoEntrantemesa(EntranteMesaBean entrantemesaBean);
	public Map actualizarEntrantemesa(EntranteMesaBean entrantemesaBean);
	public void eliminarEntrantemesa(String anno, String origen, String tipodoc, String codigo, String login);
}
