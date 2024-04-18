package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.dao.ISeguimientoEntranteDAO;
import com.sedapal.tramite.dao.ISeguimientoEntranteGerenciasDAO;

public class ServiciosSeguimientoEntranteGerencia implements IServiciosSeguimientoEntranteGerencia{
	private ISeguimientoEntranteGerenciasDAO seguimientoEntranteGerenciasDAO;
	
	public List<SeguimientoEntranteBean> catalogo(int anio, int origenes, String tipodocumentos, long correlativos, String area){
		//String derivado
    	return seguimientoEntranteGerenciasDAO.seguimientoSP(anio, origenes, tipodocumentos, correlativos, area);
    	}

		
	
	

	public void setSeguimientoEntranteGerenciasDAO(ISeguimientoEntranteGerenciasDAO seguimientoEntranteGerenciasDAO) {
		this.seguimientoEntranteGerenciasDAO = seguimientoEntranteGerenciasDAO;
	}





	@Override
	public String ActualizarSeguimiento(SeguimientoEntranteBean seguimientoBean) {
		// TODO Auto-generated method stub
		//documentosalidaDAO.actualizarDocumentoSalidaSP(documentosalidaBean);
		return seguimientoEntranteGerenciasDAO.actualizarSeguimientoSP(seguimientoBean);
			
		
	}

	@Override
	public String nuevoSeguimiento(SeguimientoEntranteBean seguimientoBean) {
		// TODO Auto-generated method stub			
		return seguimientoEntranteGerenciasDAO.nuevoSPSeguimiento(seguimientoBean);
			
	}

	@Override
	public String EliminarEntrante(String corrrelativo, String codseg, String login, String area) {		
		return seguimientoEntranteGerenciasDAO.EliminarSeguimiento(corrrelativo, codseg, login, area);
	}	

	

	
	
	

}
