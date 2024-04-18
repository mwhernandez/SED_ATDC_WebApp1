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


import com.sedapal.tramite.beans.GrupoBean;
import com.sedapal.tramite.beans.TiposBean;

public class StoredListaGerencias extends StoredProcedure implements RowMapper {
	


	private static final String SPROC_NAME = "ATDC_LISTA_GRUPOS";
	//private static final String NUM_TARJETA = "v_numero_tarjeta";
		
	public StoredListaGerencias() {
		// TODO Auto-generated constructor stub
	}

	public StoredListaGerencias(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		//declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("grupos",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute() {
		Map inputs = new HashMap();
		//inputs.put(NUM_TARJETA, "");
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		GrupoBean gruposbean = new GrupoBean();
		gruposbean.setCodigo(rs.getInt("codigo"));
		gruposbean.setDescripcion(rs.getString("descripcion"));
		gruposbean.setEstado(rs.getString("estado"));				
		gruposbean.setAbreviatura(rs.getString("abreviatura"));		
		gruposbean.setRespcrea(rs.getString("respcre"));
		gruposbean.setRespact(rs.getString("respact"));
		return gruposbean;
	}

}
