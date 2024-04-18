package com.sedapal.tramite.servicios;

import java.util.List;

import com.sedapal.tramite.beans.PerfilBean;



public interface IServiciosPerfil {
	public List<PerfilBean> catalogo();
	public void actualizarPerfil(PerfilBean perfilBean);
	public void nuevoPerfil(PerfilBean perfilBean);
	public void eliminarPerfil(int codigo);
	

}
