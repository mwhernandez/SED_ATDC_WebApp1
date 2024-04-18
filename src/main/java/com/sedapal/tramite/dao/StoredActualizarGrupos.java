package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


import com.sedapal.tramite.beans.GrupoBean;
import com.sedapal.tramite.beans.TiposBean;


public class StoredActualizarGrupos extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_UPDATE_GRUPOS";	
	private static final String VCODIGO = "v_codigo";
	private static final String VDESCRIPCION = "v_nombre_grupo";		
	private static final String VESTADO = "v_estado";
	private static final String VABREVIATURA = "v_abreviatura";
	private static final String VRESPONSABLE = "v_responsable";
	private static final String VDIRIGIDO = "v_dirigido";
		
			
	public StoredActualizarGrupos() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarGrupos(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(VCODIGO, Types.VARCHAR));
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));	
		declareParameter(new SqlParameter(VABREVIATURA, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESPONSABLE, Types.VARCHAR));
		declareParameter(new SqlParameter(VDIRIGIDO, Types.VARCHAR));		
		compile();
	}

	public Map execute(GrupoBean grupoBean) {
		Map inputs = new HashMap();	
		System.out.println("Envio los datos de grupos para update");
		inputs.put(VCODIGO, grupoBean.getCodigo());
		System.out.println(grupoBean.getCodigo());
		inputs.put(VDESCRIPCION, grupoBean.getDescripcion());
		System.out.println(grupoBean.getDescripcion());
		inputs.put(VESTADO, grupoBean.getEstado());
		System.out.println(grupoBean.getEstado());
		inputs.put(VABREVIATURA, grupoBean.getAbreviatura());
		System.out.println(grupoBean.getAbreviatura());
		inputs.put(VRESPONSABLE, grupoBean.getRespcrea());
		System.out.println(grupoBean.getRespcrea());
		inputs.put(VDIRIGIDO, grupoBean.getVdirigido());
		System.out.println(grupoBean.getVdirigido());		
		System.out.println("Fin de Execute SP");
		return super.execute(inputs);
	}

}
