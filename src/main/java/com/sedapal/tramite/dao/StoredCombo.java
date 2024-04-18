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

public class StoredCombo extends StoredProcedure{
	private static Logger logger = Logger.getLogger("R1");
	
	//private static final String SPROC_NAME = "S_GUIA.ATDC_NUEVO_REMITENTE_DOC";	
	private static final String SPROC_NAME = "ATDC_PKG_MAINT.ATDC_NUEVO_REMITENTE_DOC";
	private static final String EMPRESA = "v_empresa";
	private static final String AREA = "v_area";
	private static final String LOGIN = "v_responsable";
			
	public StoredCombo() {
		// TODO Auto-generated constructor stub
	}

	public StoredCombo(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(EMPRESA, Types.VARCHAR));
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlParameter(LOGIN, Types.VARCHAR));	
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(String nombreEmpresa, String codArea, String login) {
		Map inputs = new HashMap();		
		inputs.put(EMPRESA, nombreEmpresa);
		inputs.put(AREA, codArea);		
		inputs.put(LOGIN, login);
		return super.execute(inputs);
	}

}

