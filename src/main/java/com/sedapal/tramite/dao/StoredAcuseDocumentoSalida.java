package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.driver.OracleTypes;

public class StoredAcuseDocumentoSalida extends StoredProcedure {
	private static final String SPROC_NAME = "ATDC_ACUSE_DOCUMENTO_SALIENTE";
	private static final String NCODSAL = "v_codsal";
	private static final String NANO = "v_anno";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCODAREA = "v_area";
	private static final String VRESACT = "v_login";
	private static final String VRECIBO = "v_recibo";
	private static final String VCORRELATIVO = "v_correlativo";
	private static final String VCORRELANIO = "v_correlativo_anio";
	private static final String VCORRELAREA = "v_correl_area";

	public StoredAcuseDocumentoSalida() {
	}

	public StoredAcuseDocumentoSalida(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NCODSAL, Types.VARCHAR));
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		declareParameter(new SqlParameter(VRECIBO, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(VCORRELATIVO, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(VCORRELANIO, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(VCORRELAREA, OracleTypes.NUMBER));
		compile();
	}

	public Map execute(int codigo, int anno, String origen, String tipodoc, String area, String login, boolean recibo, 
			int correlativo, int correlativoAnio, int correlArea) {
		Map<String, Object> inputs = new HashMap<>();
		inputs.put(NCODSAL, String.valueOf(codigo));
		inputs.put(NANO, String.valueOf(anno));
		inputs.put(NORIGEN, origen);
		inputs.put(VTIPODOC, tipodoc);
		inputs.put(NCODAREA, area);
		inputs.put(VRESACT, login);
		inputs.put(VRECIBO, recibo ? 1 : 2);
		inputs.put(VCORRELATIVO, correlativo);
		inputs.put(VCORRELANIO, correlativoAnio);
		inputs.put(VCORRELAREA, correlArea);
		return super.execute(inputs);
	}

}
