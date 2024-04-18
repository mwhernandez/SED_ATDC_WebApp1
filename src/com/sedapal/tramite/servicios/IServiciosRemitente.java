package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.RemitenteBean;




public interface IServiciosRemitente {
	public List<RemitenteBean> catalogo();
	public void eliminarRemitente(int codigo);
	public String nuevoRemitente(RemitenteBean remitenteBean);
	public void actualizarRemitente(RemitenteBean remitenteBean);
	

}
