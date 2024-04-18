package com.sedapal.tramite.dao;


import java.io.Serializable;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sedapal.tramite.beans.CredencialesAPI;

public class CredencialesApiDAO implements ICredencialesApiDAO, Serializable {
	
	private StoredCredencialesBpmAPI storedCredencialesBpmAPI;
	private StoredRutasBpmAPI storedRutasBpmAPI;
	
	private JdbcTemplate jdbcTemplate;
	
	 @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
	 
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public CredencialesAPI obtener() {
		CredencialesAPI credencial = new CredencialesAPI();
		try {
			Map output = storedCredencialesBpmAPI.execute();
			
			String  url= (String)output.get("o_url");
			String  key= (String)output.get("o_key");
			String  aut= (String)output.get("o_authorization");
			credencial.setUrl(url);
			credencial.setKey(key);
			credencial.setAuthorization(aut);
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return credencial;	
	}

	public String getRuta(Integer tipo) {
		String ruta="";
		Map output = storedRutasBpmAPI.execute(tipo);
		ruta= (String)output.get("o_url");
		return ruta;
	}

	public StoredCredencialesBpmAPI getStoredCredencialesBpmAPI() {
		return storedCredencialesBpmAPI;
	}

	public void setStoredCredencialesBpmAPI(StoredCredencialesBpmAPI storedCredencialesBpmAPI) {
		this.storedCredencialesBpmAPI = storedCredencialesBpmAPI;
	}


	public StoredRutasBpmAPI getStoredRutasBpmAPI() {
		return storedRutasBpmAPI;
	}


	public void setStoredRutasBpmAPI(StoredRutasBpmAPI storedRutasBpmAPI) {
		this.storedRutasBpmAPI = storedRutasBpmAPI;
	}




	

	
	
}
