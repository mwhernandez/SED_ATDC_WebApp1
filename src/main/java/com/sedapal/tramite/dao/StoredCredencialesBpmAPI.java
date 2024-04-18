package com.sedapal.tramite.dao;


import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


public class StoredCredencialesBpmAPI extends StoredProcedure   {
	
	private static final String SPROC_NAME = "PCK_BPM_GENERAL.BPM_CREDENCIALES_API";
	private static final String NUM_TARJETA = "v_numero_tarjeta";
	
	
	
	public StoredCredencialesBpmAPI() {
		super();
		// TODO Auto-generated constructor stub
	}


	public StoredCredencialesBpmAPI(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("o_url", Types.VARCHAR));
		declareParameter(new SqlOutParameter("o_key", Types.VARCHAR));
		declareParameter(new SqlOutParameter("o_authorization", Types.VARCHAR));
		declareParameter(new SqlOutParameter("o_mensaje", Types.VARCHAR));
		declareParameter(new SqlOutParameter("o_retorno", Types.INTEGER));
		compile();
	}

	 
	public Map execute() {
		Map inputs = new HashMap();
		inputs.put(NUM_TARJETA, "");
		return super.execute(inputs);
	}
}
