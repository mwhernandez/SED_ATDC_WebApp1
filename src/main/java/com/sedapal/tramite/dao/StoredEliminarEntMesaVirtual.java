package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.EntranteBean;

public class StoredEliminarEntMesaVirtual extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_DELETE_DOC_ENT_MESA_VIRTUAL";
	private static final String NANO = "v_anno";
	private static final String NORIGEN = "v_origen";	
	private static final String NCORRELATIVO = "v_corr";	
	private static final String VRESACT  = "v_login";
	private static final String TIPOINGRESO  = "v_tipo";
	private static final String MOTIVOINGRESO  = "v_motivo";
			
	public StoredEliminarEntMesaVirtual() {
		// TODO Auto-generated constructor stub
	}

	public StoredEliminarEntMesaVirtual(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));	
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		declareParameter(new SqlParameter(TIPOINGRESO, Types.VARCHAR));
		declareParameter(new SqlParameter(MOTIVOINGRESO, Types.VARCHAR));
		compile();
	}

	public Map execute(String anno, String origen, String codigo, String login, String tipoingreso, String motivoingreso) {
		Map inputs = new HashMap();		
		inputs.put(NANO, anno);
		inputs.put(NORIGEN, origen);			
		inputs.put(NCORRELATIVO, codigo);			
		inputs.put(VRESACT, login);		
		inputs.put(TIPOINGRESO, tipoingreso);
		inputs.put(MOTIVOINGRESO, motivoingreso);
		System.out.println(anno);
		System.out.println(origen);		
		System.out.println(codigo);
		System.out.println(login);
		System.out.println(tipoingreso);
		System.out.println(motivoingreso);
		System.out.println("Fin de Execute SP eliminacion");
		
		return super.execute(inputs);
	}
}
