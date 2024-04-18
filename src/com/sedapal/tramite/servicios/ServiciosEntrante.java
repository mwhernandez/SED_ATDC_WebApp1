package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.dao.IEntranteDAO;

public class ServiciosEntrante implements IServiciosEntrante{
	private IEntranteDAO entranteDAO;
	
    public List<EntranteBean> catalogo(String ncodarea){    	
    	return entranteDAO.entrantesSP(ncodarea);
    }

	public void setEntranteDAO(IEntranteDAO entranteDAO) {
		this.entranteDAO = entranteDAO;
	}

	@Override
	public String nuevoEntrante(EntranteBean entranteBean) {
		return entranteDAO.nuevoSPEnt(entranteBean);
			
	}

	@Override
	public String actualizarEntrante(EntranteBean entranteBean) {
		return entranteDAO.actualizarSPEnt(entranteBean);
	}
	
	@Override
	public String eliminarEntrante(String anno, String origen, String tipodoc, String codigo, String login) {		
		return	entranteDAO.eliminarSPEnt(anno, origen, tipodoc, codigo, login);
	}
	
	@Override
	public String actualizarLineaDigitalizacion(String anno, String correlativo, String ubicacion, String nlote, String login){
		return	entranteDAO.actualizarLineaDigitalizacion(anno, correlativo, ubicacion, nlote, login);
	}
	
	@Override
	public List<RemitenteBPM> consultaRemitentesBPM(String tipo, String valor) {
		return entranteDAO.consultaRemitentesBPM(tipo, valor);
	}
    
}
