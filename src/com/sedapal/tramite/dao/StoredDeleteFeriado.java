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

public class StoredDeleteFeriado extends StoredProcedure{
	private static final String SPROC_NAME = "S_GUIA.ATDC_DELETE_FERIADOS";	
	private static final String NCODIGO = "n_codigo";
	
			
	public StoredDeleteFeriado() {
		// TODO Auto-generated constructor stub
	}

	public StoredDeleteFeriado(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(NCODIGO, Types.INTEGER));		
		compile();
	}

	public Map execute(int codigo) {
		Map inputs = new HashMap();
		inputs.put(NCODIGO, codigo);		
		System.out.println("Fin de Execute SP");
		System.out.println(codigo);
		return super.execute(inputs);

	}

}
