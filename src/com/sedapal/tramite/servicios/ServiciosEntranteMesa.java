package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;



import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.dao.IEntranteMesaDAO;

public class ServiciosEntranteMesa implements IServiciosEntranteMesa{
	private IEntranteMesaDAO entrantemesaDAO;
	
    public List<EntranteMesaBean> catalogo(String ncodarea){    	
    	return entrantemesaDAO.entrantesSP(ncodarea);
    }


	public void setEntrantemesaDAO(IEntranteMesaDAO entrantemesaDAO) {
		this.entrantemesaDAO = entrantemesaDAO;
	}

	@Override
	public Map actualizarEntrantemesa(EntranteMesaBean entrantemesaBean) {
		return entrantemesaDAO.actualizarSPEntMesa(entrantemesaBean);
		
	}

	@Override
	public void eliminarEntrantemesa(String anno, String origen,String tipodoc, String codigo, String login) {
		entrantemesaDAO.eliminarSPEnt(anno, origen, tipodoc, codigo, login);
		
	}

	@Override
	public Map nuevoEntrantemesa(EntranteMesaBean entrantemesaBean) {
		return entrantemesaDAO.nuevoSPEnt(entrantemesaBean);	
		
	}

	

    
}
