package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.driver.OracleTypes;

public class StoredDocSalRegEntrada extends StoredProcedure {
	private static final String SPROC_NAME = "s_guia.ATDC_DOC_SAL_REG_ENTRADA";
	private static final String NREG = "v_reg";
	private static final String NANIO = "v_anio";
	private static final String NCODSAL = "v_codsal";
	private static final String NDOCANIO = "v_doc_anio";
	private static final String NCODAREA = "v_area";
	private static final String VRESACT = "v_login";

	public StoredDocSalRegEntrada() {
	}

	public StoredDocSalRegEntrada(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NREG, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(NANIO, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(NCODSAL, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(NDOCANIO, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(NCODAREA, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		compile();
	}

	public Map execute(int registro, int anio, int codigo, int docAnio, int area, String login) {
		Map<String, Object> inputs = new HashMap<>();
		inputs.put(NREG, registro);
		inputs.put(NANIO, anio);
		inputs.put(NCODSAL, codigo);
		inputs.put(NDOCANIO, docAnio);
		inputs.put(NCODAREA, area);
		inputs.put(VRESACT, login);
		return super.execute(inputs);
	}

}
