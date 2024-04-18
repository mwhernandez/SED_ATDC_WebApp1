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


import com.sedapal.tramite.beans.CentroBean;


public class StoredCentros extends StoredProcedure implements RowMapper{

	private static final String SPROC_NAME = "S_GUIA.ATDC_CENTRO";
	private static final String NUM_TARJETA = "v_numero_tarjeta";
	
	public StoredCentros(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("centro",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute() {
		Map inputs = new HashMap();
		inputs.put(NUM_TARJETA, "");
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CentroBean bean = new CentroBean();
		bean.setCodigo(rs.getInt("codigo"));
		//System.out.println(rs.getInt("codigo"));
		bean.setNombre(rs.getString("nombre"));
		//System.out.println(rs.getString("nombre"));
		bean.setDireccion(rs.getString("direccion"));
		bean.setAbreviatura(rs.getString("abreviatura"));
		bean.setEstado(rs.getString("estado"));		
		return bean;
	}

}
