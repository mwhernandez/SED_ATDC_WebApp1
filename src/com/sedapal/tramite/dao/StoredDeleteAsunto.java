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
import com.sedapal.tramite.beans.TiposBean;

public class StoredDeleteAsunto extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_DELETE_ASUNTO";
	private static final String NCODIGO = "n_codigo";
	private static final String VRESPONSABLE = "v_responsable";	
	
			
	public StoredDeleteAsunto() {
		// TODO Auto-generated constructor stub
	}

	public StoredDeleteAsunto(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(NCODIGO, Types.NUMERIC));		
		declareParameter(new SqlParameter(VRESPONSABLE, Types.VARCHAR));		
		compile();
	}

	public Map execute(String codigo, String responsable) {
		Map inputs = new HashMap();
		System.out.println("Inicio del SP");
		inputs.put(NCODIGO, codigo);	
		inputs.put(VRESPONSABLE, responsable);
		System.out.println(codigo);
		System.out.println(responsable);
		System.out.println("Fin de Execute SP");
		return super.execute(inputs);

	}

}
