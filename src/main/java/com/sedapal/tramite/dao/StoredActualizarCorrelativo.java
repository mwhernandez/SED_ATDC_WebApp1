package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;



import com.sedapal.tramite.beans.CorrelativosBean;
import com.sedapal.tramite.beans.TiposBean;


public class StoredActualizarCorrelativo extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_UPDATE_TIPOS_DOC";	
	private static final String VCODTIPO = "v_tipo";
	private static final String VDESCRIPCION = "v_descripcion";
	private static final String DFECCREACION = "d_fecha";	
	private static final String VESTADO = "v_estado";
	private static final String VRESPONSABLE = "v_responsable";
	private static final String VABREVIATURA = "v_abreviatura";	
			
	public StoredActualizarCorrelativo() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarCorrelativo(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(VCODTIPO, Types.VARCHAR));
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));		
		declareParameter(new SqlParameter(DFECCREACION, Types.DATE));		
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));	
		declareParameter(new SqlParameter(VRESPONSABLE, Types.VARCHAR));
		declareParameter(new SqlParameter(VABREVIATURA, Types.VARCHAR));
		compile();
	}

	public Map execute(CorrelativosBean correlativosBean) {
		Map inputs = new HashMap();				
		inputs.put(VCODTIPO, correlativosBean.getTipo());
		System.out.println(correlativosBean.getTipo());
		inputs.put(VDESCRIPCION, correlativosBean.getDescripcion());
		System.out.println(correlativosBean.getDescripcion());
		inputs.put(DFECCREACION, correlativosBean.getFecha());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		String date1 = sdf.format(correlativosBean.getFecha());
		System.out.println(correlativosBean.getFecha());
		inputs.put(VESTADO, correlativosBean.getEstado());
		System.out.println(correlativosBean.getEstado());
		inputs.put(VRESPONSABLE, correlativosBean.getResponsable());
		System.out.println(correlativosBean.getResponsable());	
		inputs.put(VABREVIATURA, correlativosBean.getAbreviatura());
		System.out.println(correlativosBean.getAbreviatura());
		System.out.println("Fin de Execute SP");
		return super.execute(inputs);
	}

}
