package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.EntranteBean;

public class StoredActualizarLineadigitalizacionEnt extends StoredProcedure{
	private static final String SPROC_NAME = "S_GUIA.ATDC_UPDATE_DOCUMENTO_LINEA_DIGITAL";		
	private static final String NANO = "v_anno";
	private static final String NCORRELATIVO = "v_correlativo";
	private static final String VUBICACION = "v_ubicacion";	
	private static final String NLOTE  = "n_lote";
	private static final String VRESACT  = "v_login";
	
	
			
	public StoredActualizarLineadigitalizacionEnt() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarLineadigitalizacionEnt(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(VUBICACION, Types.VARCHAR));
		declareParameter(new SqlParameter(NLOTE, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(String anno, String correlativo, String ubicacion, String nlote, String login) {
		Map inputs = new HashMap();	
		
		inputs.put(NANO, anno);
		inputs.put(NCORRELATIVO, correlativo);			
		inputs.put(VUBICACION, ubicacion);
		inputs.put(NLOTE, nlote);
		inputs.put(VRESACT, login);	
		System.out.println("viendo los datos para sp");
		System.out.println(anno);
		System.out.println(correlativo);
		System.out.println(ubicacion);
		System.out.println(nlote);	
		System.out.println(login);			
		System.out.println("Fin de Execute SP eliminacion");
		
		return super.execute(inputs);
	}
}
