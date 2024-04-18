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

import com.sedapal.tramite.beans.DocumentoSalidaBean;


public class StoredNuevoDocumentoSaliente extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_PKG_DOCUMENTO_SALIENTE.ATDC_NUEVO_DOCUMENTO_SALIENTE_BPM_V2";	
	//private static final String SPROC_NAME = "ATDC_NUEVO_DOCUMENTO_SALIENTE";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCODAREA = "v_codarea";
	private static final String NDIRIGIDO = "v_ndirigido";
	private static final String VUBIARCHIVO = "v_ubiarchivo";
	private static final String DFECEMISION = "d_fecha";	
	private static final String VASUNTO = "v_asunto";
	private static final String VNUMDOC = "v_numdoc";
	private static final String VREF_NUMDOC = "v_referencia";
	private static final String VOBSERVACION = "v_observacion";
	private static final String VPRIORIDAD = "v_prioridad";
	private static final String VESTADO = "v_estado";	
	private static final String VRESCRE = "v_responsable";
	private static final String VRESACT = "v_resactual";
	private static final String NFICHA_DIRIGIDO = "v_ficha";
	private static final String VNUM_DOC_ENTR = "v_numrodoc_entrante";
	private static final String NREMITENTE = "v_remitente";	
	private static final String VACCION = "v_accion";
	private static final String NDIASPLAZO = "v_dias";
	private static final String VTIPO = "v_tipo";
	private static final String VFICHAS_DIRIGIDOS = "v_fichadirigidos";
	private static final String VANOENTRADA = "v_ano_entrada";
	private static final String VNOTIFICADOR = "v_notificador";	
	private static final String VFICHA_USUARIO = "v_ficha_usuario";
	private static final String V_ANEXO = "v_anexo";
	
	public StoredNuevoDocumentoSaliente(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlParameter(NDIRIGIDO, Types.VARCHAR));
		declareParameter(new SqlParameter(VUBIARCHIVO, Types.VARCHAR));		
		declareParameter(new SqlParameter(DFECEMISION, Types.VARCHAR));
		declareParameter(new SqlParameter(VASUNTO, Types.VARCHAR));
		declareParameter(new SqlParameter(VNUMDOC, Types.VARCHAR));
		declareParameter(new SqlParameter(VREF_NUMDOC, Types.VARCHAR));
		declareParameter(new SqlParameter(VOBSERVACION, Types.VARCHAR));		
		declareParameter(new SqlParameter(VPRIORIDAD, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));		
		declareParameter(new SqlParameter(VRESCRE, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));	
		declareParameter(new SqlParameter(NFICHA_DIRIGIDO, Types.VARCHAR));
		declareParameter(new SqlParameter(VNUM_DOC_ENTR, Types.VARCHAR));
		declareParameter(new SqlParameter(NREMITENTE, Types.VARCHAR));	
		declareParameter(new SqlParameter(VACCION, Types.VARCHAR));
		declareParameter(new SqlParameter(NDIASPLAZO, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPO, Types.VARCHAR));
		declareParameter(new SqlParameter(VFICHAS_DIRIGIDOS, Types.VARCHAR));
		declareParameter(new SqlParameter(VANOENTRADA, Types.VARCHAR));
		declareParameter(new SqlParameter(VNOTIFICADOR, Types.VARCHAR));
		declareParameter(new SqlParameter(VFICHA_USUARIO, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(V_ANEXO, OracleTypes.NUMBER));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		declareParameter(new SqlOutParameter("outcorrelativo",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(DocumentoSalidaBean documentoSalidaBean) {
		Map inputs = new HashMap();
		System.out.println("Manda los parametros para nuevo salida 30.10.2023");
		inputs.put(NORIGEN, documentoSalidaBean.getOrigenes());
		System.out.println(documentoSalidaBean.getOrigenes());
		inputs.put(VTIPODOC, documentoSalidaBean.getTipodoc());
		System.out.println(documentoSalidaBean.getTipodoc());
		inputs.put(NCODAREA, String.valueOf(documentoSalidaBean.getArea()));
		System.out.println(documentoSalidaBean.getArea());
		//inputs.put(NDIRIGIDO,(documentoSalidaBean.getDirigido()));
		//System.out.println(documentoSalidaBean.getDirigido());
		inputs.put(NDIRIGIDO,(documentoSalidaBean.getAreas()));
		System.out.println(documentoSalidaBean.getAreas());
		inputs.put(VUBIARCHIVO, documentoSalidaBean.getUbiarchivo());
		System.out.println(documentoSalidaBean.getUbiarchivo());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		inputs.put(DFECEMISION, sdf.format(documentoSalidaBean.getFecha()));
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		System.out.println(sdf.format(documentoSalidaBean.getFecha()));	
		inputs.put(VASUNTO, documentoSalidaBean.getAsunto());
		System.out.println(documentoSalidaBean.getAsunto());
		inputs.put(VNUMDOC, String.valueOf(documentoSalidaBean.getNumerodoc()));
		System.out.println(documentoSalidaBean.getNumerodoc());
		inputs.put(VREF_NUMDOC, documentoSalidaBean.getReferencia());
		System.out.println(documentoSalidaBean.getReferencia());
		inputs.put(VOBSERVACION, documentoSalidaBean.getObservacion());
		System.out.println(documentoSalidaBean.getObservacion());
		inputs.put(VPRIORIDAD, documentoSalidaBean.getPrioridad());
		System.out.println(documentoSalidaBean.getPrioridad());
		//inputs.put(VESTADO, documentoSalidaBean.getEstado());
		//System.out.println(documentoSalidaBean.getEstado());
		///
		inputs.put(VESTADO, documentoSalidaBean.getNestado());
		System.out.println(documentoSalidaBean.getNestado());
		//
		inputs.put(VRESCRE, documentoSalidaBean.getVrescre());
		System.out.println(documentoSalidaBean.getVrescre());
		inputs.put(VRESACT, documentoSalidaBean.getVresact());
		System.out.println(documentoSalidaBean.getVresact());
		inputs.put(NFICHA_DIRIGIDO, String.valueOf(documentoSalidaBean.getFicha_dirigido()));
		System.out.println(documentoSalidaBean.getFicha_dirigido());
		inputs.put(VNUM_DOC_ENTR, documentoSalidaBean.getDoc_entrada());
		System.out.println(documentoSalidaBean.getDoc_entrada());	
		inputs.put(NREMITENTE, documentoSalidaBean.getRemitente());
		System.out.println(documentoSalidaBean.getRemitente());
		inputs.put(VACCION, documentoSalidaBean.getVaccion());
		System.out.println(documentoSalidaBean.getVaccion());
		inputs.put(NDIASPLAZO, documentoSalidaBean.getDias());
		System.out.println(documentoSalidaBean.getDias());
		inputs.put(VTIPO, String.valueOf(documentoSalidaBean.getIndicadores()));
		System.out.println(String.valueOf(documentoSalidaBean.getIndicadores()));
		inputs.put(VFICHAS_DIRIGIDOS, documentoSalidaBean.getCodigos());
		System.out.println(documentoSalidaBean.getCodigos());
		inputs.put(VANOENTRADA, documentoSalidaBean.getVanoentrada());		
		System.out.println(documentoSalidaBean.getVanoentrada());
		inputs.put(VNOTIFICADOR, documentoSalidaBean.getVnotificador());		
		System.out.println(documentoSalidaBean.getVnotificador());
		inputs.put(VFICHA_USUARIO, documentoSalidaBean.getFichaVisador());		
		System.out.println(documentoSalidaBean.getFichaVisador());
		inputs.put(V_ANEXO, documentoSalidaBean.getAnexo());		
		System.out.println(documentoSalidaBean.getAnexo());
		System.out.println("fin de los parametros para nuevo salida");
		Map resultMap = new HashMap();
		try{
			resultMap = super.execute(inputs);
			resultMap.put("msgOper", "Se realizo la accion sin problemas");
		}catch (Exception e) {
			// TODO: handle exception
			resultMap.put("msgOper", e.toString());
			System.out.println("Resultado de la Operacion del SP :" + e.toString());
		}
		return resultMap;
	}

}
