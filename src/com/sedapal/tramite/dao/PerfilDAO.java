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

import com.sedapal.tramite.beans.PerfilBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.Usuario;


public class PerfilDAO implements IPerfilDAO, Serializable{
	
	private JdbcTemplate jdbcTemplate;
	private storedPerfiles storedPerfiles;	
	private StoredActualizarPerfil storedactualizarPerfil;
	private StoredNuevoPerfil storednuevoPerfil;
	private StoredDeletePerfil storeddeletePerfil;
	private static Logger logger = Logger.getLogger("R1");
		
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public List<PerfilBean> perfilesSP() {
		
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storedPerfiles.execute();
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_PERFIL Duracion:" + nowDif);
		
		List perfiles = (ArrayList)output.get("perfiles");
		return perfiles;
	}

	public void setStoredPerfiles(storedPerfiles storedPerfiles) {
		this.storedPerfiles = storedPerfiles;
	}
	
	@Override
	public void actualizarPerfilSP(PerfilBean perfilBean) {	
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storedactualizarPerfil.execute(perfilBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_UPDATE_PERFIL Duracion:" + nowDif);
		
	}
	@Override
	public void nuevoSP(PerfilBean perfilBean) {
		// TODO Auto-generated method stub
		Map output = storednuevoPerfil.execute(perfilBean);		
	}

	@Override
	public List<PerfilBean> perfiles() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStoredNuevo(StoredNuevoPerfil storednuevoPerfil) {
		this.storednuevoPerfil = storednuevoPerfil;
	}
	public void setStoredActualizarPerfil(StoredActualizarPerfil storedActualizarPerfil) {
		this.storedactualizarPerfil = storedActualizarPerfil;
	}
	public void setStoredDeletePerfil(StoredDeletePerfil storeddeletePerfil) {
		this.storeddeletePerfil = storeddeletePerfil;
	}
	
	@Override
	public void eliminarSP(int codigo) {
		// TODO Auto-generated method stub
		Map output = storeddeletePerfil.execute(codigo);	
		
	}


	@Override
	public void eliminarSP(PerfilBean perfilBean) {
		// TODO Auto-generated method stub
		
	}
	
}
