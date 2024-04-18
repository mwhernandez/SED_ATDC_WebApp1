package com.sedapal.tramite.servicios;

import java.util.List;
import java.util.Map;







import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.beans.EntranteMesaVirtualBean;
import com.sedapal.tramite.dao.IEntranteMesaDAO;
import com.sedapal.tramite.dao.IEntranteMesaVirtualDAO;

public class ServiciosEntranteMesaVirtual implements IServiciosEntranteMesaVirtual{
	private IEntranteMesaVirtualDAO entrantemesavirtualDAO;
	
    public List<EntranteMesaVirtualBean> catalogo(String ncodarea){    	
    	return entrantemesavirtualDAO.entrantesSP(ncodarea);
    }


	public void setEntrantemesaDAO(IEntranteMesaVirtualDAO entrantemesavirtualDAO) {
		this.entrantemesavirtualDAO = entrantemesavirtualDAO;
	}

	@Override
	public Map actualizarEntrantemesavirtual(EntranteMesaVirtualBean entrantemesavirtualBean) {
		return entrantemesavirtualDAO.actualizarSPEntMesaVitual(entrantemesavirtualBean);
		
	}

	@Override
	public void eliminarEntrantemesavirtual(String anno, String origen, String codigo, String login, String tipoingreso, String motivoingreso) {
		entrantemesavirtualDAO.eliminarSPEntVirtual(anno, origen, codigo, login, tipoingreso, motivoingreso);
		
	}


	public void setEntrantemesavirtualDAO(IEntranteMesaVirtualDAO entrantemesavirtualDAO) {
		this.entrantemesavirtualDAO = entrantemesavirtualDAO;
	}
	
	
	
	

    
}
