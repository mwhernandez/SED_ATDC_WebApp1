package com.sedapal.tramite.dao;

import java.util.List;


import com.sedapal.tramite.beans.GrupoBean;

public interface IGruposDAO {	
	 public List<GrupoBean> grupos();
	 public  String nuevoGruposSP(GrupoBean grupoBean);
	 public  void actualizarGruposSP(GrupoBean grupoBean);	 
	 public void eliminarGrupoSP(int codigo);	  

}
