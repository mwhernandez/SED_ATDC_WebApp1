package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;


import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.dao.DocumentoSalidaDAO;
import com.sedapal.tramite.dao.IDocumentoSalidaDAO;
import com.sedapal.tramite.dao.IEntranteDAO;

public class ServiciosDocumentoSalida implements IServiciosDocumentoSalida{
	private IDocumentoSalidaDAO documentosalidaDAO;
	
	public List<DocumentoSalidaBean> catalogo(String area){    	
    	return documentosalidaDAO.documentoSP(area);
    	}

	
	public void setDocumentoSalidaDAO(IDocumentoSalidaDAO documentosalidaDAO) {
		this.documentosalidaDAO = documentosalidaDAO;
	}
	
	@Override
	public Map ActualizarDocumentosSalida(DocumentoSalidaBean documentosalidaBean){
		return documentosalidaDAO.actualizarDocumentoSalidaSP(documentosalidaBean);
		
	}
	

	@Override
	public Map NuevoDocumentoSalida(DocumentoSalidaBean documentosalidaBean) {
		return documentosalidaDAO.nuevoSP(documentosalidaBean);
		
	}

	@Override
	public void eliminarDocumentoSalida(String codigo, String anno, String origen,
			String tipodoc, String area, String login) {		
			documentosalidaDAO.eliminarSP(codigo, anno, origen, tipodoc, area, login);
		
	}

	

}
