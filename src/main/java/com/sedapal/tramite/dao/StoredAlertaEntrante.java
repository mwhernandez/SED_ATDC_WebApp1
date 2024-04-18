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

public class StoredAlertaEntrante extends StoredProcedure implements RowMapper {
	
	
	private static final String SPROC_NAME = "ATDC_ALERTA_DOC_ENTRAN";	
	private static final String NCODAREA = "v_codarea";
	
	public StoredAlertaEntrante() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public StoredAlertaEntrante(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		//declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));	
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));		
		declareParameter(new SqlOutParameter("entradaalerta",OracleTypes.CURSOR, this));//cuenta_cursor?		
		compile();
	}

	public Map execute(String area) {		
		Map inputs = new HashMap();		
		//inputs.put(NUM_TARJETA, "");
		inputs.put(NCODAREA, area);
		return super.execute(inputs);
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		EntranteBean bean = new EntranteBean();		
		bean.setNano(rs.getInt("ANO"));
		bean.setNcorrelativo(rs.getInt("CORRELATIVO"));
		bean.setVnumdoc(rs.getString("NUMDOC"));
		bean.setVremitente(rs.getString("REMITE"));		
		bean.setVasunto(rs.getString("ASUNTO"));
		bean.setVtipodoc(rs.getString("TIPODOC"));
		bean.setVdirigido(rs.getString("DERIVADO"));
		bean.setDfecplazo(rs.getDate("FECHAPLAZO"));
		bean.setDfecdoc(rs.getDate("FECHADOCUMENTO"));		
		//System.out.println(rs.getInt("CORRELATIVO"));
		//System.out.println(rs.getString("NUMDOC"));
		//System.out.println(rs.getString("CORRELATIVO"));
		return bean;
		 
	}

}

