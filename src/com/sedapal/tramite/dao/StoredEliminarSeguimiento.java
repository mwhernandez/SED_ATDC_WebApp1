package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class StoredEliminarSeguimiento extends StoredProcedure{
	private static final String SPROC_NAME = "S_GUIA.ATDC_DELETE_SEGUIMIENTO";	
	private static final String NCORRELATIVO = "v_correlativo";	
	private static final String NCODSEG  = "v_seguimiento";
	private static final String VRESACT  = "v_login";
	private static final String NAREADERIVADO = "v_areaderivado";
	
	
	public StoredEliminarSeguimiento() {
		// TODO Auto-generated constructor stub
	}
	
	public StoredEliminarSeguimiento(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODSEG, Types.VARCHAR));	
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));	
		declareParameter(new SqlParameter(NAREADERIVADO, Types.VARCHAR));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}
	
	public Map execute(String codigo, String codseg, String login, String area) {
		Map inputs = new HashMap();		
		inputs.put(NCORRELATIVO, codigo);			
		inputs.put(NCODSEG, codseg);
		inputs.put(VRESACT, login);
		inputs.put(NAREADERIVADO, area);
		System.out.println(codigo);		
		System.out.println(codseg);
		System.out.println(login);
		System.out.println(area);
		System.out.println("Fin de Execute SP eliminacion");
		
		return super.execute(inputs);
	}

}
