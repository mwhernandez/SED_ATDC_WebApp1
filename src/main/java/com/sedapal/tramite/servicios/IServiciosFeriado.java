package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.FeriadosBean;
import com.sedapal.tramite.beans.RemitenteBean;




public interface IServiciosFeriado {
	public List<FeriadosBean> catalogo();
	public void nuevoFeriado(FeriadosBean feriadosBean);
	public void actualizarFeriado(FeriadosBean feriadosBean);
	public void eliminarFeriado(int codigo);
	

	
	

}
