package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class StoredDerivado extends StoredProcedure{
	
		
	private static final String SPROC_NAME = "S_GUIA.ATDC_NUEVO_REMITENTE_DOC";
	private static final String NANO = "v_anio";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCORRELATIVO = "v_correlativo";
	private static final String NCODAREA = "v_area";
	private static final String VRESCRE = "v_login";
	private static final String NFICHA_DIRIGIDO = "v_ficha";	
			
	public StoredDerivado() {
		// TODO Auto-generated constructor stub
	}

	public StoredDerivado(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESCRE, Types.VARCHAR));				
		declareParameter(new SqlParameter(NFICHA_DIRIGIDO, Types.VARCHAR));		
		compile();
	}

	public Map execute(int anio, int origen, String tipodoc, long correlativo, int area, String login, long ficha) {
		Map inputs = new HashMap();	
		inputs.put(NANO, String.valueOf(anio));
		inputs.put(NORIGEN, String.valueOf(origen));
		inputs.put(VTIPODOC, tipodoc);
		inputs.put(NCORRELATIVO, String.valueOf(correlativo));		
		inputs.put(NCODAREA, String.valueOf(area));		
		inputs.put(VRESCRE, login);
		inputs.put(NFICHA_DIRIGIDO, String.valueOf(ficha));	
		return super.execute(inputs);
	}

}
