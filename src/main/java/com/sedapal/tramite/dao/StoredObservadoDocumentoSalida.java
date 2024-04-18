package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class StoredObservadoDocumentoSalida extends StoredProcedure {
	private static final String SPROC_NAME = "ATDC_OBSERVADO_DOCUMENTO_SALIENTE";
	private static final String NCODSAL = "v_codsal";
	private static final String NANO = "v_anno";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCODAREA = "v_area";
	private static final String VRESACT = "v_login";
	private static final String VOBSERVACION = "v_obs";

	public StoredObservadoDocumentoSalida() {
	}

	public StoredObservadoDocumentoSalida(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NCODSAL, Types.VARCHAR));
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		declareParameter(new SqlParameter(VOBSERVACION, Types.VARCHAR));
		compile();
	}

	public Map execute(String codigo, String anno, String origen, String tipodoc, String area, String login, String observacion) {
		Map<String, Object> inputs = new HashMap<>();
		inputs.put(NCODSAL, codigo);
		inputs.put(NANO, anno);
		inputs.put(NORIGEN, origen);
		inputs.put(VTIPODOC, tipodoc);
		inputs.put(NCODAREA, area);
		inputs.put(VRESACT, login);
		inputs.put(VOBSERVACION, observacion);
		System.out.println("Observacion: " + observacion);
		return super.execute(inputs);
	}

}
