package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.AccionBean;




public interface IServiciosAccion {
	public List<AccionBean> catalogo();
	public void nuevoAccion(AccionBean accionBean);
	public void actualizarAccion(AccionBean accionBean);
	public void eliminarAccion(int codigo);

}
