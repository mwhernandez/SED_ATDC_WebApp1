package com.sedapal.tramite.dao;

import java.util.List;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoDerivado;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TipoConsulta;

public interface IEntranteGerenciasDAO {
	   public List<EntranteBean> gerencias();
	   public List entrantesSP(String ncodarea);
	   public void nuevoSPEnt(EntranteBean entranteBean);
	   public void actualizarSPEnt(EntranteBean entranteBean);
	   public void eliminarSPEnt(String anno, String origen, String tipodoc, String codigo, String login);
	   public String actualizaCombos(String nombreEmpresa, String codArea, String login);
	   public boolean updatedirigidoCancel(long correlativo, int area);
	   public boolean updatedirigido(long correlativo, int area);
	   public List<AreaBean> areas_seleccionadasOrigen(int anio, int origen, long correlativo, int nCodAreaOrigen);
	   public List<DocumentoDerivado> derivadosSP(String tipoConsulta, String area, String fechaInicial, String fechaFinal, String estado, String ficha, String areaLogin);
	   //public List<DocumentoDerivado> obtenerDerivados(String tipoConsulta, String area, String fechaInicial, String fechaFinal, String estado, String ficha);
	   public List<TipoConsulta> getTiposConsulta();
	   public List<RemitenteBean> personaExterna(String cadenaDigitada);
}
