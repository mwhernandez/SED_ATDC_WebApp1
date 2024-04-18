package com.sedapal.tramite.dao;

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


import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.ResumenBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.IResumenDAO;

public class ResumenDAO implements IResumenDAO{
	
	private JdbcTemplate jdbcTemplate;	
	private StoredResumen StoredResumen;
	private static Logger logger = Logger.getLogger("R1");		
		
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public List<ResumenBean> resumenSP(String fecha1, String fecha2, String area) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();		
		Map output = StoredResumen.execute(fecha1,fecha2,area);
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_RESUMEN Duracion:" + nowDif);
		List resumen = (ArrayList)output.get("resumen");
		return resumen;
	}
	
	
		
	public StoredResumen getStoredResumen() {
		return StoredResumen;
	}



	public void setStoredResumen(StoredResumen storedResumen) {
		StoredResumen = storedResumen;
	}


	@Override
	public List resumenSP() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
