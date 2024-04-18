package com.sedapal.tramite.dao;

import java.util.List;



import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.TiposBean;


public interface IRemitenteBPMDAO {	
	 public List<RemitenteBPM> remitenteBPM();	 
	 public List remitentebpmSP();	 
	 public  void actualizarTipoSP(RemitenteBPM remitentebpmBean);	 
	 	
}
