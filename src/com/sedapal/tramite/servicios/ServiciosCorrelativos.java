package com.sedapal.tramite.servicios;

import java.util.List;




import com.sedapal.tramite.beans.CorrelativosBean;
import com.sedapal.tramite.dao.ICorrelativosDAO;


public class ServiciosCorrelativos implements IServiciosCorrelativos {	
	private ICorrelativosDAO correlativosDAO;


	public List<CorrelativosBean> catalogo() {
		return correlativosDAO.correlativosSP();
		
	}
	
		
	
	public void setCorrelativosDAO(ICorrelativosDAO correlativosDAO) {
		this.correlativosDAO = correlativosDAO;
	}



	@Override
	public void actualizarTipos(CorrelativosBean correlativosBean) {
		correlativosDAO.actualizarCorrelativosSP(correlativosBean);
		
	}





	



	
	
	


}
