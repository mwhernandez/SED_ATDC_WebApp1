package com.sedapal.tramite.dao;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.RemitenteBean;

public interface IDocumentoSalidaDAO {
	public List<DocumentoSalidaBean> documento();

	public List documentoSP(String area, int fichaUsuario);

	public Map actualizarDocumentoSalidaSP(DocumentoSalidaBean documentosalidaBean);

	public Map nuevoSP(DocumentoSalidaBean documentosalidaBean);

	public void eliminarSP(String codigo, String anno, String origen, String tipodoc, String area, String login);

	public String actualizaCombos(String nombreEmpresa, String codArea, String login);

	public List documentoentradaSP(String area);

	List<EntranteBean> entradasalida();

	public List busquedadocumentoentradaSP(String area, String opcion, String detalle);

	// public List<RemitenteBean> remitentesSP();
	public List<RemitenteBean> personaExterna(String cadenaDigitada);

	public RemitenteBPM consultaDocumentoBPM(Long correlativo); // SED-REQ-00001

	public void visarSP(String codigo, String anno, String origen, String tipodoc, String area, String login);

	public void firmarSP(String codigo, String anno, String origen, String tipodoc, String area, String login);
	
	public void observarDocumento(String codigo, String anno, String origen, String tipodoc, String area, String login, String observacion);
	
	public void guardarAnexo(int codigo, int anno, String login, String anexo, String anexoNombre);
	
	public void eliminarAnexo(int codigo, int anno, String login, int anexo);
	
	public void guardarRegistroEntradaPorDocSal(int registro, int anio, int codigo, int docAnio, int docArea, String login);
}
