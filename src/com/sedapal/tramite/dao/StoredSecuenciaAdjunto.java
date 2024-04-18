package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class StoredSecuenciaAdjunto extends StoredProcedure{
	private static Logger logger = Logger.getLogger("R1");
	
	private static final String SPROC_NAME = "S_GUIA.ATDC_SECUENCIAL_ADJUNTO";	
	private static final String LOGIN = "v_login";
			
	public StoredSecuenciaAdjunto() {
		// TODO Auto-generated constructor stub
	}

	public StoredSecuenciaAdjunto(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(LOGIN, Types.VARCHAR));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(String login) {
		Map inputs = new HashMap();		
		inputs.put(LOGIN, login);		
		return super.execute(inputs);
	}

}

