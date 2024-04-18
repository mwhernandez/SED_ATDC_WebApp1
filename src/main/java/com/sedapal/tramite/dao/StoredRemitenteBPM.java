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

import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.RemitenteBean;

public class StoredRemitenteBPM extends StoredProcedure implements RowMapper {
	
	private static final String SPROC_NAME = "ATDC_REMITENTE_BPM";
	private static final String NUM_TARJETA = "v_numero_tarjeta";
	
	public StoredRemitenteBPM(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("remitentebpm",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute() {
		Map inputs = new HashMap();
		inputs.put(NUM_TARJETA, "");
		return super.execute(inputs);
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		RemitenteBPM bean = new RemitenteBPM();
		bean.setNcorrelativo(rs.getLong("CORRELATIVO"));
		bean.setTipoDocumento(rs.getString("TIPODOC"));
		bean.setNroDocumento(rs.getString("NUM_DOC"));
		bean.setNombre(rs.getString("NOMBRE"));				
		bean.setTelefono(rs.getString("TELEFONO"));		
		bean.setCorreo(rs.getString("CORREO"));
		bean.setDireccion(rs.getString("DIRECCION"));
		bean.setDescripciontipo(rs.getString("DESCTIPO"));
		return bean;
		 
	}

}
