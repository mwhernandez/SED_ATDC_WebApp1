package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;


import com.sedapal.tramite.beans.TiposBean;



public interface IServiciosAsunto {	
	public List<TiposBean> catalogo();
	public Map nuevoAsunto(TiposBean tiposBean);
	public Map actualizarAsunto(TiposBean tiposBean);
	public void eliminarAsunto(String codigo);
}
