package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.PerfilBean;

public class StoredActualizarPerfil extends StoredProcedure{
	
	private static final String SPROC_NAME = "ATDC_UPDATE_PERFIL";	
	private static final String NCODPERFIL = "n_codigo";
	private static final String VDESCRIPCION = "v_nombre";
	private static final String VESTADO = "v_estado";
	
	
	public StoredActualizarPerfil(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NCODPERFIL, Types.VARCHAR));
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));	
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		compile();
	}

	public Map execute(PerfilBean perfilBean) {
		Map inputs = new HashMap();				
		inputs.put(NCODPERFIL, perfilBean.getCodigo());
		System.out.println(perfilBean.getCodigo());
		inputs.put(VDESCRIPCION, perfilBean.getNombre());
		System.out.println(perfilBean.getNombre());
		inputs.put(VESTADO, perfilBean.getEstado());		
		System.out.println(perfilBean.getEstado());	
		return super.execute(inputs);
	}

}
