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

import com.sedapal.tramite.beans.AreaBean;

public class StoredAreas extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_AREAS";
	private static final String NUM_TARJETA = "v_numero_tarjeta";
		
	public StoredAreas() {
		// TODO Auto-generated constructor stub
	}

	public StoredAreas(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("areas",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute() {
		Map inputs = new HashMap();
		inputs.put(NUM_TARJETA, "");
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		AreaBean bean = new AreaBean();
		bean.setCodigo(rs.getInt("codigo"));
		bean.setCentro(rs.getString("centro"));
		bean.setNombre(rs.getString("nombre"));
		bean.setAbreviatura(rs.getString("abreviatura"));
		bean.setTipo(rs.getString("tipo"));
		bean.setEstado(rs.getString("estado"));		
		return bean;
	}
}