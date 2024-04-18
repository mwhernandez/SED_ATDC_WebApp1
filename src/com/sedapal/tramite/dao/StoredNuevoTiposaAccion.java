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

import com.sedapal.tramite.beans.AccionBean;
import com.sedapal.tramite.beans.TiposBean;

public class StoredNuevoTiposaAccion extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_NUEVO_TIPO_ACCION";

	private static final String VDESCRIPCION = "v_descripcion";
	private static final String VESTADO = "v_estado";
	private static final String DFECCREACION = "d_fecha";
	//private static final String VRESPONSABLE = "v_responsable";
	
			
	

	public StoredNuevoTiposaAccion(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECCREACION, Types.DATE));
		//declareParameter(new SqlParameter(VRESPONSABLE, Types.VARCHAR));
		compile();
	}

	public Map execute(AccionBean accionBean) {
		Map inputs = new HashMap();
		inputs.put(VDESCRIPCION, accionBean.getDescripcion());
		System.out.println(accionBean.getDescripcion());
		inputs.put(VESTADO, accionBean.getEstado());
		System.out.println(accionBean.getEstado());
		inputs.put(DFECCREACION, accionBean.getFecha());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		System.out.println(accionBean.getEstado());				
		System.out.println(accionBean.getDescripcion());
		return super.execute(inputs);
	}

}
