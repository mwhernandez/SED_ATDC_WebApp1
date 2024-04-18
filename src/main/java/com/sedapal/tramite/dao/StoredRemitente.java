package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.RemitenteBean;

public class StoredRemitente extends StoredProcedure implements RowMapper {
	
	private static final String SPROC_NAME = "ATDC_REMITENTE";
	private static final String NUM_TARJETA = "v_numero_tarjeta";
	
	public StoredRemitente(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("remitente",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute() {
		Map inputs = new HashMap();
		inputs.put(NUM_TARJETA, "");
		return super.execute(inputs);
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		RemitenteBean bean = new RemitenteBean();
		bean.setCodigo(rs.getInt("CODIGO"));
		bean.setDescripcion(rs.getString("DESCRIPCION"));		
		bean.setEstado(rs.getString("ESTADO"));
		bean.setFecha(rs.getDate("FECHA"));
		bean.setResponsable(rs.getString("RESPONSABLE"));		
		return bean;
		 
	}

}
