package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.dao.ISeguimientoEntranteDAO;

public class ServiciosSeguimientoEntrante implements IServiciosSeguimientoEntrante{
	private ISeguimientoEntranteDAO seguimientoEntranteDAO;
	
	public List<SeguimientoEntranteBean> catalogo(int anio, int origenes, String tipodocumentos, long correlativos, String area){
		//String derivado
    	return seguimientoEntranteDAO.seguimientoSP(anio, origenes, tipodocumentos, correlativos, area);
    	}

	public void setSeguimientoEntranteDAO(ISeguimientoEntranteDAO seguimientoEntranteDAO) {
		this.seguimientoEntranteDAO = seguimientoEntranteDAO;
	}	

	

	@Override
	public String ActualizarSeguimiento(SeguimientoEntranteBean seguimientoBean) {
		// TODO Auto-generated method stub
		//documentosalidaDAO.actualizarDocumentoSalidaSP(documentosalidaBean);
		return seguimientoEntranteDAO.actualizarSeguimientoSP(seguimientoBean);
			
		
	}

	@Override
	public String nuevoSeguimiento(SeguimientoEntranteBean seguimientoBean) {
		// TODO Auto-generated method stub			
		return seguimientoEntranteDAO.nuevoSPSeguimiento(seguimientoBean);
			
	}

	@Override
	public String EliminarEntrante(String corrrelativo, String codseg, String login, String area) {		
		return seguimientoEntranteDAO.EliminarSeguimiento(corrrelativo, codseg, login, area);
	}	

	

	
	
	

}
