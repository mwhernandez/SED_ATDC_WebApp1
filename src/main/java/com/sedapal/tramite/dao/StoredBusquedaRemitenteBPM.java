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

import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.TiposBean;
import com.sedapal.tramite.beans.UsersBean;

public class StoredBusquedaRemitenteBPM extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_BUSQUEDA_REMITENTE_BPM";	
	private static final String OPCION = "v_opcion";
	private static final String DETALLE = "v_detalle";
	
		
	public StoredBusquedaRemitenteBPM() {
		// TODO Auto-generated constructor stub
	}

	public StoredBusquedaRemitenteBPM(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));		
		declareParameter(new SqlOutParameter("remitentebpm",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String opcion, String detalle) {
		System.out.println("Execute Stored..."+ opcion+"   "+ detalle);
		Map inputs = new HashMap();		
		inputs.put(OPCION, opcion);
		inputs.put(DETALLE, detalle);		
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
