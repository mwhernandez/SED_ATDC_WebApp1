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


public class StoredNuevoDocumentoEntrada extends StoredProcedure{
	//private static final String SPROC_NAME = "S_GUIA.ATDC_NUEVO_DOCUMENTO_ENTRADA";	
	private static final String SPROC_NAME = "ATDC_PKG_DOCUMENTO_ENTRANTE.ATDC_NUEVO_DOCUMENTO_ENTRADA";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";	
	private static final String NCODAREA = "v_codarea";	
	private static final String NCODAREA_ORIGEN = "v_codareaorigen";
	private static final String NREMITENTE = "n_remitente";
	private static final String VUBIARCHIVO = "v_ubicacion_archivo";
	private static final String NDIRIGIDO = "v_dirigido";
	private static final String NFICHA_DIRIGIDO = "v_ficha";	
	private static final String VNUMDOC = "v_numdoc";
	private static final String VASUNTO = "v_asunto";
	private static final String VREFERENCIA = "v_referencia";
	private static final String VOBSERVACION = "v_observacion";
	private static final String VACCION = "v_accion";
	private static final String VPRIORIDAD = "v_prioridad";
	private static final String VESTADO = "v_estado";	
	private static final String DFECDOC = "d_fechadoc";	
	private static final String VRESCRE = "v_responsable";
	private static final String VRESACT = "v_resactual";
	//private static final String NDERIVADO = "v_ficha_derivado";	
	//private static final String DFECPLAZO = "d_fechaplazo";
	private static final String DFECREGISTRO = "d_fecharegistro";
	private static final String NDIASPLAZO = "v_dias";
	private static final String VTIPO = "v_tipo";
	private static final String NFOLIO = "n_folio";
	
	
		
	public StoredNuevoDocumentoEntrada(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));		
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODAREA_ORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(NREMITENTE, Types.VARCHAR));
		declareParameter(new SqlParameter(VUBIARCHIVO, Types.VARCHAR));		
		declareParameter(new SqlParameter(NDIRIGIDO, Types.VARCHAR));
		declareParameter(new SqlParameter(NFICHA_DIRIGIDO, Types.VARCHAR));
		declareParameter(new SqlParameter(VNUMDOC, Types.VARCHAR));		
		declareParameter(new SqlParameter(VASUNTO, Types.VARCHAR));
		declareParameter(new SqlParameter(VREFERENCIA, Types.VARCHAR));	
		declareParameter(new SqlParameter(VOBSERVACION, Types.VARCHAR));
		declareParameter(new SqlParameter(VACCION, Types.VARCHAR));		
		declareParameter(new SqlParameter(VPRIORIDAD, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));		
		declareParameter(new SqlParameter(DFECDOC, Types.DATE));		
		declareParameter(new SqlParameter(VRESCRE, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		//declareParameter(new SqlParameter(NDERIVADO, Types.VARCHAR));
		//declareParameter(new SqlParameter(DFECPLAZO, Types.DATE));
		declareParameter(new SqlParameter(DFECREGISTRO, Types.DATE));
		declareParameter(new SqlParameter(NDIASPLAZO, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPO, Types.VARCHAR));
		declareParameter(new SqlParameter(NFOLIO, Types.VARCHAR));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(EntranteBean entranteBean) {
		Map inputs = new HashMap();
		///verificar las asignaciones de los campos
		System.out.println("Verificado los datos");	
		inputs.put(NORIGEN, String.valueOf(entranteBean.getOrigen()));	
		System.out.println(entranteBean.getOrigen());		
		inputs.put(VTIPODOC, String.valueOf(entranteBean.getVtipodoc()));
		System.out.println(entranteBean.getVtipodoc());
		inputs.put(NCODAREA, String.valueOf(entranteBean.getNcodarea()));
		System.out.println(entranteBean.getNcodarea());
		inputs.put(NCODAREA_ORIGEN, String.valueOf(entranteBean.getNcodarea_origen()));
		System.out.println(entranteBean.getNcodarea_origen());
		//inputs.put(NREMITENTE, entranteBean.getVremitente());
		//System.out.println(entranteBean.getVremitente());
		inputs.put(NREMITENTE, entranteBean.getAreas());
		System.out.println(entranteBean.getAreas());
		inputs.put(VUBIARCHIVO, String.valueOf(entranteBean.getVubiarchivo()));
		System.out.println(entranteBean.getVubiarchivo());
		inputs.put(NDIRIGIDO, String.valueOf(entranteBean.getNdirigido()));
		System.out.println(entranteBean.getNdirigido());
		inputs.put(NFICHA_DIRIGIDO, String.valueOf(entranteBean.getFicha_dirigido()));
		System.out.println(entranteBean.getFicha_dirigido());
		inputs.put(VNUMDOC, String.valueOf(entranteBean.getVnumdoc()));
		System.out.println(entranteBean.getVnumdoc());
		inputs.put(VASUNTO, entranteBean.getVasunto());
		System.out.println(entranteBean.getVasunto());
		inputs.put(VREFERENCIA, entranteBean.getVreferencia());
		System.out.println(entranteBean.getVreferencia());
		inputs.put(VOBSERVACION, entranteBean.getVobservacion());
		System.out.println(entranteBean.getVobservacion());
		inputs.put(VACCION, String.valueOf(entranteBean.getVaccion()));
		System.out.println(entranteBean.getVaccion());
		inputs.put(VPRIORIDAD, String.valueOf(entranteBean.getVprioridad()));
		System.out.println(entranteBean.getVprioridad());
		inputs.put(VESTADO, String.valueOf(entranteBean.getVestado()));
		System.out.println(entranteBean.getVestado());
		inputs.put(DFECDOC, entranteBean.getDfecdoc());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");	
		System.out.println(entranteBean.getDfecdoc());
		inputs.put(VRESCRE, entranteBean.getVrescre());	
		System.out.println(entranteBean.getVrescre());		
		inputs.put(VRESACT, entranteBean.getVresact());
		System.out.println(entranteBean.getVresact());
		//inputs.put(NDERIVADO, String.valueOf(entranteBean.getFicha_derivado()));
		//System.out.println(entranteBean.getFicha_derivado());	
		//inputs.put(DFECPLAZO, entranteBean.getDfecplazo());
		//SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yy");
		//System.out.println(entranteBean.getDfecplazo());
		inputs.put(DFECREGISTRO, entranteBean.getDfecregistro());		
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println(entranteBean.getDfecregistro());
		inputs.put(NDIASPLAZO, String.valueOf(entranteBean.getNdiasplazo()));
		System.out.println(String.valueOf(entranteBean.getNdiasplazo()));
		inputs.put(VTIPO, String.valueOf(entranteBean.getIndicador()));
		System.out.println(String.valueOf(entranteBean.getIndicador()));
		inputs.put(NFOLIO, String.valueOf(entranteBean.getNfolio()));
		System.out.println(String.valueOf(entranteBean.getNfolio()));
		return super.execute(inputs);
	}

}
