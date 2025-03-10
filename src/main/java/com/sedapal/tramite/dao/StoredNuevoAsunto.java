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
import com.sedapal.tramite.beans.TiposBean;

public class StoredNuevoAsunto extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_NUEVO_ASUNTO";
	private static final String VDESCRIPCION = "v_descripcion";
	private static final String VESTADO = "v_estado";	
	private static final String VRESPONSABLE = "v_responsable";
	
			
	

	public StoredNuevoAsunto(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));		
		declareParameter(new SqlParameter(VRESPONSABLE, Types.VARCHAR));	
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(TiposBean tiposBean) {
		Map inputs = new HashMap();
		inputs.put(VDESCRIPCION, tiposBean.getDescripcion());
		System.out.println(tiposBean.getDescripcion());
		inputs.put(VESTADO, tiposBean.getEstado());
		System.out.println(tiposBean.getEstado());	
		inputs.put(VRESPONSABLE, tiposBean.getResponsable());
		System.out.println(tiposBean.getResponsable());
		return super.execute(inputs);
	}

}
