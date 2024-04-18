package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;




import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.dao.IEntranteMesaDAO;
import com.sedapal.tramite.dao.IEntranteMesaLineaDAO;

public class ServiciosEntranteMesaLinea implements IServiciosEntranteMesaLinea{
	
	private IEntranteMesaLineaDAO entrantemesalineaDAO;
	
    public List<EntranteMesaBean> catalogo(String ncodarea){    	
    	return entrantemesalineaDAO.entrantesSP(ncodarea);
    }


	

	public void setEntrantemesalineaDAO(IEntranteMesaLineaDAO entrantemesalineaDAO) {
		this.entrantemesalineaDAO = entrantemesalineaDAO;
	}




	@Override
	public Map actualizarEntrantemesa(EntranteMesaBean entrantemesaBean) {
		return entrantemesalineaDAO.actualizarSPEntMesa(entrantemesaBean);
		
	}

	@Override
	public void eliminarEntrantemesa(String anno, String origen,String tipodoc, String codigo, String login) {
		entrantemesalineaDAO.eliminarSPEnt(anno, origen, tipodoc, codigo, login);
		
	}

	@Override
	public Map nuevoEntrantemesa(EntranteMesaBean entrantemesaBean) {
		return entrantemesalineaDAO.nuevoSPEnt(entrantemesaBean);	
		
	}

	

    
}
