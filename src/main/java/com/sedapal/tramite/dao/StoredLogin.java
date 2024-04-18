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

import com.sedapal.tramite.beans.ProductoBean;

public class StoredLogin extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "S_GUIA.ATDC_VALDIVIA_USUARIO";
	private static final String LOGIN = "p_login";
	private static final String PWD = "p_password";
			
	public StoredLogin() {
		// TODO Auto-generated constructor stub
	}

	public StoredLogin(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(LOGIN, Types.VARCHAR));
		declareParameter(new SqlParameter(PWD, Types.VARCHAR));
		declareParameter(new SqlOutParameter("login",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String login, String password) {
		Map inputs = new HashMap();		
		inputs.put(LOGIN, login);
		inputs.put(PWD, password);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		//mapeo del bean
		ProductoBean bean = new ProductoBean();
		System.out.println(rs.getString("codigo"));
		bean.setCodigo(rs.getInt("codigo"));
		bean.setDescripcion(rs.getString("descripcion"));
		bean.setPrecio(rs.getDouble("precio"));
		bean.setStatus(rs.getString("status"));
		bean.setStock(rs.getInt("stock"));
		//bean.setTipo(rs.getString("tipo"));
		//bean.setFecha(rs.getDate("fecha"));
		return bean;
	}
}