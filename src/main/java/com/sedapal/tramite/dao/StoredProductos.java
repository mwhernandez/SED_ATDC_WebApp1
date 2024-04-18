package com.sedapal.tramite.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.ProductoBean;

public class StoredProductos extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "SP_GET_PRODUCTOS";
	private static final String NUM_TARJETA = "v_numero_tarjeta";
		
	public StoredProductos() {
		// TODO Auto-generated constructor stub
	}

	public StoredProductos(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("productos",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute() {
		Map inputs = new HashMap();
		inputs.put(NUM_TARJETA, "");
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductoBean bean = new ProductoBean();
		bean.setCodigo(rs.getInt("codigo"));
		bean.setDescripcion(rs.getString("descripcion"));
		bean.setPrecio(rs.getDouble("precio"));
		bean.setStatus(rs.getString("status"));
		bean.setStock(rs.getInt("stock"));		
		return bean;
	}
}