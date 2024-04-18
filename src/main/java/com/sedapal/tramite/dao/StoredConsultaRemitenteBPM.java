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

//SED-FON-161
public class StoredConsultaRemitenteBPM extends StoredProcedure implements RowMapper{

	private static final String SPROC_NAME = "PCK_BPM_GENERAL.BPM_CONSULTA_REMITENTE"; 
	private static final String TIPO = "v_tipo";
	private static final String VALOR = "v_valor";
	
	
	public StoredConsultaRemitenteBPM(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(TIPO, Types.VARCHAR));	
		declareParameter(new SqlParameter(VALOR, Types.VARCHAR));
		declareParameter(new SqlOutParameter("CUR_DATOS",OracleTypes.CURSOR, this));
		compile();
	}
	
	public Map execute(String tipo, String valor) {
		Map inputs = new HashMap();		
		inputs.put(TIPO, tipo);
		System.out.println(tipo);
		inputs.put(VALOR, valor);
	
		return super.execute(inputs);
	}
	
	public StoredConsultaRemitenteBPM() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		RemitenteBPM remitente = new RemitenteBPM();
		remitente.setNcorrelativo(rs.getLong("ncorrelativo"));
		remitente.setTipoDocumento(rs.getString("vtipdoc"));
		remitente.setNroDocumento(rs.getString("vnumdoc"));
		remitente.setNombre(rs.getString("vnombre"));
		return remitente;
	}

}
