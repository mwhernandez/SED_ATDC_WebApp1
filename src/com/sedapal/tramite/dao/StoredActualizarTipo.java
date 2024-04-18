package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


import com.sedapal.tramite.beans.TiposBean;


public class StoredActualizarTipo extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_UPDATE_TIPOS_DOC";	
	private static final String VCODTIPO = "v_tipo";
	private static final String VDESCRIPCION = "v_descripcion";
	private static final String DFECCREACION = "d_fecha";	
	private static final String VESTADO = "v_estado";
	private static final String VRESPONSABLE = "v_responsable";
	private static final String VABREVIATURA = "v_abreviatura";	
			
	public StoredActualizarTipo() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarTipo(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(VCODTIPO, Types.VARCHAR));
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));		
		declareParameter(new SqlParameter(DFECCREACION, Types.DATE));		
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));	
		declareParameter(new SqlParameter(VRESPONSABLE, Types.VARCHAR));
		declareParameter(new SqlParameter(VABREVIATURA, Types.VARCHAR));
		compile();
	}

	public Map execute(TiposBean tiposBean) {
		Map inputs = new HashMap();				
		inputs.put(VCODTIPO, tiposBean.getTipo());
		System.out.println(tiposBean.getTipo());
		inputs.put(VDESCRIPCION, tiposBean.getDescripcion());
		System.out.println(tiposBean.getDescripcion());
		inputs.put(DFECCREACION, tiposBean.getFecha());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		String date1 = sdf.format(tiposBean.getFecha());
		System.out.println(tiposBean.getFecha());
		inputs.put(VESTADO, tiposBean.getEstado());
		System.out.println(tiposBean.getEstado());
		inputs.put(VRESPONSABLE, tiposBean.getResponsable());
		System.out.println(tiposBean.getResponsable());	
		inputs.put(VABREVIATURA, tiposBean.getAbreviatura());
		System.out.println(tiposBean.getAbreviatura());
		System.out.println("Fin de Execute SP");
		return super.execute(inputs);
	}

}
