package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


import com.sedapal.tramite.beans.AccionBean;


public class StoredActualizarAccion extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_UPDATE_ACCION";
	
	private static final String VCODTIPO = "v_tipo";
	private static final String VDESCRIPCION = "v_descripcion";
	private static final String DFECCREACION = "d_fecha";
	//private static final String VRESPONSABLE = "v_responsable";
	private static final String VESTADO = "v_estado";
			
	public StoredActualizarAccion() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarAccion(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(VCODTIPO, Types.VARCHAR));
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));		
		declareParameter(new SqlParameter(DFECCREACION, Types.DATE));
		//declareParameter(new SqlParameter(VRESPONSABLE, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));		
		compile();
	}

	public Map execute(AccionBean accionBean) {
		Map inputs = new HashMap();
				
		inputs.put(VCODTIPO, accionBean.getCodigo());
		System.out.println(accionBean.getCodigo());
		inputs.put(VDESCRIPCION, accionBean.getDescripcion());
		System.out.println(accionBean.getDescripcion());
		inputs.put(DFECCREACION, accionBean.getFecha());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		String date1 = sdf.format(accionBean.getFecha());
		System.out.println(accionBean.getFecha());
		//inputs.put(VRESPONSABLE, tiposBean.getResponsable());
		//System.out.println(tiposBean.getResponsable());				
		inputs.put(VESTADO, accionBean.getEstado());
		System.out.println(accionBean.getCodigo());
		System.out.println("Fin de Execute SP");
		return super.execute(inputs);
	}

}
