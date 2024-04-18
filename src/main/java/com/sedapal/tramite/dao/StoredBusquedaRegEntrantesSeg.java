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

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;

public class StoredBusquedaRegEntrantesSeg extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_RPTE_GERENCIAL_TRABAJADOR";	
	private static final String REGISTRO = "v_correlativo";
	private static final String VAREA = "v_area";
	private static final String ANNO = "v_ano";
	private static final String VTIPODOC = "v_tipo";
	
		
		
	public StoredBusquedaRegEntrantesSeg() {
		// TODO Auto-generated constructor stub
	}

	public StoredBusquedaRegEntrantesSeg(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(REGISTRO, Types.VARCHAR));
		declareParameter(new SqlParameter(VAREA, Types.VARCHAR));
		declareParameter(new SqlParameter(ANNO, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlOutParameter("seguimiento_registro",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String registro, String area, String anno, String tipodoc) {
		System.out.println("Execute Stored..."+ registro+"   "+ area +"   "+ anno +"   "+ tipodoc);
		Map inputs = new HashMap();	
		inputs.put(REGISTRO, registro);
		inputs.put(VAREA, area);
		inputs.put(ANNO, anno);	
		inputs.put(VTIPODOC, tipodoc);
		return super.execute(inputs);
	}
	
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		SeguimientoEntranteBean bean = new SeguimientoEntranteBean();	
		bean.setCorrelativo(rs.getInt("correlativo"));
		bean.setSeguimiento(rs.getInt("seguimiento"));
		bean.setFecderivado(rs.getDate("fecderivacion"));
		bean.setEstado(rs.getString("estado"));
		bean.setAbrevremite(rs.getString("remitente"));
		bean.setNombre_remitente(rs.getString("nombre_remitente"));
		bean.setAbrevderivado(rs.getString("nombrederivado"));		
		bean.setNombre_derivado(rs.getString("nombre_derivado"));				
		bean.setComentario(rs.getString("comentario"));
		return bean;
		 
	}

}
