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

import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;

public class StoredRepresentante extends StoredProcedure implements RowMapper {
	
	private static final String SPROC_NAME = "S_GUIA.ATDC_REPRESENTANTE";	
	private static final String NCODREMITENTE = "v_remitente";
	
	public StoredRepresentante(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NCODREMITENTE, Types.VARCHAR));
		declareParameter(new SqlOutParameter("representante",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(int codigo) {
		Map inputs = new HashMap();	
		inputs.put(NCODREMITENTE, codigo);
		return super.execute(inputs);
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		RepresentanteBean bean = new RepresentanteBean();		
		bean.setCodremitente(rs.getInt("ncodremitente"));
		bean.setCodrepresentante(rs.getInt("ncodrepresentante"));		
		bean.setVtiporepresentante(rs.getString("vtiporepresentante"));
		bean.setVtipodocumento(rs.getString("vtipodocumento"));
		bean.setVnumerodocumento(rs.getString("vnro_documento"));
		bean.setVnombre(rs.getString("vnombre"));
		bean.setVdireccion(rs.getString("vdireccion"));
		bean.setVcorreo(rs.getString("vcorreo"));
		bean.setVtelefono(rs.getString("vtelefono"));
		bean.setVfax(rs.getString("vfax"));
		bean.setVcelular(rs.getString("vcelular"));
		bean.setVestado(rs.getString("estado"));
		//bean.setEstado(rs.getString("vestado"));
		return bean;
		 
	}

}
