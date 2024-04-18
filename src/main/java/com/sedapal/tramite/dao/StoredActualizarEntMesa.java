package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EntranteMesaBean;

public class StoredActualizarEntMesa extends StoredProcedure{	
	private static final String SPROC_NAME = "ATDC_PKG_DOCUMENTO_ENTRANTE.ATDC_UPDATE_DOC_ENTR_MESA";
	private static final String NANO = "v_ano";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCORRELATIVO = "v_correlativo";
	private static final String VNUMDOC = "v_numdoc";	
	private static final String NFICHA_DIRIGIDO = "v_ficha";
	private static final String VASUNTO = "v_asunto";
	private static final String VREFERENCIA = "v_referencia";
	private static final String VOBSERVACION = "v_observacion";	
	private static final String VPRIORIDAD = "v_prioridad";
	private static final String VRESACT = "v_responsable";
	private static final String VUBIARCHIVO = "v_ubiarchivo";	
	private static final String DFECDOC = "d_fechadoc";	
	private static final String NDIASPLAZO = "v_dias";
	private static final String VESTADO = "v_estado";
	private static final String NDIRIGIDO = "v_dirigido";	
	private static final String NREMITENTE = "v_remitente";		
	private static final String VREMITENTE = "v_areasremitente";
	private static final String NCODAREA = "v_codarea";
	private static final String DFECREGISTRO = "d_fecharegistro";
	private static final String VFOLIO =  "n_folio";
		
	
	public StoredActualizarEntMesa() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarEntMesa(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));	
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(VNUMDOC, Types.VARCHAR));				
		declareParameter(new SqlParameter(NFICHA_DIRIGIDO, Types.VARCHAR));		
		declareParameter(new SqlParameter(VASUNTO, Types.VARCHAR));
		declareParameter(new SqlParameter(VREFERENCIA, Types.VARCHAR));
		declareParameter(new SqlParameter(VOBSERVACION, Types.VARCHAR));	
		declareParameter(new SqlParameter(VPRIORIDAD, Types.VARCHAR));	
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		declareParameter(new SqlParameter(VUBIARCHIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECDOC, Types.DATE));
		declareParameter(new SqlParameter(NDIASPLAZO, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(NDIRIGIDO, Types.VARCHAR));
		declareParameter(new SqlParameter(NREMITENTE, Types.VARCHAR));
		declareParameter(new SqlParameter(VREMITENTE, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECREGISTRO, Types.DATE));
		declareParameter(new SqlParameter(VFOLIO, Types.VARCHAR));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(EntranteMesaBean entranteMesaBean) {
		Map inputs = new HashMap();		
		inputs.put(NANO, String.valueOf(entranteMesaBean.getNano()));
		System.out.println(entranteMesaBean.getNano());
		inputs.put(NORIGEN, String.valueOf(entranteMesaBean.getNorigen()));
		System.out.println(entranteMesaBean.getNorigen());
		inputs.put(VTIPODOC, entranteMesaBean.getVtipodoc());
		System.out.println(entranteMesaBean.getVtipodoc());
		inputs.put(NCORRELATIVO, String.valueOf(entranteMesaBean.getNcorrelativo()));
		System.out.println(entranteMesaBean.getNcorrelativo());
		inputs.put(VNUMDOC, entranteMesaBean.getVnumdoc());		
		System.out.println(entranteMesaBean.getVnumdoc());		
		inputs.put(NFICHA_DIRIGIDO, String.valueOf(entranteMesaBean.getFicha_dirigido()));
		System.out.println(entranteMesaBean.getFicha_dirigido());
		inputs.put(VASUNTO, entranteMesaBean.getVasunto());
		System.out.println(entranteMesaBean.getVasunto());
		inputs.put(VREFERENCIA, entranteMesaBean.getVreferencia());
		System.out.println(entranteMesaBean.getVreferencia());
		inputs.put(VOBSERVACION, entranteMesaBean.getVobservacion());
		System.out.println(entranteMesaBean.getVobservacion());		
		inputs.put(VPRIORIDAD, entranteMesaBean.getVprioridad());
		System.out.println(entranteMesaBean.getVprioridad());
		inputs.put(VRESACT, entranteMesaBean.getVresact());	
		System.out.println(entranteMesaBean.getVresact());
		inputs.put(VUBIARCHIVO, entranteMesaBean.getVubiarchivo());	
		System.out.println(entranteMesaBean.getVubiarchivo());
		inputs.put(DFECDOC, entranteMesaBean.getDfecdoc());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");	
		System.out.println(entranteMesaBean.getDfecdoc());
		inputs.put(NDIASPLAZO, String.valueOf(entranteMesaBean.getNdiasplazo()));
		System.out.println(String.valueOf(entranteMesaBean.getNdiasplazo()));
		inputs.put(VESTADO, entranteMesaBean.getVestado());
		System.out.println(entranteMesaBean.getVestado());
		//
		inputs.put(NDIRIGIDO, String.valueOf(entranteMesaBean.getFicha_dirigido()));
		System.out.println(entranteMesaBean.getFicha_dirigido());
		inputs.put(NREMITENTE, String.valueOf(entranteMesaBean.getNremitente()));
		System.out.println(entranteMesaBean.getNremitente());
		inputs.put(VREMITENTE, entranteMesaBean.getVdirigido());
		System.out.println(entranteMesaBean.getVdirigido());		
		inputs.put(NCODAREA, String.valueOf(entranteMesaBean.getNcodarea()));
		System.out.println(entranteMesaBean.getNcodarea());
		inputs.put(DFECREGISTRO, entranteMesaBean.getDfecregistro());
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yy");
		System.out.println(entranteMesaBean.getDfecregistro());
		inputs.put(VFOLIO, String.valueOf(entranteMesaBean.getNfolio()));		
		System.out.println(String.valueOf(entranteMesaBean.getNfolio()));
		return super.execute(inputs);
	}

	

}
