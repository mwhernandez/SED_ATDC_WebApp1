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

import com.sedapal.tramite.beans.RemitenteBean;


public class StoredNuevoRemitente extends StoredProcedure{
	private static final String SPROC_NAME = "S_GUIA.ATDC_NUEVO_REMITENTE";	
	private static final String VDESCRIPCION = "v_descripcion";	
	private static final String NCODAREA = "v_area";
	private static final String VESTADO = "v_estado";
	private static final String DFECCRE = "d_fecha";
	private static final String VRESCRE = "v_responsable";
	private static final String VRESACT = "v_resactual";
	
	
	
	
	public StoredNuevoRemitente(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));		
		declareParameter(new SqlParameter(NCODAREA, Types.NUMERIC));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECCRE, Types.DATE));	
		declareParameter(new SqlParameter(VRESCRE, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));	
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(RemitenteBean remitenteBean) {
		Map inputs = new HashMap();
		inputs.put(VDESCRIPCION, remitenteBean.getDescripcion());
		System.out.println(remitenteBean.getDescripcion());		
		inputs.put(NCODAREA, remitenteBean.getArea());
		System.out.println(remitenteBean.getArea());
		inputs.put(VESTADO, remitenteBean.getEstado());
		System.out.println(remitenteBean.getEstado());
		inputs.put(DFECCRE, remitenteBean.getFecha());
		System.out.println(remitenteBean.getFecha());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		System.out.println(remitenteBean.getFecha());
		inputs.put(VRESCRE, remitenteBean.getResponsable());
		System.out.println(remitenteBean.getResponsable());
		inputs.put(VRESACT, remitenteBean.getResponsable());
		System.out.println(remitenteBean.getResponsable());
		return super.execute(inputs);
	}

}
