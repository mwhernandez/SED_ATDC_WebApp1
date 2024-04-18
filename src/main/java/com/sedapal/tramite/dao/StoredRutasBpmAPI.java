package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class StoredRutasBpmAPI extends StoredProcedure   {
	
	private static final String SPROC_NAME = "PCK_BPM_GENERAL.BPM_RUTAS_API";

	public StoredRutasBpmAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StoredRutasBpmAPI(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter("i_tipo", Types.VARCHAR));
		declareParameter(new SqlOutParameter("o_url", Types.VARCHAR));
		declareParameter(new SqlOutParameter("o_mensaje", Types.VARCHAR));
		declareParameter(new SqlOutParameter("o_retorno", Types.INTEGER));
		compile();
	}
	
	public Map execute(Integer tipo) {
		Map inputs = new HashMap();
		inputs.put("i_tipo", Integer.toString(tipo));
		return super.execute(inputs);
	}

	
}
