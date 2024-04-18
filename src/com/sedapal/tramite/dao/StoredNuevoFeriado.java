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

import com.sedapal.tramite.beans.FeriadosBean;


public class StoredNuevoFeriado extends StoredProcedure{
	private static final String SPROC_NAME = "S_GUIA.ATDC_NUEVO_FERIADO";	
	private static final String DFERIADO = "d_feriado";
	private static final String VDESCRIPCION = "v_descripcion";
	private static final String VTIPOFERIADO = "v_tipoferiado";
	private static final String VESTADO = "v_estado";	
	private static final String VRESCRE = "v_rescre";
	
	
	public StoredNuevoFeriado(DataSource dataSource) {
		super(dataSource, SPROC_NAME);			
		declareParameter(new SqlParameter(DFERIADO, Types.DATE));
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPOFERIADO, Types.VARCHAR));	
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESCRE, Types.VARCHAR));
		compile();
	}

	public Map execute(FeriadosBean feriadoBean) {
		Map inputs = new HashMap();				
		inputs.put(DFERIADO, feriadoBean.getDferiado());
		System.out.println(feriadoBean.getDferiado());
		inputs.put(VDESCRIPCION, feriadoBean.getVdescripcion());
		System.out.println(feriadoBean.getVdescripcion());
		inputs.put(VTIPOFERIADO, feriadoBean.getVtipoferiado());			
		System.out.println(feriadoBean.getVtipoferiado());
		inputs.put(VESTADO, feriadoBean.getVestado());
		System.out.println(feriadoBean.getVestado());
		inputs.put(VRESCRE, feriadoBean.getRespactual());
		System.out.println(feriadoBean.getRespactual());				
		return super.execute(inputs);
	}

}
