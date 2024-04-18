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

public class StoredDeleteRemitente extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_DELETE_REMITENTE";
	
	private static final String NCODREMITENTE = "n_codigo";
	
			
	public StoredDeleteRemitente() {
		// TODO Auto-generated constructor stub
	}

	public StoredDeleteRemitente(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(NCODREMITENTE, Types.INTEGER));		
		compile();
	}

	public Map execute(int codigo) {
		Map inputs = new HashMap();
		inputs.put(NCODREMITENTE, codigo);		
		System.out.println("Fin de Execute SP");
		return super.execute(inputs);

	}

}
