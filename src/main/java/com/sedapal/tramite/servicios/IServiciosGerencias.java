package com.sedapal.tramite.servicios;

import java.util.List;


import com.sedapal.tramite.beans.GrupoBean;




public interface IServiciosGerencias {	
	public List<GrupoBean> catalogo();
	public String nuevoGrupos(GrupoBean gruposBean);
	public void actualizarGrupos(GrupoBean gruposBean);
	public void eliminarGrupos(int codigo);
}
