package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


import com.sedapal.tramite.beans.UsersBean;


public class StoredNuevoUsers extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_NUEVO_USERS";
	
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
	
	
	public StoredNuevoUsers(DataSource dataSource) {
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
		System.out.println("Pinto las variables de nuevo usuario");
		inputs.put(VCODUSUARIO, usersBean.getLogin());		
		System.out.println(usersBean.getLogin());
		inputs.put(VDESCRIPCION, usersBean.getNombre());
		System.out.println(usersBean.getNombre());
		inputs.put(NCODFICHA, usersBean.getFicha());
		System.out.println(usersBean.getFicha());
		inputs.put(VPASS, usersBean.getPassword());
		System.out.println(usersBean.getPassword());
		inputs.put(VESTADO, usersBean.getEstado());
		System.out.println(usersBean.getEstado());
		inputs.put(DFECCREACION, usersBean.getFecha());
		System.out.println(usersBean.getFecha());
		inputs.put(NCODPERFIL, usersBean.getPerfil());
		System.out.println(usersBean.getPerfil());
		inputs.put(NCODAREA, usersBean.getNomequipo());
		System.out.println(usersBean.getNomequipo());
		inputs.put(VRESPONSABLE, usersBean.getReponsable());
		System.out.println(usersBean.getReponsable());
		inputs.put(NCONEXION, usersBean.getNconexion());		
		System.out.println(usersBean.getNconexion());
		System.out.println("Fin de las variables de nuevo usuario");
		return super.execute(inputs);
	}

}
