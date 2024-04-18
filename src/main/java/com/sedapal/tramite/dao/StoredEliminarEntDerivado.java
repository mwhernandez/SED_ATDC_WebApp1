package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.EntranteBean;

public class StoredEliminarEntDerivado extends StoredProcedure{
	private static final String SPROC_NAME = "S_GUIA.ATDC_DELETE_DOC_ENT";
	private static final String NANO = "v_anno";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCORRELATIVO = "v_corr";	
	private static final String VRESACT  = "v_login";
			
	public StoredEliminarEntDerivado() {
		// TODO Auto-generated constructor stub
	}

	public StoredEliminarEntDerivado(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));		
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		compile();
	}

	public Map execute(String anno, String origen, String tipodoc, String codigo, String login) {
		Map inputs = new HashMap();		
		inputs.put(NANO, anno);
		inputs.put(NORIGEN, origen);	
		inputs.put(VTIPODOC, tipodoc);
		inputs.put(NCORRELATIVO, codigo);			
		inputs.put(VRESACT, login);	
		
		System.out.println(codigo);
		System.out.println(anno);
		System.out.println(origen);
		System.out.println(tipodoc);		
		System.out.println(login);
		System.out.println("Fin de Execute SP eliminacion");
		
		return super.execute(inputs);
	}
}
