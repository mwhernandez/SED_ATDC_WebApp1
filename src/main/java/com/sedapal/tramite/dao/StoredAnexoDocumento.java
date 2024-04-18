package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class StoredAnexoDocumento extends StoredProcedure {
	private static final String SPROC_NAME = "S_GUIA.ATDC_NUEVO_ANEXO_DOCUMENTO";
	private static final String NANO = "v_ano";
	private static final String COD_SAL = "v_codsal";
	private static final String LOGIN = "v_login";
	private static final String ANEXO = "v_anexo";
	private static final String ANEXO_NOMBRE = "v_anexo_name";

	public StoredAnexoDocumento() {
	}

	public StoredAnexoDocumento(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(COD_SAL, Types.VARCHAR));
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(LOGIN, Types.VARCHAR));
		declareParameter(new SqlParameter(ANEXO, Types.VARCHAR));
		declareParameter(new SqlParameter(ANEXO_NOMBRE, Types.VARCHAR));
		compile();
	}

	public Map execute(int codSal, int anio, String login, String anexo, String anexoNombre) {
		Map inputs = new HashMap();
		inputs.put(COD_SAL, String.valueOf(codSal));
		inputs.put(NANO, String.valueOf(anio));
		inputs.put(LOGIN, login);
		inputs.put(ANEXO, anexo);
		inputs.put(ANEXO_NOMBRE, anexoNombre);
		return super.execute(inputs);
	}

}
