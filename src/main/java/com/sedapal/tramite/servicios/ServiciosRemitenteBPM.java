package com.sedapal.tramite.servicios;

import java.util.List;





import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.dao.IRemitenteBPMDAO;
import com.sedapal.tramite.dao.ITiposDAO;

public class ServiciosRemitenteBPM implements IServiciosRemitenteBPM {	
	private IRemitenteBPMDAO remitentebpmDAO;


	public List<RemitenteBPM> catalogo() {
		return remitentebpmDAO.remitenteBPM();
		
	}
	
	

	public void setRemitentebpmDAO(IRemitenteBPMDAO remitentebpmDAO) {
		this.remitentebpmDAO = remitentebpmDAO;
	}



	
	
	public void actualizarRemitentebpm(RemitenteBPM remitentebpmBean) {
		remitentebpmDAO.actualizarTipoSP(remitentebpmBean);
				
	}




}
