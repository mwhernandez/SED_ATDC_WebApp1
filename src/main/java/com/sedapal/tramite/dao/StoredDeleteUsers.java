package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class StoredDeleteUsers extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_DELETE_USERS";
	
	private static final String VCODUSUARIO = "v_login";
	
			
	public StoredDeleteUsers() {
		// TODO Auto-generated constructor stub
	}

	public StoredDeleteUsers(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(VCODUSUARIO, Types.VARCHAR));		
		compile();
	}

	public Map execute(String codigo) {
		Map inputs = new HashMap();
		inputs.put(VCODUSUARIO, codigo);		
		System.out.println("Fin de Execute SP");
		return super.execute(inputs);

	}

}
