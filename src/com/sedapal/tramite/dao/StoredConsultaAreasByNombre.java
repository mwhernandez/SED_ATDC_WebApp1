package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.RemitenteBPM;

import oracle.jdbc.driver.OracleTypes;

public class StoredConsultaAreasByNombre extends StoredProcedure implements RowMapper{
	
	private static final String SPROC_NAME = "PCK_BPM_GENERAL.BPM_CONSULTA_AREA"; 
	private static final String VALOR = "v_valor";

	public StoredConsultaAreasByNombre(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(VALOR, Types.VARCHAR));
		declareParameter(new SqlOutParameter("CUR_DATOS",OracleTypes.CURSOR, this));
		compile();
	}
	
	
	public StoredConsultaAreasByNombre() {
		// TODO Auto-generated constructor stub
	}


	public Map execute( String valor) {
		Map inputs = new HashMap();		
		inputs.put(VALOR, valor);	
		return super.execute(inputs);
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		RemitenteBPM remitente = new RemitenteBPM();
		remitente.setNcorrelativo(new Long(rs.getString("ncodarea")));
		remitente.setTipoDocumento("0");
		remitente.setNroDocumento(rs.getString("ncodarea"));
		remitente.setNombre(rs.getString("vdescripcion"));
		return remitente;
	}

	
}
