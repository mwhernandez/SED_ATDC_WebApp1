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

public class StoredActualizarEnt extends StoredProcedure{
	//private static final String SPROC_NAME = "S_GUIA.ATDC_UPDATE_DOC_ENTR";	
	private static final String SPROC_NAME = "ATDC_PKG_DOCUMENTO_ENTRANTE.ATDC_UPDATE_DOC_ENTR";
	private static final String NANO = "v_ano";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCORRELATIVO = "v_correlativo";
	private static final String VNUMDOC = "v_numdoc";	
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
	private static final String VMESAPARTE = "v_mesa";
	private static final String VREMITENTE = "v_remite";
	private static final String NDIRIGIDO = "v_codarea";
	private static final String DFECREGISTRO = "d_fecharegistro";
	private static final String VTIPO = "v_tipo";
	private static final String NQUITARAREAS = "v_quitarareas";	
	private static final String NFOLIO = "n_folio";
	public StoredActualizarEnt() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarEnt(DataSource dataSource) {
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
		declareParameter(new SqlParameter(VACCION, Types.VARCHAR));
		declareParameter(new SqlParameter(VPRIORIDAD, Types.VARCHAR));	
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		declareParameter(new SqlParameter(VUBIARCHIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECDOC, Types.DATE));
		declareParameter(new SqlParameter(NDIASPLAZO, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(VMESAPARTE, Types.VARCHAR));
		declareParameter(new SqlParameter(VREMITENTE, Types.VARCHAR));
		declareParameter(new SqlParameter(NDIRIGIDO, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECREGISTRO, Types.DATE));
		declareParameter(new SqlParameter(VTIPO, Types.VARCHAR));
		declareParameter(new SqlParameter(NQUITARAREAS, Types.VARCHAR));
		declareParameter(new SqlParameter(NFOLIO, Types.VARCHAR));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(EntranteBean entranteBean) {
		Map inputs = new HashMap();
		System.out.println("Datos de update de documentos de entrada");
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
		//inputs.put(VMESAPARTE, entranteBean.getVdirigido());
		//System.out.println(entranteBean.getVdirigido());	
		inputs.put(VMESAPARTE, entranteBean.getVdirigido());
		System.out.println(entranteBean.getVdirigido());
		inputs.put(VREMITENTE, String.valueOf(entranteBean.getVremitente()));
		System.out.println(String.valueOf(entranteBean.getVremitente()));
		inputs.put(NDIRIGIDO, String.valueOf(entranteBean.getNdirigido()));
		System.out.println(String.valueOf(entranteBean.getNdirigido()));
		inputs.put(DFECREGISTRO, entranteBean.getDfecregistro());
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yy");
		System.out.println(entranteBean.getDfecregistro());
		inputs.put(VTIPO, String.valueOf(entranteBean.getIndicador()));
		System.out.println(String.valueOf(entranteBean.getIndicador()));
		inputs.put(NQUITARAREAS, entranteBean.getQuitarareas());		
		System.out.println(entranteBean.getQuitarareas());
		inputs.put(NFOLIO, String.valueOf(entranteBean.getNfolio()));
		System.out.println(String.valueOf(entranteBean.getNfolio()));
		//System.out.println(entranteBean.getNcorrelativo());	
		//System.out.println(entranteBean.getDfecdoc());
		//System.out.println(entranteBean.getDfecregistro());
		//System.out.println("Fin de Execute SP actualizacion");
		return super.execute(inputs);
	}

}
