package com.sedapal.tramite.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TrabajadorBean;

public class TrabajadorDAO implements ITrabajadorDAO{
	
	private JdbcTemplate jdbcTemplate;
	private StoredTrabajador storedTrabajador;
	private StoredFiltrosTrabajador storedFiltrosTrabajador;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<TrabajadorBean> trabajadorSP() {
		Map output = storedTrabajador.execute();
		List trabajador = (ArrayList)output.get("trabajador");
		return trabajador;
	}

	public void setStoredTrabajador(StoredTrabajador storedtrabajador) {
		this.storedTrabajador = storedtrabajador;
	}

	public List<TrabajadorBean> filtrosSP(String opcion, String detalle) {
		Map output = storedFiltrosTrabajador.execute(opcion, detalle);
		List trabajador = (ArrayList)output.get("filtrostrabajadores");
		return trabajador;
	}
	
	@Override
	public List<TrabajadorBean> trabajador() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStoredFiltros(StoredFiltrosTrabajador storedFiltrosTrabajador) {
		this.storedFiltrosTrabajador = storedFiltrosTrabajador;
	}
	

}
