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
import com.sedapal.tramite.beans.EntranteMesaVirtualBean;

public class StoredActualizarEntMesaVirtual extends StoredProcedure{	
	private static final String SPROC_NAME = "ATDC_NUEVO_DOC_ENTR_MESA_VIRTUAL";
	private static final String NANO = "v_ano";
	private static final String SECUENCIAL = "v_secuencial";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String VNUMDOC = "v_numdoc";	
	private static final String NFICHA_DIRIGIDO = "v_ficha";
	private static final String VASUNTO = "v_asunto";
	private static final String VREFERENCIA = "v_referencia";
	private static final String VOBSERVACION = "v_observacion";
	private static final String VPRIORIDAD = "v_prioridad";
	private static final String VRESACT = "v_responsable";
	private static final String VUBIARCHIVO = "v_ubicacion_archivo";	
	private static final String DFECDOC = "d_fechadoc";	
	private static final String NDIASPLAZO = "v_dias";
	private static final String VESTADO = "v_estado";
	private static final String NDIRIGIDO = "v_dirigido";	
	private static final String NREMITENTE = "v_remitente";		
	private static final String VREMITENTE = "v_areasremitente";
	private static final String NCODAREA = "v_codarea";
	private static final String DFECREGISTRO = "d_fecharegistro";
	private static final String NFOLIO = "v_folio";
	private static final String VTIPOINGRESO = "v_tipoingreso";
	private static final String VTIPOMOTIVO = "v_tipomotivo";
	
		
	
	public StoredActualizarEntMesaVirtual() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarEntMesaVirtual(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(SECUENCIAL, Types.VARCHAR));
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));		
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
		declareParameter(new SqlParameter(NFOLIO, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPOINGRESO, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPOMOTIVO, Types.VARCHAR));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		declareParameter(new SqlOutParameter("outcorrelativo",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(EntranteMesaVirtualBean entrantemesavirtualBean) {
		Map inputs = new HashMap();	
		System.out.println("inicio de update de mesa partes virtual");
		inputs.put(NANO, String.valueOf(entrantemesavirtualBean.getNano()));
		System.out.println(entrantemesavirtualBean.getNano());
		inputs.put(SECUENCIAL, String.valueOf(entrantemesavirtualBean.getNcorrelativo()));
		System.out.println(entrantemesavirtualBean.getNcorrelativo());
		inputs.put(NORIGEN, String.valueOf(entrantemesavirtualBean.getNorigen()));
		System.out.println(entrantemesavirtualBean.getNorigen());
		inputs.put(VTIPODOC, entrantemesavirtualBean.getVtipodoc());
		System.out.println(entrantemesavirtualBean.getVtipodoc());
		inputs.put(VNUMDOC, entrantemesavirtualBean.getVnumdoc());		
		System.out.println(entrantemesavirtualBean.getVnumdoc());		
		inputs.put(NFICHA_DIRIGIDO, String.valueOf(entrantemesavirtualBean.getFicha_dirigido()));
		System.out.println(entrantemesavirtualBean.getFicha_dirigido());
		inputs.put(VASUNTO, entrantemesavirtualBean.getVasunto());
		System.out.println(entrantemesavirtualBean.getVasunto());
		inputs.put(VREFERENCIA, entrantemesavirtualBean.getVreferencia());
		System.out.println(entrantemesavirtualBean.getVreferencia());
		inputs.put(VOBSERVACION, entrantemesavirtualBean.getVobservacion());
		System.out.println(entrantemesavirtualBean.getVobservacion());		
		inputs.put(VPRIORIDAD, entrantemesavirtualBean.getVprioridad());
		System.out.println(entrantemesavirtualBean.getVprioridad());
		inputs.put(VRESACT, entrantemesavirtualBean.getVresact());	
		System.out.println(entrantemesavirtualBean.getVresact());
		inputs.put(VUBIARCHIVO, entrantemesavirtualBean.getVubiarchivo());	
		System.out.println(entrantemesavirtualBean.getVubiarchivo());
		inputs.put(DFECDOC, entrantemesavirtualBean.getDfecdoc());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");	
		System.out.println(entrantemesavirtualBean.getDfecdoc());
		inputs.put(NDIASPLAZO, String.valueOf(entrantemesavirtualBean.getNdiasplazo()));
		System.out.println(String.valueOf(entrantemesavirtualBean.getNdiasplazo()));
		inputs.put(VESTADO, entrantemesavirtualBean.getVestado());
		System.out.println(entrantemesavirtualBean.getVestado());
		inputs.put(NDIRIGIDO, String.valueOf(entrantemesavirtualBean.getFicha_dirigido()));
		System.out.println(entrantemesavirtualBean.getFicha_dirigido());
		inputs.put(NREMITENTE, String.valueOf(entrantemesavirtualBean.getNremitente()));
		System.out.println(entrantemesavirtualBean.getNremitente());
		inputs.put(VREMITENTE, entrantemesavirtualBean.getVdirigido());
		System.out.println(entrantemesavirtualBean.getVdirigido());		
		inputs.put(NCODAREA, String.valueOf(entrantemesavirtualBean.getNcodarea()));
		System.out.println(entrantemesavirtualBean.getNcodarea());
		inputs.put(DFECREGISTRO, entrantemesavirtualBean.getDfecregistro());
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yy");
		System.out.println(entrantemesavirtualBean.getDfecregistro());
		inputs.put(NFOLIO, String.valueOf(entrantemesavirtualBean.getNfolio()));
		System.out.println(entrantemesavirtualBean.getNfolio());
		inputs.put(VTIPOINGRESO, String.valueOf(entrantemesavirtualBean.getVtipoingreso()));
		System.out.println(entrantemesavirtualBean.getVtipoingreso());
		inputs.put(VTIPOMOTIVO, String.valueOf(entrantemesavirtualBean.getVmotivoingreso()));
		System.out.println(entrantemesavirtualBean.getVmotivoingreso());
		System.out.println("fin de update de mesa partes virtual");
		return super.execute(inputs);
	}

	

}
