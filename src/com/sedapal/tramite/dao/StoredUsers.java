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

public class StoredUsers extends StoredProcedure implements RowMapper{

	private static final String SPROC_NAME = "ATDC_LISTA_USUARIO";
	private static final String NUM_TARJETA = "v_numero_tarjeta";
	
	public StoredUsers() {
		// TODO Auto-generated constructor stub
	}
	
	public StoredUsers(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("users",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute() {
		Map inputs = new HashMap();
		inputs.put(NUM_TARJETA, "");
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
		bean.setNcodperfil(rs.getInt("ncodperfil"));
		bean.setNconexion(rs.getInt("conexion"));
		bean.setCorreo(rs.getString("correo"));
		//bean.setSistema(rs.getString("sistema"));
		//System.out.println(bean.getNombre());		
		return bean;
	}

}
