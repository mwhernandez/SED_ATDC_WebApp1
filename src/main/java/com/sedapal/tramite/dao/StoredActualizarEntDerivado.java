package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.EntranteBean;

public class StoredActualizarEntDerivado extends StoredProcedure{
	//private static final String SPROC_NAME = "S_GUIA.ATDC_UPDATE_DOC_ENTR";
	private static final String SPROC_NAME = "ATDC_PKG_DOCUMENTO_ENTRANTE.ATDC_UPDATE_DOC_ENTR";
	private static final String NANO = "v_ano";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCORRELATIVO = "v_correlativo";
	private static final String VNUMDOC = "v_numdoc";
	//private static final String NREMITENTE = "v_remitente";	
	private static final String NFICHA_DIRIGIDO = "v_ficha";
	private static final String VASUNTO = "v_asunto";
	private static final String VREFERENCIA = "v_referencia";
	private static final String VOBSERVACION = "v_observacion";	
	private static final String VACCION = "v_accion";	
	private static final String VPRIORIDAD = "v_prioridad";
	private static final String VRESACT = "v_responsable";
	private static final String VUBIARCHIVO = "v_ubiarchivo";
	private static final String DFECDOC = "d_fechadoc";	
	private static final String NDIASPLAZO = "v_dias";
	private static final String VESTADO = "v_estado";
	
	public StoredActualizarEntDerivado() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarEntDerivado(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));	
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(VNUMDOC, Types.VARCHAR));		
		//declareParameter(new SqlParameter(NREMITENTE, Types.VARCHAR));
		declareParameter(new SqlParameter(NFICHA_DIRIGIDO, Types.VARCHAR));		
		declareParameter(new SqlParameter(VASUNTO, Types.VARCHAR));
		declareParameter(new SqlParameter(VREFERENCIA, Types.VARCHAR));
		declareParameter(new SqlParameter(VOBSERVACION, Types.VARCHAR));
		declareParameter(new SqlParameter(VACCION, Types.VARCHAR));
		declareParameter(new SqlParameter(VPRIORIDAD, Types.VARCHAR));	
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		declareParameter(new SqlParameter(VUBIARCHIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECDOC, Types.DATE));
		declareParameter(new SqlParameter(NDIASPLAZO, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
	
		compile();
	}

	public Map execute(EntranteBean entranteBean) {
		Map inputs = new HashMap();		
		inputs.put(NANO, String.valueOf(entranteBean.getNano()));
		System.out.println(entranteBean.getNano());
		inputs.put(NORIGEN, String.valueOf(entranteBean.getNorigen()));
		System.out.println(entranteBean.getNorigen());
		inputs.put(VTIPODOC, entranteBean.getVtipodoc());
		System.out.println(entranteBean.getVtipodoc());
		inputs.put(NCORRELATIVO, String.valueOf(entranteBean.getNcorrelativo()));
		System.out.println(entranteBean.getNcorrelativo());
		inputs.put(VNUMDOC, entranteBean.getVnumdoc());		
		System.out.println(entranteBean.getVnumdoc());
		//inputs.put(NREMITENTE, String.valueOf(entranteBean.getNremitente()));
		//System.out.println(entranteBean.getNremitente());
		inputs.put(NFICHA_DIRIGIDO, String.valueOf(entranteBean.getFicha_dirigido()));
		System.out.println(entranteBean.getFicha_dirigido());
		inputs.put(VASUNTO, entranteBean.getVasunto());
		System.out.println(entranteBean.getVasunto());
		inputs.put(VREFERENCIA, entranteBean.getVreferencia());
		System.out.println(entranteBean.getVreferencia());
		inputs.put(VOBSERVACION, entranteBean.getVobservacion());
		System.out.println(entranteBean.getVobservacion());
		inputs.put(VACCION, entranteBean.getVaccion());
		System.out.println(entranteBean.getVaccion());
		inputs.put(VPRIORIDAD, entranteBean.getVprioridad());
		System.out.println(entranteBean.getVprioridad());
		inputs.put(VRESACT, entranteBean.getVresact());	
		System.out.println(entranteBean.getVresact());
		inputs.put(VUBIARCHIVO, entranteBean.getVubiarchivo());	
		System.out.println(entranteBean.getVubiarchivo());
		inputs.put(DFECDOC, entranteBean.getDfecdoc());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");	
		System.out.println(entranteBean.getDfecdoc());
		inputs.put(NDIASPLAZO, String.valueOf(entranteBean.getNdiasplazo()));
		System.out.println(String.valueOf(entranteBean.getNdiasplazo()));
		inputs.put(VESTADO, entranteBean.getVestado());
		System.out.println(entranteBean.getVestado());
		//System.out.println(entranteBean.getNano());
		//System.out.println(entranteBean.getNcorrelativo());	
		//System.out.println(entranteBean.getDfecdoc());
		//System.out.println(entranteBean.getDfecregistro());
		//System.out.println("Fin de Execute SP actualizacion");
		return super.execute(inputs);
	}

}
