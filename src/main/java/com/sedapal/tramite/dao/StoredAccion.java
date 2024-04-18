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

import com.sedapal.tramite.beans.AccionBean;

public class StoredAccion extends StoredProcedure implements RowMapper{

	private static final String SPROC_NAME = "ATDC_ACCION";
	private static final String NUM_TARJETA = "v_numero_tarjeta";
	
	public StoredAccion() {
		// TODO Auto-generated constructor stub
	}
	
	public StoredAccion(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("accion",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute() {
		Map inputs = new HashMap();
		inputs.put(NUM_TARJETA, "");
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccionBean bean = new AccionBean();
		bean.setCodigo(rs.getInt("codigo"));
		bean.setDescripcion(rs.getString("descripcion"));		
		bean.setFecha(rs.getDate("fecha"));
		bean.setResponsable(rs.getString("responsable"));
		bean.setEstado(rs.getString("estado"));
		return bean;
	}

}
