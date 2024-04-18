package com.sedapal.tramite.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.UsersBean;
import com.sedapal.tramite.beans.Usuario;




public class RemitenteDAO implements IRemitenteDAO , Serializable{
	
	private JdbcTemplate jdbcTemplate;
    private StoredRemitente storedRemitente;
    private StoredDeleteRemitente storeddeleteRemitente;
    private StoredActualizarRemitente storedactualizarRemitente;
    private StoredNuevoRemitente storednuevoRemitente;
    private StoredFiltrosRemitente storedFiltrosRemitente;
    private static Logger logger = Logger.getLogger("R1");
    
   	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

    @Override
	public List<RemitenteBean> remitenteSP() {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
    	
    	Map output = storedRemitente.execute();
    	
    	nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_REMITENTE Duracion:" + nowDif);
    	
		List remitente = (ArrayList)output.get("remitente");
		return remitente;
	}
    
    @Override
	public void actualizarRemitenteSP(RemitenteBean remitenteBean) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
		Map output = storedactualizarRemitente.execute(remitenteBean);
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_UPDATE_REMITENTE Duracion:" + nowDif);
		
	}
    
    public List<RemitenteBean> filtrosSP(String opcion, String detalle) {
		Map output = storedFiltrosRemitente.execute(opcion, detalle);
		List remitente = (ArrayList)output.get("filtrosremitentes");
		return remitente;
	}
    
    @Override
	public String nuevoSP(RemitenteBean remitenteBean) {
    	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();	
		
    	Map output = storednuevoRemitente.execute(remitenteBean);
    	String out = (String) output.get("out");
    	nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_NUEVO_REMITENTE Duracion:" + nowDif);
		return out;
	}
    
    @Override
	public void eliminarSP(int codigo) {		
		Map output = storeddeleteRemitente.execute(codigo);		
	}
    
    public void setStoredRemitente(StoredRemitente storedRemitente) {
		this.storedRemitente = storedRemitente;
	}
    
    @Override
	public List<RemitenteBean> remitente() {
		// TODO Auto-generated method stub
		return null;
	}
	
    public void setStoredNuevo(StoredNuevoRemitente storednuevoRemitente) {
		this.storednuevoRemitente = storednuevoRemitente;
	}
    
    public void setStoredFiltros(StoredFiltrosRemitente storedFiltrosRemitente) {
		this.storedFiltrosRemitente = storedFiltrosRemitente;
	}
	
    public void setStoredActualizarRemitente(StoredActualizarRemitente storedactualizarRemitente) {
		this.storedactualizarRemitente = storedactualizarRemitente;
	}

    public void setStoredDeleteRemitente(StoredDeleteRemitente storeddeleteRemitente) {
		this.storeddeleteRemitente = storeddeleteRemitente;
	}

	

    
	
}
