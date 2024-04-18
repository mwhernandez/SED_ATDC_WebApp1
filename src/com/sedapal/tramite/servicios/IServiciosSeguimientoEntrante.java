package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.SeguimientoEntranteBean;

public interface IServiciosSeguimientoEntrante {
	public List<SeguimientoEntranteBean> catalogo(int anio, int origenes, String tipodocumentos, long correlativos, String area);	
	public String ActualizarSeguimiento(SeguimientoEntranteBean seguimientoBean);
	public String nuevoSeguimiento(SeguimientoEntranteBean seguimientoBean);
	public String EliminarEntrante(String corrrelativo, String codseg, String login, String area);
	

}
