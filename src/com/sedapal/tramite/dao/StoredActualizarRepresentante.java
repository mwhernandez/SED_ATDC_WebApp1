package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.RepresentanteBean;


public class StoredActualizarRepresentante extends StoredProcedure{
	private static final String SPROC_NAME = "S_GUIA.ATDC_UPDATE_REPRESENTANTE";	
	private static final String NCODREMITENTE = "vcodremite";
	private static final String NCODREPRESENTANTE = "vcodrepresentante";	
	private static final String VTIPOREPRESENTANTE = "vtiporepresentante";
	private static final String VTIPODOCUMENTO = "vtipodoc";
	private static final String VNRO_DOCUMENTO = "vnro_doc";	
	private static final String VNOMBRE = "v_nombre";
	private static final String VDIRECCION = "v_direccion";	
	private static final String VCORREO = "v_correo";
	private static final String VTELEFONO = "v_telefono";
	private static final String VFAX    = "v_fax";
	private static final String VCELULAR = "v_celular";
	private static final String VESTADO = "v_estado";
	private static final String VRESACT = "v_resactual";
				
	public StoredActualizarRepresentante() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarRepresentante(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NCODREMITENTE, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODREPRESENTANTE, Types.VARCHAR));		
		declareParameter(new SqlParameter(VTIPOREPRESENTANTE, Types.VARCHAR));		
		declareParameter(new SqlParameter(VTIPODOCUMENTO, Types.VARCHAR));	
		declareParameter(new SqlParameter(VNRO_DOCUMENTO, Types.VARCHAR));		
		declareParameter(new SqlParameter(VNOMBRE, Types.VARCHAR));		
		declareParameter(new SqlParameter(VDIRECCION, Types.VARCHAR));
		declareParameter(new SqlParameter(VCORREO, Types.VARCHAR));
		declareParameter(new SqlParameter(VTELEFONO, Types.VARCHAR));
		declareParameter(new SqlParameter(VFAX, Types.VARCHAR));
		declareParameter(new SqlParameter(VCELULAR, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		
		compile();
	}

	public Map execute(RepresentanteBean representanteBean) {
		Map inputs = new HashMap();				
		inputs.put(NCODREMITENTE, representanteBean.getCodremitente());	
		System.out.println(representanteBean.getCodremitente());
		inputs.put(NCODREPRESENTANTE, representanteBean.getCodrepresentante());
		System.out.println(representanteBean.getCodrepresentante());
		inputs.put(VTIPOREPRESENTANTE, representanteBean.getVtiporepresentante());
		System.out.println(representanteBean.getVtiporepresentante());
		inputs.put(VTIPODOCUMENTO, representanteBean.getVtipodocumento());
		System.out.println(representanteBean.getVtipodocumento());
		inputs.put(VNRO_DOCUMENTO, representanteBean.getVnumerodocumento());
		System.out.println(representanteBean.getVnumerodocumento());
		inputs.put(VNOMBRE, representanteBean.getVnombre());
		System.out.println(representanteBean.getVnombre());
		inputs.put(VDIRECCION, representanteBean.getVdireccion());
		System.out.println(representanteBean.getVdireccion());
		inputs.put(VCORREO, representanteBean.getVcorreo());
		System.out.println(representanteBean.getVcorreo());
		inputs.put(VTELEFONO, representanteBean.getVtelefono());
		System.out.println(representanteBean.getVtelefono());
		inputs.put(VFAX, representanteBean.getVfax());
		System.out.println(representanteBean.getVfax());
		inputs.put(VCELULAR, representanteBean.getVcelular());
		System.out.println(representanteBean.getVcelular());
		inputs.put(VESTADO, representanteBean.getVestado());
		System.out.println(representanteBean.getVestado());
		inputs.put(VRESACT, representanteBean.getVresact());
		System.out.println(representanteBean.getVresact());
		return super.execute(inputs);
	}

}
