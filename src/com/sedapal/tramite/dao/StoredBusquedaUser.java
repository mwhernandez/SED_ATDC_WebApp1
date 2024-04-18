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

import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.UsersBean;

public class StoredBusquedaUser extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "S_GUIA.ATDC_BUSQUEDA_USER";	
	private static final String OPCION = "v_opcion";
	private static final String DETALLE = "v_detalle";
	
		
	public StoredBusquedaUser() {
		// TODO Auto-generated constructor stub
	}

	public StoredBusquedaUser(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));		
		declareParameter(new SqlOutParameter("busquedauser",OracleTypes.CURSOR, this));//cuenta_cursor?
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
		return bean;
		 
	}

}
