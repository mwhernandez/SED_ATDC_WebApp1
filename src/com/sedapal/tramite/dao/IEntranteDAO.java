package com.sedapal.tramite.dao;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoDerivado;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;
import com.sedapal.tramite.beans.TipoConsulta;
import com.sedapal.tramite.beans.TiposDocumentosBean;

public interface IEntranteDAO {
	   public List<EntranteBean> entrantes();
	   public List entrantesSP(String ncodarea);
	   public String nuevoSPEnt(EntranteBean entranteBean);
	   public String actualizarSPEnt(EntranteBean entranteBean);
	   public String eliminarSPEnt(String anno, String origen, String tipodoc, String codigo, String login);
	   public String actualizarLineaDigitalizacion(String anno, String correlativo, String ubicacion, String nlote, String login);
	   public String actualizaCombos(String nombreEmpresa, String codArea, String login);
	   public boolean updatedirigidoCancel(long correlativo, int area);
	   public boolean updatedirigido(long correlativo, int area);
	   //public List<AreaBean> areas_seleccionadasOrigen(int anio, int origen, long correlativo, int nCodAreaOrigen);
	   public List<DocumentoDerivado> derivadosSP(String tipoConsulta, String area, String fechaInicial, String fechaFinal, String estado, String ficha, String areaLogin);	   
	   //public List<DocumentoDerivado> obtenerDerivados(String tipoConsulta, String area, String fechaInicial, String fechaFinal, String estado, String ficha);
	   public List<TipoConsulta> getTiposConsulta();
	   public String confirmaGrupo();
	   public String entrantesNuevos(String grupo, String fechaActual);
	   /* Agregado el 16/11/2011 - Alfredo Panitz */
	   public List getListadoDocEntrUrgentes(int codArea);
	   //public List<RemitenteBean> remitentesSP();
	   public List<RemitenteBean> personaExterna(String cadenaDigitada);	   
	   public List<EntranteBean> AlertasEntradaSP(String area);
	   
	   //SED-FON-161 INI
	   public List<RemitenteBean> remitentesBPM(int codigo);
	   public List<TiposDocumentosBean> getTiposDocumentos();  
	   public List<RemitenteBPM> consultaRemitentesBPM(String tipo, String valor) ;
	   public Integer registraRemitentesBPM(RemitenteBPM remitente);
	   //SED-FON-161 FIN
	   
	   //SED-REQ-00001
	    public List<RepresentanteBean> representanteBPM(int codigo);
}
