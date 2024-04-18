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

import com.sedapal.tramite.beans.UsersBean;

public class StoredFiltros extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_FILTRO_USUARIO";	
	private static final String FECHA1 = "v_fecha1";
	private static final String FECHA2 = "v_fecha2";
	private static final String PERFIL = "v_perfil";
		
	public StoredFiltros() {
		// TODO Auto-generated constructor stub
	}

	public StoredFiltros(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(FECHA1, Types.VARCHAR));
		declareParameter(new SqlParameter(FECHA2, Types.VARCHAR));
		declareParameter(new SqlParameter(PERFIL, Types.VARCHAR));
		declareParameter(new SqlOutParameter("filtros",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String fecha1, String fecha2, String perfil) {
		System.out.println("Execute Stored..."+ fecha1+"   "+ fecha2 +"  "+perfil);
		Map inputs = new HashMap();		
		inputs.put(FECHA1, fecha1);
		inputs.put(FECHA2, fecha2);
		inputs.put(PERFIL, perfil);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		UsersBean bean = new UsersBean();			
		bean.setLogin(rs.getString("login"));
		bean.setFicha(rs.getInt("ficha"));
		bean.setNombre(rs.getString("nombre"));		
		bean.setEstado(rs.getString("estado"));		
		bean.setPassword(rs.getString("password"));		
		bean.setFecha(rs.getDate("fecha"));	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bean.setCodarea(rs.getInt("ncodarea"));
		bean.setNomequipo(rs.getString("nomequipo"));		
		bean.setPerfil(rs.getString("perfil"));	
		bean.setNconexion(rs.getInt("conexion"));
		//System.out.println(rs.getString("perfil"));
		return bean;
	}
}