package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.driver.OracleTypes;

public class StoredEliminarAnexoDocumento extends StoredProcedure {
	private static final String SPROC_NAME = "S_GUIA.ATDC_ELIMINAR_ANEXO_DOCUMENTO";
	private static final String NANO = "v_ano";
	private static final String COD_SAL = "v_codsal";
	private static final String LOGIN = "v_login";
	private static final String ANEXO = "v_cod_anexo";

	public StoredEliminarAnexoDocumento() {
	}

	public StoredEliminarAnexoDocumento(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(COD_SAL, Types.VARCHAR));
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(LOGIN, Types.VARCHAR));
		declareParameter(new SqlParameter(ANEXO, OracleTypes.NUMBER));
		compile();
	}

	public Map execute(int codSal, int anio, String login, int anexo) {
		Map inputs = new HashMap();
		inputs.put(COD_SAL, String.valueOf(codSal));
		inputs.put(NANO, String.valueOf(anio));
		inputs.put(LOGIN, login);
		inputs.put(ANEXO, anexo);
		return super.execute(inputs);
	}

}
