package com.sedapal.tramite.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.UsersBean;


public class StoredActualizarUsers extends StoredProcedure{

	private static final String SPROC_NAME = "ATDC_UPDATE_USERS";	                              
	private static final String VCODUSUARIO = "v_usuario";
	private static final String VDESCRIPCION = "v_descripcion";
	private static final String NCODFICHA = "v_ficha";
	private static final String VPASS = "v_pass";	
	private static final String VESTADO = "v_estado";	
	private static final String DFECCREACION = "d_fecha";
	private static final String NCODPERFIL = "v_perfil";
	private static final String NCODAREA = "v_area";
	private static final String VRESPONSABLE = "v_responsable";
	private static final String NCONEXION = "v_conexion";
			
	public StoredActualizarUsers() {
		// TODO Auto-generated constructor stub

	}


	public StoredActualizarUsers(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(VCODUSUARIO, Types.VARCHAR));
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODFICHA, Types.VARCHAR));		
		declareParameter(new SqlParameter(VPASS, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECCREACION, Types.DATE));
		declareParameter(new SqlParameter(NCODPERFIL, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESPONSABLE, Types.VARCHAR));
		declareParameter(new SqlParameter(NCONEXION, Types.VARCHAR));
		compile();
	}

	

	public Map execute(UsersBean usersBean) {
		Map inputs = new HashMap();		
		inputs.put(VCODUSUARIO, usersBean.getLogin());
		inputs.put(VDESCRIPCION, usersBean.getNombre());		
		inputs.put(NCODFICHA, usersBean.getFicha());
		inputs.put(VPASS, usersBean.getPassword());		
		inputs.put(VESTADO, usersBean.getEstado());
		inputs.put(DFECCREACION, usersBean.getFecha());
		inputs.put(NCODPERFIL, usersBean.getNcodperfil());	
		inputs.put(NCODAREA, String.valueOf(usersBean.getNcodarea()));
		inputs.put(VRESPONSABLE, usersBean.getReponsable());
		inputs.put(NCONEXION, usersBean.getNconexion());
		return super.execute(inputs);
	}
}