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

//SED-REQ-00001
public class StoredConsultaDocumentoBPM extends StoredProcedure implements RowMapper{

	private static final String SPROC_NAME = "PCK_BPM_GENERAL.BPM_CONSULTA_DOCUMENTO"; 
	private static final String v_correlativo = "v_correlativo";

	
	public StoredConsultaDocumentoBPM(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(v_correlativo, Types.NUMERIC));	
		declareParameter(new SqlOutParameter("CUR_DATOS",OracleTypes.CURSOR, this));
		compile();
	}
	
	public Map execute(Long valor) {
		Map inputs = new HashMap();		
		inputs.put(v_correlativo, valor);
		return super.execute(inputs);
	}
	
	public StoredConsultaDocumentoBPM() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		RemitenteBPM remitente = new RemitenteBPM();
		remitente.setTipoDocumento(rs.getString("vtipdoc"));
		remitente.setNroDocumento(rs.getString("vnumdoc"));
		return remitente;
	}
}
