package com.sedapal.tramite.servicios;

import java.util.List;



import com.sedapal.tramite.beans.CorrelativosBean;

public interface IServiciosCorrelativos {	
	public List<CorrelativosBean> catalogo();
	public void actualizarTipos(CorrelativosBean correlativosBean);
	/*public String nuevoTipos(TiposBean tiposBean);	
	public void eliminarTipos(String codigo);
	*/
}
