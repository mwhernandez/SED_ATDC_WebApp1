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

import com.sedapal.tramite.beans.EntranteMesaBean;


public class StoredNuevoDocumentoEntradaMesaVirtual extends StoredProcedure{
	
	private static final String SPROC_NAME = "ATDC_NUEVO_DOC_ENTR_MESA_VIRTUAL";		
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
	private static final String VPRIORIDAD = "v_prioridad";
	private static final String VESTADO = "v_estado";	
	private static final String DFECDOC = "d_fechadoc";	
	private static final String VRESCRE = "v_responsable";
	private static final String VRESACT = "v_resactual";	
	private static final String DFECREGISTRO = "d_fecharegistro";
	private static final String NDIASPLAZO = "v_dias";
	
	
	
	
		
	public StoredNuevoDocumentoEntradaMesaVirtual(DataSource dataSource) {
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
		declareParameter(new SqlParameter(VPRIORIDAD, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));		
		declareParameter(new SqlParameter(DFECDOC, Types.DATE));		
		declareParameter(new SqlParameter(VRESCRE, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));		
		declareParameter(new SqlParameter(DFECREGISTRO, Types.DATE));
		declareParameter(new SqlParameter(NDIASPLAZO, Types.VARCHAR));		
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));	
		declareParameter(new SqlOutParameter("outcorrelativo", OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(EntranteMesaBean entrantemesaBean) {
		Map inputs = new HashMap();
		///verificar las asignaciones de los campos
		System.out.println("iNICIO  DEL SP INGRESO DE DOC MESA DE PARTES");	
		inputs.put(NORIGEN, String.valueOf(entrantemesaBean.getOrigen()));	
		System.out.println(entrantemesaBean.getOrigen());		
		inputs.put(VTIPODOC, String.valueOf(entrantemesaBean.getVtipodoc()));
		System.out.println(entrantemesaBean.getVtipodoc());
		inputs.put(NCODAREA, entrantemesaBean.getNcodarea());
		System.out.println(entrantemesaBean.getNcodarea());
		inputs.put(NCODAREA_ORIGEN, String.valueOf(entrantemesaBean.getNcodarea_origen()));
		System.out.println(entrantemesaBean.getNcodarea_origen());		
		//inputs.put(NREMITENTE, String.valueOf(entranteBean.getNremitente()));
		//System.out.println(entranteBean.getNremitente());
		inputs.put(NREMITENTE,entrantemesaBean.getVremitente());
		System.out.println(entrantemesaBean.getVremitente());
		inputs.put(VUBIARCHIVO, String.valueOf(entrantemesaBean.getVubiarchivo()));
		System.out.println(entrantemesaBean.getVubiarchivo());
		inputs.put(NDIRIGIDO, String.valueOf(entrantemesaBean.getNdirigido()));
		System.out.println(entrantemesaBean.getNdirigido());
		inputs.put(NFICHA_DIRIGIDO, String.valueOf(entrantemesaBean.getFicha_dirigido()));
		System.out.println(entrantemesaBean.getFicha_dirigido());
		inputs.put(VNUMDOC, String.valueOf(entrantemesaBean.getVnumdoc()));
		System.out.println(entrantemesaBean.getVnumdoc());
		inputs.put(VASUNTO, entrantemesaBean.getVasunto());
		System.out.println(entrantemesaBean.getVasunto());
		inputs.put(VREFERENCIA, entrantemesaBean.getVreferencia());
		System.out.println(entrantemesaBean.getVreferencia());
		inputs.put(VOBSERVACION, entrantemesaBean.getVobservacion());
		System.out.println(entrantemesaBean.getVobservacion());
		//inputs.put(VACCION, String.valueOf(entrantemesaBean.getVaccion()));
		//System.out.println(entrantemesaBean.getVaccion());
		inputs.put(VPRIORIDAD, String.valueOf(entrantemesaBean.getVprioridad()));
		System.out.println(entrantemesaBean.getVprioridad());
		inputs.put(VESTADO, String.valueOf(entrantemesaBean.getVestado()));
		System.out.println(entrantemesaBean.getVestado());
		inputs.put(DFECDOC, entrantemesaBean.getDfecdoc());		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println(entrantemesaBean.getDfecdoc());
		inputs.put(VRESCRE, entrantemesaBean.getVrescre());	
		System.out.println(entrantemesaBean.getVrescre());		
		inputs.put(VRESACT, entrantemesaBean.getVresact());
		System.out.println(entrantemesaBean.getVresact());	
		inputs.put(DFECREGISTRO, entrantemesaBean.getDfecregistro());		
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println(entrantemesaBean.getDfecregistro());
		inputs.put(NDIASPLAZO, String.valueOf(entrantemesaBean.getNdiasplazo()));
		System.out.println(String.valueOf(entrantemesaBean.getNdiasplazo()));		
		System.out.println("FIN DEL SP INGRESO DE DOC MESA DE PARTES");
		return super.execute(inputs);
	}

}
