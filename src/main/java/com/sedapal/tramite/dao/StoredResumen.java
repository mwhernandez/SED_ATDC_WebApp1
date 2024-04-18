package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.ResumenBean;


public class StoredResumen extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_RESUMEN_ENTRADA";
	private static final String FECHA1 = "v_fecha1";
	private static final String FECHA2 = "v_fecha2";	
	private static final String AREA = "v_area";
	
		
	public StoredResumen() {
		// TODO Auto-generated constructor stub
	}

	public StoredResumen(DataSource dataSource) {
		super(dataSource, SPROC_NAME);	
		declareParameter(new SqlParameter(FECHA1, Types.VARCHAR));
		declareParameter(new SqlParameter(FECHA2, Types.VARCHAR));		
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("resumen",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String fecha1, String fecha2, String area) {
		Map inputs = new HashMap();
		inputs.put(FECHA1, fecha1);
		inputs.put(FECHA2, fecha2);		
		inputs.put(AREA, area);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResumenBean bean = new ResumenBean();
		bean.setArea(rs.getString("area"));
		bean.setEstado(rs.getString("estado"));
		bean.setResumen(rs.getLong("cantidad"));		
		return bean;
	}
}