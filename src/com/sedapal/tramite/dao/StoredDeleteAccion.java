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
import com.sedapal.tramite.beans.AccionBean;

public class StoredDeleteAccion extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_DELETE_ACCION";
	private static final String VCODTIPO = "v_tipo";
	
			
	public StoredDeleteAccion() {
		// TODO Auto-generated constructor stub
	}

	public StoredDeleteAccion(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(VCODTIPO, Types.VARCHAR));		
		compile();
	}

	public Map execute(String codigo) {
		Map inputs = new HashMap();
		inputs.put(VCODTIPO, codigo);		
		System.out.println("Fin de Execute SP");
		return super.execute(inputs);

	}


}
