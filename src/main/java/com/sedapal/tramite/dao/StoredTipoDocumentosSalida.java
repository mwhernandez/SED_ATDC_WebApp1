package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.DocumentoSalidaBean;

public class StoredTipoDocumentosSalida extends StoredProcedure implements RowMapper {
													 
	private static final String SPROC_NAME = "ATDC_TIPOS_DOC_SALIDA";
	private static final String NANO = "v_anno";
	private static final String AREA = "v_area";
	private static final String CODIGO = "v_correlativo";
	
	
		
	public StoredTipoDocumentosSalida() {
		// TODO Auto-generated constructor stub
	}

	public StoredTipoDocumentosSalida(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlParameter(CODIGO, Types.VARCHAR));
		declareParameter(new SqlOutParameter("tipodocumentosalida",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(int anno, int area, long correlativo) {
		System.out.println("TIPOS_DOCUMENTO DE SALIDA");
		System.out.println("Execute Stored..."+ anno+"   "+ area +"  "+ correlativo);
		Map inputs = new HashMap();
		inputs.put(NANO, anno);
		inputs.put(AREA, area);
		inputs.put(CODIGO, correlativo);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		AreaBean bean = new AreaBean();	
		bean.setTipo(rs.getString("tipo"));
		bean.setNombre(rs.getString("nombre"));					
		bean.setAbreviatura(rs.getString("nombrecompleto"));
		
				
		return bean;
	}
}