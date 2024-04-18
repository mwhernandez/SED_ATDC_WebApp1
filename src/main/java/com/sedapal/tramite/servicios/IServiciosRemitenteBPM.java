package com.sedapal.tramite.servicios;

import java.util.List;



import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.TiposBean;



public interface IServiciosRemitenteBPM {	
	public List<RemitenteBPM> catalogo();	
	public void actualizarRemitentebpm(RemitenteBPM remitenteBPMBean);
	
}
