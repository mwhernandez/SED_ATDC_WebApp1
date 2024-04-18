package com.sedapal.tramite.dao;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.RemitenteBean;




public interface IDocumentoSalidaDAO {
	 public List<DocumentoSalidaBean> documento();
	 public List documentoSP(String area);
	 public Map actualizarDocumentoSalidaSP(DocumentoSalidaBean documentosalidaBean);
	 public Map nuevoSP(DocumentoSalidaBean documentosalidaBean);
	 public void eliminarSP(String codigo, String anno, String origen, String tipodoc, String area, String login);
	 public String actualizaCombos(String nombreEmpresa, String codArea, String login);	 
	 public List documentoentradaSP(String area);
	 List<EntranteBean> entradasalida();
	 public List busquedadocumentoentradaSP(String area, String opcion, String detalle);
	 //public List<RemitenteBean> remitentesSP();	 
	 public List<RemitenteBean> personaExterna(String cadenaDigitada);
	
	 public RemitenteBPM consultaDocumentoBPM(Long correlativo);  //SED-REQ-00001
}
