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


import com.sedapal.tramite.beans.TiposBean;

public class StoredListaAsuntoPopup extends StoredProcedure implements RowMapper {
	


	private static final String SPROC_NAME = "ATDC_LISTA_POPUP_ASUNTO";	
		
	public StoredListaAsuntoPopup() {
		// TODO Auto-generated constructor stub
	}

	public StoredListaAsuntoPopup(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlOutParameter("asunto",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute() {
		Map inputs = new HashMap();		
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		TiposBean bean = new TiposBean();
		bean.setCodigo(rs.getString("CODIGO"));
		bean.setDescripcion(rs.getString("ASUNTO"));		
		bean.setFecha(rs.getDate("FECCREACION"));
		bean.setResponsable(rs.getString("USUARIO"));
		bean.setEstado(rs.getString("ESTADO"));		
		return bean;
	}

}
