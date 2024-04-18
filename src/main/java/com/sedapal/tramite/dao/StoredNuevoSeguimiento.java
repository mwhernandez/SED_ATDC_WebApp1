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

import com.icesoft.faces.component.ext.OutputBody;
import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;



public class StoredNuevoSeguimiento extends StoredProcedure{
	//private static final String SPROC_NAME = "S_GUIA.ATDC_NUEVO_SEGUIMIENTO";
	private static final String SPROC_NAME = "ATDC_PKG_SEGUIMIENTO.ATDC_NUEVO_SEGUIMIENTO";	
	private static final String NANO = "v_ano";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCORRELATIVO = "v_correlativo";	
	private static final String DFECDERIVACION = "d_fecderivacion";	
	private static final String VOBSERVACION = "v_comentario";
	private static final String DFECPLAZO = "d_fechaplazo ";
	private static final String VRESACT = "v_responsable";	
	private static final String VESTADO = "v_estado";	
	private static final String NAREAREMITENTE = "v_arearemitente";
	private static final String NFICHAREMITENTE = "v_ficharemitente";
	private static final String VUBIARCHIVO = "v_ubicacion_archivo";
	private static final String VACCION = "v_accion";
	//nuevos parametros
	private static final String VAREAS = "v_areas";
	private static final String VFICHAS = "v_fichas";
	private static final String VPLAZO = "v_plazo";
	private static final String VPRIORIDAD = "v_prioridad";
	private static final String NINDICADOR = "n_indicador";
	//private static final String NDERIVADO = "v_derivado";
	//private static final String NAREADERIVADO = "v_areaderivado";
	
	
					
	public StoredNuevoSeguimiento() {
		// TODO Auto-generated constructor stub
	}

	public StoredNuevoSeguimiento(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECDERIVACION, Types.DATE));
		declareParameter(new SqlParameter(VOBSERVACION, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECPLAZO, Types.DATE));		
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));		
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(NAREAREMITENTE, Types.VARCHAR));
		declareParameter(new SqlParameter(NFICHAREMITENTE, Types.VARCHAR));
		declareParameter(new SqlParameter(VUBIARCHIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(VACCION, Types.VARCHAR));
		//nuevos parametros
		declareParameter(new SqlParameter(VAREAS, Types.VARCHAR));
		declareParameter(new SqlParameter(VFICHAS, Types.VARCHAR));	
		declareParameter(new SqlParameter(VPLAZO, Types.VARCHAR));
		declareParameter(new SqlParameter(VPRIORIDAD, Types.VARCHAR));
		declareParameter(new SqlParameter(NINDICADOR, Types.VARCHAR));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(SeguimientoEntranteBean seguimientoentranteBean) {
		Map inputs = new HashMap();
		System.out.println("Paso los parametros - INICIO NUEVO SEGUIMIENTO");
		inputs.put(NANO, String.valueOf(seguimientoentranteBean.getAno()));
		System.out.println(seguimientoentranteBean.getAno());	
		inputs.put(NORIGEN, String.valueOf(seguimientoentranteBean.getNorigen()));
		System.out.println(seguimientoentranteBean.getNorigen());
		inputs.put(VTIPODOC, String.valueOf(seguimientoentranteBean.getTipodoc()));
		System.out.println(seguimientoentranteBean.getTipodoc());
		inputs.put(NCORRELATIVO, String.valueOf(seguimientoentranteBean.getCorrelativo()));
		System.out.println(seguimientoentranteBean.getCorrelativo());	
		inputs.put(DFECDERIVACION, seguimientoentranteBean.getFecderivado());		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		System.out.println(seguimientoentranteBean.getFecderivado());		
		inputs.put(VOBSERVACION, String.valueOf(seguimientoentranteBean.getComentario()));
		System.out.println(seguimientoentranteBean.getComentario());
		inputs.put(DFECPLAZO, seguimientoentranteBean.getFecplazo());
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yy");
		System.out.println(seguimientoentranteBean.getFecplazo());
		inputs.put(VRESACT, String.valueOf(seguimientoentranteBean.getResact()));	
		System.out.println(seguimientoentranteBean.getResact());		
		inputs.put(VESTADO, String.valueOf(seguimientoentranteBean.getVestado()));	
		System.out.println(seguimientoentranteBean.getVestado());	
		inputs.put(NAREAREMITENTE, String.valueOf(seguimientoentranteBean.getArearemitente()));
		System.out.println(seguimientoentranteBean.getArearemitente());
		inputs.put(NFICHAREMITENTE, String.valueOf(seguimientoentranteBean.getFicharemitente()));
		System.out.println(seguimientoentranteBean.getFicharemitente());
		inputs.put(VUBIARCHIVO, String.valueOf(seguimientoentranteBean.getUbicaarchivo()));
		System.out.println(seguimientoentranteBean.getUbicaarchivo());
		inputs.put(VACCION, String.valueOf(seguimientoentranteBean.getAccion()));
		System.out.println(seguimientoentranteBean.getAccion());
		inputs.put(VAREAS, seguimientoentranteBean.getAreas());
		System.out.println(seguimientoentranteBean.getAreas());
		inputs.put(VFICHAS, seguimientoentranteBean.getFichas());
		System.out.println(seguimientoentranteBean.getFichas());
		inputs.put(VPLAZO, seguimientoentranteBean.getNplazo());
		System.out.println(seguimientoentranteBean.getNplazo());
		inputs.put(VPRIORIDAD, seguimientoentranteBean.getVprioridad());
		System.out.println(seguimientoentranteBean.getVprioridad());
		inputs.put(NINDICADOR, String.valueOf(seguimientoentranteBean.getIndicador()));
		System.out.println(seguimientoentranteBean.getIndicador());
		System.out.println("FIN NUEVO SEGUIMIENTO");
		return super.execute(inputs);
	}

	

}
