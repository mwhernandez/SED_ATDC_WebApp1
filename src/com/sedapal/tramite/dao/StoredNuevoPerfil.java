package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleSql;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.PerfilBean;
import com.sedapal.tramite.beans.RemitenteBean;


public class StoredNuevoPerfil extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_NUEVO_PERFIL";	
	private static final String VDESCRIPCION = "v_descripcion";
	private static final String VESTADO = "v_estado";
		
	
	public StoredNuevoPerfil(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		compile();
	}

	public Map execute(PerfilBean perfilBean) {
		Map inputs = new HashMap();
		inputs.put(VDESCRIPCION, perfilBean.getNombre());
		inputs.put(VESTADO, perfilBean.getEstado());		
		System.out.println(perfilBean.getEstado());	
		return super.execute(inputs);
	}

}
