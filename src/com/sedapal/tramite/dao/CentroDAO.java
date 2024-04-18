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

import com.sedapal.tramite.beans.CentroBean;
import com.sedapal.tramite.beans.Usuario;

public class CentroDAO implements ICentroDAO{
	
	private JdbcTemplate jdbcTemplate;
	private StoredCentros StoredCentros;	
	private static Logger logger = Logger.getLogger("R1");	
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<CentroBean> centroSP() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		Map output = StoredCentros.execute();
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_CENTRO Duracion:" + nowDif);
		List centros = (ArrayList)output.get("centro");
		return centros;
	}

	public void setStoredCentro(StoredCentros storedCentros) {
		this.StoredCentros = storedCentros;
	}

	@Override
	public List<CentroBean> centros() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	
	
	


	

	
	
}
