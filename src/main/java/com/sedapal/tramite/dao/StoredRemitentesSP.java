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
import com.sedapal.tramite.beans.RemitenteBean;

public class StoredRemitentesSP extends StoredProcedure implements RowMapper {
	
	
	private static final String SPROC_NAME = "ATDC_REMITENTE_ACTIVOS";	
	private static final String NUM_TARJETA = "v_numero_tarjeta";
	private static final String DETALLE = "v_detalle";
	
	
	
	
	public StoredRemitentesSP(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));	
		declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));
		declareParameter(new SqlOutParameter("remitentesactivos",OracleTypes.CURSOR, this));//cuenta_cursor?		
		compile();
	}

	public Map execute(String detalle) {		
		Map inputs = new HashMap();	
		inputs.put(NUM_TARJETA, "");
		inputs.put(DETALLE, detalle);	
		return super.execute(inputs);
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		RemitenteBean bean = new RemitenteBean();	
		bean.setCodigo(rs.getInt("codigo"));
		bean.setDescripcion(rs.getString("nombre"));
		//bean.setNcorrelativo(rs.getInt("CORRELATIVO"));
		//bean.setVnumdoc(rs.getString("DESCRIPCION"));		
		//bean.setVasunto(rs.getString("ASUNTO"));
		System.out.println("Pinta combo");
		System.out.println(rs.getInt("codigo"));
		System.out.println(rs.getString("nombre"));		
		return bean;
		 
	}

}

