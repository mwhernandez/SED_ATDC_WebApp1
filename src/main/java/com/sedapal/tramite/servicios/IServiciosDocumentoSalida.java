package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.DocumentoSalidaBean;

public interface IServiciosDocumentoSalida {
	public List<DocumentoSalidaBean> catalogo(String area, int fichaUsuario);

	public Map ActualizarDocumentosSalida(DocumentoSalidaBean documentosalidaBean);

	public Map NuevoDocumentoSalida(DocumentoSalidaBean documentosalidaBean);

	public void eliminarDocumentoSalida(String codigo, String anno, String origen, String tipodoc, String area,
			String login);

	public void visarDocumentoSalida(String codigo, String anno, String origen, String tipodoc, String area,
			String login);

	public void firmarDocumentoSalida(String codigo, String anno, String origen, String tipodoc, String area,
			String login);
}
