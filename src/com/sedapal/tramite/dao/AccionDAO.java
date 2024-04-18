package com.sedapal.tramite.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.beans.TiposBean;



public class AccionDAO implements IAccionDAO , Serializable{
	
	private JdbcTemplate jdbcTemplate;
    private StoredAccion storedAccion;
    private StoredDeleteAccion storeddeleteAccion;
    private StoredActualizarAccion storedactualizarAccion;
    private StoredNuevoTiposaAccion storednuevoTiposAccion;
    
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
    
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
    
	@Override
	public List<AccionBean> accionSP() {
		Map output = storedAccion.execute();
		List accion = (ArrayList)output.get("accion");
		return accion;
	}
	
	public void actualizarAccionSP(AccionBean accionBean) {
		Map output = storedactualizarAccion.execute(accionBean);		
	}
	
	@Override
	public void nuevoSP(AccionBean accionBean) {		
		Map output = storednuevoTiposAccion.execute(accionBean);
		
	}
	@Override
	public void eliminarSP(String codigo) {		
		Map output = storeddeleteAccion.execute(codigo);		
	}
	
	public void setStoredAccion(StoredAccion storedAccion) {
		this.storedAccion = storedAccion;
	}
	@Override
	public List<AccionBean> accion() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setStoredNuevo(StoredNuevoTiposaAccion storednuevoAccion) {
		this.storednuevoTiposAccion = storednuevoAccion;
	}
	
	public void setStoredActualizarAccion(StoredActualizarAccion storedActualizarAccion) {
		this.storedactualizarAccion = storedActualizarAccion;
	}
	public void setStoredDeleteAccion(StoredDeleteAccion storeddeleteAccion) {
		this.storeddeleteAccion = storeddeleteAccion;
	}
	@Override
	public void eliminarSP(AccionBean accionBean) {
		this.storedAccion = storedAccion;
		
	}

	

	
	
	
	
	
}
