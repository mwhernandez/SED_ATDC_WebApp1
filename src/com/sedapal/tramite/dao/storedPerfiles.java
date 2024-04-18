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

import com.sedapal.tramite.beans.PerfilBean;

public class storedPerfiles extends StoredProcedure implements RowMapper {
	
	private static final String SPROC_NAME = "S_GUIA.ATDC_PERFIL";
	private static final String NUM_TARJETA = "v_numero_tarjeta";
	
	public storedPerfiles() {
		// TODO Auto-generated constructor stub
	}
	
	public storedPerfiles(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("perfiles",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}
	public Map execute() {
		Map inputs = new HashMap();
		inputs.put(NUM_TARJETA, "");
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		PerfilBean bean = new PerfilBean();
		bean.setCodigo(rs.getInt("CODIGO"));
		bean.setNombre(rs.getString("NOMBRE"));
		bean.setEstado(rs.getString("ESTADO"));
		//System.out.println(bean.getCodigo());	
		//System.out.println(bean.getEstado());
		return bean;
	}
	
	

}
