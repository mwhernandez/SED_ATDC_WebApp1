package com.sedapal.tramite.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.StoredTipos;



public class RemitenteBPMDAO implements IRemitenteBPMDAO, Serializable{
	
	private JdbcTemplate jdbcTemplate;
    private StoredRemitenteBPM storedremitentebpm;    
    private StoredActualizarRemitenteBPM storedactualizarremitentebpm;    
    private StoredBusquedaRemitenteBPM storedbusquedaremitentebpm;
    private static Logger logger = Logger.getLogger("R1");
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List<RemitenteBPM> remitenteBPM() {
		Map output = storedremitentebpm.execute();
		List remitentebpm = (ArrayList)output.get("remitentebpm");
		return remitentebpm;
	}
	
	public void actualizarTipoSP(RemitenteBPM remitenteBean) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storedactualizarremitentebpm.execute(remitenteBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_UPDATE_TIPOS_DOC Duracion:" + nowDif);
		
	}
	
	
	 public List<RemitenteBPM> filtrosSP(String opcion, String detalle) {
			Map output = storedbusquedaremitentebpm.execute(opcion, detalle);
			List remitentebpm = (ArrayList)output.get("remitentebpm");
			return remitentebpm;
		}
	 
	 
	public void setStoredremitentebpm(StoredRemitenteBPM storedremitentebpm) {
		this.storedremitentebpm = storedremitentebpm;
	}
	
	public void setStoredactualizarremitentebpm(
			StoredActualizarRemitenteBPM storedactualizarremitentebpm) {
		this.storedactualizarremitentebpm = storedactualizarremitentebpm;
	}
	
		
	
	public void setStoredbusquedaremitentebpm(StoredBusquedaRemitenteBPM storedbusquedaremitentebpm) {
		this.storedbusquedaremitentebpm = storedbusquedaremitentebpm;
	}
	@Override
	public List remitentebpmSP() {
		// TODO Auto-generated method stub
		Map output = storedremitentebpm.execute();
		List remitentebpm = (ArrayList)output.get("remitentebpm");
		return remitentebpm;
	}
	
	
	
	
	
	
	
	
	
}
