package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;



import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.TiposBean;


public class StoredActualizarRemitenteBPM extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_UPDATE_TIPOS_DOC";
	private static final String NCORRELATIVO = "n_correlativo";   
	private static final String VTIPDOC  = "v_tipodoc";      
	private static final String VNUMDOC  = "v_numerodoc";     
	private static final String VNOMBRE  = "v_nombre";
	private static final String VRESACT  = "v_resact";      
	private static final String DFECACT  = "d_fecact";             
	private static final String VCORREO  = "v_correo";    
	private static final String VTELEFONO = "v_telefono";    
	private static final String VDIRECCION = "v_direccion"; 
			
	public StoredActualizarRemitenteBPM() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarRemitenteBPM(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPDOC, Types.VARCHAR));		
		declareParameter(new SqlParameter(VNUMDOC, Types.DATE));		
		declareParameter(new SqlParameter(VNOMBRE, Types.VARCHAR));	
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECACT, Types.VARCHAR));
		declareParameter(new SqlParameter(VCORREO, Types.VARCHAR));
		declareParameter(new SqlParameter(VTELEFONO, Types.VARCHAR));
		declareParameter(new SqlParameter(VDIRECCION, Types.VARCHAR));
		compile();
	}

	public Map execute(RemitenteBPM remitentebpmBean) {
		Map inputs = new HashMap();		
		System.out.println("Inicio de Execute SP Remitente BPM");
		inputs.put(NCORRELATIVO, remitentebpmBean.getNcorrelativo());
		System.out.println(remitentebpmBean.getNcorrelativo());
		inputs.put(VTIPDOC, remitentebpmBean.getTipoDocumento());
		System.out.println(remitentebpmBean.getTipoDocumento());
		inputs.put(VNUMDOC, remitentebpmBean.getNroDocumento());
		System.out.println(remitentebpmBean.getNroDocumento());
		inputs.put(VNOMBRE, remitentebpmBean.getNombre());
		System.out.println(remitentebpmBean.getNombre());
		inputs.put(VRESACT, remitentebpmBean.getUserAct());
		System.out.println(remitentebpmBean.getUserAct());		
		inputs.put(VCORREO, remitentebpmBean.getCorreo());
		System.out.println(remitentebpmBean.getCorreo());
		inputs.put(VTELEFONO, remitentebpmBean.getTelefono());
		System.out.println(remitentebpmBean.getTelefono());
		inputs.put(VDIRECCION, remitentebpmBean.getDireccion());
		System.out.println(remitentebpmBean.getDireccion());
		System.out.println("Fin de Execute SP");
		return super.execute(inputs);
	}

}
