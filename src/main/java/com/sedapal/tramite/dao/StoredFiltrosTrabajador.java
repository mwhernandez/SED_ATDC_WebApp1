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

import com.sedapal.tramite.beans.TrabajadorBean;

public class StoredFiltrosTrabajador extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_FILTRO_TRABAJADOR";	
	private static final String OPCION = "v_opcion";
	private static final String DETALLE = "v_detalle";
	
		
	public StoredFiltrosTrabajador() {
		// TODO Auto-generated constructor stub
	}

	public StoredFiltrosTrabajador(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));		
		declareParameter(new SqlOutParameter("filtrostrabajadores",OracleTypes.CURSOR, this));//cuenta_cursor?
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
		TrabajadorBean bean = new TrabajadorBean();
		bean.setCodigo(rs.getInt("codigo"));
		bean.setFicha(rs.getLong("ficha"));
		bean.setNombre(rs.getString("nombre"));
		bean.setApellidopaterno(rs.getString("paterno"));
		bean.setApellidomaterno(rs.getString("materno"));
		bean.setOnomastico(rs.getInt("onomastico"));
		bean.setAnexo(rs.getInt("anexo"));
		bean.setNombre_completo(rs.getString("nombre_completo"));		
		bean.setMes(rs.getString("mes"));
		bean.setCargo(rs.getString("cargo"));
		bean.setCorreo(rs.getString("correo"));
		bean.setEquipo(rs.getString("equipo"));
		bean.setLocal(rs.getString("local"));
		bean.setTipo(rs.getString("tipo"));
		return bean;
		 
	}

}
