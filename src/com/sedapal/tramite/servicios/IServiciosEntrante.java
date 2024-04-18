package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.RemitenteBPM;

public interface IServiciosEntrante {
	public List<EntranteBean> catalogo(String ncodarea);
	public String nuevoEntrante(EntranteBean entranteBean);
	public String actualizarEntrante(EntranteBean entranteBean);
	public String eliminarEntrante(String anno, String origen, String tipodoc, String codigo, String login);
	public String actualizarLineaDigitalizacion(String anno, String correlativo, String ubicacion, String nlote, String login);
	public List<RemitenteBPM> consultaRemitentesBPM(String tipo, String valor) ;
}
