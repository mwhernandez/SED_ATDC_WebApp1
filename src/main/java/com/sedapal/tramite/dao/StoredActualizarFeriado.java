package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


import com.sedapal.tramite.beans.FeriadosBean;
import com.sedapal.tramite.beans.RemitenteBean;


public class StoredActualizarFeriado extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_UPDATE_FERIADO";	
	private static final String NCODIGO = "n_codigo";
	private static final String DFERIADO = "d_feriado";
	private static final String VDESCRIPCION = "v_descripcion";
	private static final String VTIPOFERIADO = "v_tipoferiado";
	private static final String VESTADO = "v_estado";	
	private static final String VRESCRE = "v_rescre";

			
	public StoredActualizarFeriado() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarFeriado(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NCODIGO, Types.VARCHAR));
		declareParameter(new SqlParameter(DFERIADO, Types.DATE));	
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));		
		declareParameter(new SqlParameter(VTIPOFERIADO, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));				
		declareParameter(new SqlParameter(VRESCRE, Types.VARCHAR));
		compile();
	}

	public Map execute(FeriadosBean feriadoBean) {
		Map inputs = new HashMap();				
		inputs.put(NCODIGO, feriadoBean.getNcodigo());	
		inputs.put(DFERIADO, feriadoBean.getDferiado());
		inputs.put(VDESCRIPCION, feriadoBean.getVdescripcion());		
		inputs.put(VTIPOFERIADO, feriadoBean.getVtipoferiado());
		inputs.put(VESTADO, feriadoBean.getVestado());				
		inputs.put(VRESCRE, feriadoBean.getRespactual());		
		//System.out.println(feriadoBean.getVcodestado());
		//System.out.println(feriadoBean.getVestado());
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//inputs.put(VRESACT, remitenteBean.getResponActual());
		//String date1 = sdf.format(remitenteBean.getFecha());
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
