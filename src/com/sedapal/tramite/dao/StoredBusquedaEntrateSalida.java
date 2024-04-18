package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.EntranteBean;

public class StoredBusquedaEntrateSalida extends StoredProcedure implements RowMapper {
	
	
	private static final String SPROC_NAME = "S_GUIA.ATDC_BUSQUEDA_ENTRAN_SALIDA";	
	private static final String NCODAREA = "v_codarea";
	private static final String OPCION = "v_opcion";
	private static final String DETALLE = "v_detalle";
	
	public StoredBusquedaEntrateSalida() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public StoredBusquedaEntrateSalida(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		//declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));	
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));	
		declareParameter(new SqlOutParameter("busquedaentradasalida",OracleTypes.CURSOR, this));//cuenta_cursor?		
		compile();
	}

	public Map execute(String area, String opcion, String detalle) {		
		Map inputs = new HashMap();	
		inputs.put(NCODAREA, area);
		inputs.put(OPCION, opcion);
		inputs.put(DETALLE, detalle);	
		return super.execute(inputs);
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		EntranteBean bean = new EntranteBean();		
		bean.setNcorrelativo(rs.getInt("CORRELATIVO"));
		bean.setVnumdoc(rs.getString("NUMDOC"));		
		bean.setVasunto(rs.getString("ASUNTO"));
		System.out.println(rs.getInt("CORRELATIVO"));
		System.out.println(rs.getString("NUMDOC"));
		System.out.println(rs.getString("CORRELATIVO"));
		return bean;
		 
	}

}

