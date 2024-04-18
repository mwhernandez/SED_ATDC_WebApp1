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

import com.sedapal.tramite.beans.FeriadosBean;
import com.sedapal.tramite.beans.RemitenteBean;

public class StoredFeriado extends StoredProcedure implements RowMapper {
	
	private static final String SPROC_NAME = "S_GUIA.ATDC_FERIADOS";
	//private static final String NUM_TARJETA = "v_numero_tarjeta";
	
	public StoredFeriado(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		//declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("feriado",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute() {
		Map inputs = new HashMap();
		//inputs.put(NUM_TARJETA, "");
		return super.execute(inputs);
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		FeriadosBean bean = new FeriadosBean();
		bean.setNcodigo(rs.getInt("codigo"));
		bean.setDferiado(rs.getDate("feriado"));
		bean.setVdescripcion(rs.getString("descripcion"));
		bean.setVtipoferiado(rs.getString("tipo"));
		bean.setVestado(rs.getString("estado"));
		bean.setRespactual(rs.getString("respcrea"));
		bean.setResponsable(rs.getString("respact"));
		bean.setDfeccrea(rs.getDate("fcreacion"));
		bean.setDfecactual(rs.getDate("factual"));		
		return bean;
		 
	}

}
