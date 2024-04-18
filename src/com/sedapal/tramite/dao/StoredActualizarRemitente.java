package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


import com.sedapal.tramite.beans.RemitenteBean;


public class StoredActualizarRemitente extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_UPDATE_REMITENTE";	
	private static final String NCODREMITENTE = "n_codigo";
	private static final String VDESCRIPCION = "v_descripcion";	
	private static final String NCODAREA = "v_area";
	private static final String VESTADO = "v_estado";
	private static final String DFECCRE = "d_fecha";	
	private static final String VRESACT = "v_resactual";

			
	public StoredActualizarRemitente() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarRemitente(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NCODREMITENTE, Types.VARCHAR));
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));		
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));		
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));	
		declareParameter(new SqlParameter(DFECCRE, Types.DATE));		
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		compile();
	}

	public Map execute(RemitenteBean remitenteBean) {
		Map inputs = new HashMap();				
		inputs.put(NCODREMITENTE, remitenteBean.getCodigo());		
		inputs.put(VDESCRIPCION, remitenteBean.getDescripcion());		
		inputs.put(NCODAREA, remitenteBean.getArea());
		inputs.put(VESTADO, remitenteBean.getEstado());				
		inputs.put(DFECCRE, remitenteBean.getFecha());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		inputs.put(VRESACT, remitenteBean.getResponActual());
		String date1 = sdf.format(remitenteBean.getFecha());
		//System.out.println(remitenteBean.getFecha());
		//inputs.put(VRESCRE, remitenteBean.getResponsable());
		//System.out.println(remitenteBean.getResponsable());			
		//inputs.put(VRESACT, remitenteBean.getResponActual());
		//System.out.println(remitenteBean.getCodigo());
		//System.out.println(remitenteBean.getCodigo());		
		//System.out.println("Fin de Execute SP");
		return super.execute(inputs);
	}

}
