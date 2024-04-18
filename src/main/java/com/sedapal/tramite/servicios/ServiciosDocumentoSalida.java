package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.dao.DocumentoSalidaDAO;
import com.sedapal.tramite.dao.IDocumentoSalidaDAO;
import com.sedapal.tramite.dao.IEntranteDAO;

public class ServiciosDocumentoSalida implements IServiciosDocumentoSalida {
	private IDocumentoSalidaDAO documentosalidaDAO;

	public List<DocumentoSalidaBean> catalogo(String area, int fichaUsuario) {
		return documentosalidaDAO.documentoSP(area, fichaUsuario);
	}

	public void setDocumentoSalidaDAO(IDocumentoSalidaDAO documentosalidaDAO) {
		this.documentosalidaDAO = documentosalidaDAO;
	}

	@Override
	public Map ActualizarDocumentosSalida(DocumentoSalidaBean documentosalidaBean) {
		return documentosalidaDAO.actualizarDocumentoSalidaSP(documentosalidaBean);
	}

	@Override
	public Map NuevoDocumentoSalida(DocumentoSalidaBean documentosalidaBean) {
		return documentosalidaDAO.nuevoSP(documentosalidaBean);
	}

	@Override
	public void eliminarDocumentoSalida(String codigo, String anno, String origen, String tipodoc, String area,
			String login) {
		documentosalidaDAO.eliminarSP(codigo, anno, origen, tipodoc, area, login);

	}

	@Override
	public void visarDocumentoSalida(String codigo, String anno, String origen, String tipodoc, String area,
			String login) {
		documentosalidaDAO.visarSP(codigo, anno, origen, tipodoc, area, login);
	}

	@Override
	public void firmarDocumentoSalida(String codigo, String anno, String origen, String tipodoc, String area,
			String login) {
		documentosalidaDAO.firmarSP(codigo, anno, origen, tipodoc, area, login);
	}

}
