package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleSql;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import com.sedapal.tramite.beans.RemitenteBean;

public class StoredDeleteRepresentante extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_DELETE_REPRESENTANTE";
	
	private static final String NCODREMITENTE = "n_codremite";
	private static final String NCODREPRESENTANTE = "n_codrepresentante";
	
			
	public StoredDeleteRepresentante() {
		// TODO Auto-generated constructor stub
	}

	public StoredDeleteRepresentante(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(NCODREMITENTE, Types.INTEGER));
		declareParameter(new SqlParameter(NCODREPRESENTANTE, Types.INTEGER));
		compile();
	}

	public Map execute(int codremite, int codrepresentante) {
		Map inputs = new HashMap();
		inputs.put(NCODREMITENTE, codremite);		
		System.out.println(codremite);
		inputs.put(NCODREPRESENTANTE, codrepresentante);		
		System.out.println(codrepresentante);
		return super.execute(inputs);

	}

}
