package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.TiposBean;



public interface IServiciosTipos {	
	public List<TiposBean> catalogo();
	public String nuevoTipos(TiposBean tiposBean);
	public void actualizarTipos(TiposBean tiposBean);
	public void eliminarTipos(String codigo);
}
