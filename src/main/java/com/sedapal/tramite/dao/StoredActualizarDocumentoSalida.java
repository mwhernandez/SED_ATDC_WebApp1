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



public class StoredActualizarDocumentoSalida extends StoredProcedure{	
	private static final String SPROC_NAME = "ATDC_PKG_DOCUMENTO_SALIENTE.ATDC_UPDATE_DOCUMENTO_SALIENTE_V2";
	private static final String NANO = "v_ano";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCODAREA = "v_codarea";
	private static final String NCODSAL = "v_codigo";
	private static final String VASUNTO = "v_asunto";
	private static final String VNUMDOC = "v_numdoc";
	private static final String VREF_NUMDOC = "v_referencia";
	private static final String VOBSERVACION = "v_observacion";
	private static final String VPRIORIDAD = "v_prioridad";
	private static final String VESTADO = "v_estado";		
	private static final String VRESACT = "v_resactual";
	private static final String VNUM_DOC_ENTR = "v_numrodoc_entrante";	
	private static final String DFECEMISION = "d_fecha";	
	private static final String NDIRIGIDO = "v_ndirigido";
	private static final String VDIRIDO = "v_vdirigidos";	
	private static final String NFICHA_DIRIGIDO = "v_ficha";
	private static final String VUBIARCHIVO = "v_ubiarchivo";
	private static final String NREMITENTE = "v_remitente";
	private static final String NDIASPLAZO = "v_dias";
	private static final String NQUITARAREAS = "v_quitarareas";
	private static final String VTIPO = "v_tipo";
	private static final String VOPCION = "v_opcion";
	private static final String VFICHAS_DIRIGIDOS = "v_fichadirigidos";
	private static final String NQUITARFICHAS = "v_quitarfichas";
	private static final String VANOENTRADA = "v_ano_entrada";
	private static final String VNOTIFICADOR = "v_notificador";	
	private static final String VFICHA_VISADOR = "v_ficha_visador";	
	private static final String VANEXO = "v_anexo";	
	private static final String VINDREMITENTE = "v_indremitente";	
	
				
	public StoredActualizarDocumentoSalida() {
		// TODO Auto-generated constructor stub
	}

	public StoredActualizarDocumentoSalida(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODSAL, Types.VARCHAR));
		declareParameter(new SqlParameter(VASUNTO, Types.VARCHAR));
		declareParameter(new SqlParameter(VNUMDOC, Types.VARCHAR));
		declareParameter(new SqlParameter(VREF_NUMDOC, Types.VARCHAR));
		declareParameter(new SqlParameter(VOBSERVACION, Types.VARCHAR));
		declareParameter(new SqlParameter(VPRIORIDAD, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));	
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));	
		declareParameter(new SqlParameter(VNUM_DOC_ENTR, Types.VARCHAR));
		declareParameter(new SqlParameter(DFECEMISION, Types.VARCHAR));
		declareParameter(new SqlParameter(NDIRIGIDO, Types.VARCHAR));
		declareParameter(new SqlParameter(VDIRIDO, Types.VARCHAR));
		declareParameter(new SqlParameter(NFICHA_DIRIGIDO, Types.VARCHAR));
		declareParameter(new SqlParameter(VUBIARCHIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(NREMITENTE, Types.VARCHAR));
		declareParameter(new SqlParameter(NDIASPLAZO, Types.VARCHAR));
		declareParameter(new SqlParameter(NQUITARAREAS, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPO, Types.VARCHAR));
		declareParameter(new SqlParameter(VOPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(VFICHAS_DIRIGIDOS, Types.VARCHAR));
		declareParameter(new SqlParameter(NQUITARFICHAS, Types.VARCHAR));
		declareParameter(new SqlParameter(VANOENTRADA, Types.VARCHAR));
		declareParameter(new SqlParameter(VNOTIFICADOR, Types.VARCHAR));
		declareParameter(new SqlParameter(VFICHA_VISADOR, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(VANEXO, OracleTypes.NUMBER));
		declareParameter(new SqlParameter(VINDREMITENTE, OracleTypes.NUMBER));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		declareParameter(new SqlOutParameter("outcorrelativo",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(DocumentoSalidaBean documentoSalidaBean) {
		Map inputs = new HashMap();
		System.out.println("Datos de los documentos salientes");
		inputs.put(NANO, String.valueOf(documentoSalidaBean.getAno()));
		System.out.println(documentoSalidaBean.getAno());		
		inputs.put(NORIGEN, String.valueOf(documentoSalidaBean.getOrigen()));
		System.out.println(documentoSalidaBean.getOrigen());
		inputs.put(VTIPODOC, documentoSalidaBean.getNtipodoc());
		System.out.println(documentoSalidaBean.getNtipodoc());
		inputs.put(NCODAREA, String.valueOf(documentoSalidaBean.getArea()));
		System.out.println(documentoSalidaBean.getArea());
		inputs.put(NCODSAL, String.valueOf(documentoSalidaBean.getCodigo()));
		System.out.println(documentoSalidaBean.getCodigo());			
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
		inputs.put(VESTADO, documentoSalidaBean.getNestado());
		System.out.println(documentoSalidaBean.getNestado());
		inputs.put(VRESACT, documentoSalidaBean.getVresact());
		System.out.println(documentoSalidaBean.getVresact());
		inputs.put(VNUM_DOC_ENTR, documentoSalidaBean.getDoc_entrada());
		System.out.println(documentoSalidaBean.getDoc_entrada());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		//sdf.format(documentoSalidaBean.getFecha());
		inputs.put(DFECEMISION, sdf.format(documentoSalidaBean.getFecha()));		
		System.out.println(sdf.format(documentoSalidaBean.getFecha()));
		inputs.put(NDIRIGIDO,  String.valueOf(documentoSalidaBean.getNdirigido()));
		System.out.println(documentoSalidaBean.getNdirigido());
		inputs.put(VDIRIDO,  String.valueOf(documentoSalidaBean.getAreaseleccionadas()));
		System.out.println(documentoSalidaBean.getAreaseleccionadas());		
		//System.out.println(documentoSalidaBean.getVdirigidos());
		inputs.put(NFICHA_DIRIGIDO,  String.valueOf(documentoSalidaBean.getFicha_dirigido()));
		System.out.println(documentoSalidaBean.getFicha_dirigido());
		inputs.put(VUBIARCHIVO,  documentoSalidaBean.getUbiarchivo());
		System.out.println(documentoSalidaBean.getUbiarchivo());
		inputs.put(NREMITENTE, String.valueOf(documentoSalidaBean.getFicha_remitente()));
		System.out.println(documentoSalidaBean.getFicha_remitente());
		inputs.put(NDIASPLAZO, String.valueOf(documentoSalidaBean.getDias()));		
		System.out.println(documentoSalidaBean.getDias());
		//
		inputs.put(NQUITARAREAS, documentoSalidaBean.getQuitarareas());		
		System.out.println(documentoSalidaBean.getQuitarareas());
		inputs.put(VTIPO, String.valueOf(documentoSalidaBean.getIndicadores()));
		System.out.println(String.valueOf(documentoSalidaBean.getIndicadores()));
		inputs.put(VOPCION, documentoSalidaBean.getOpcion_seguimiento());
		System.out.println(String.valueOf(documentoSalidaBean.getOpcion_seguimiento()));
		inputs.put(VFICHAS_DIRIGIDOS, documentoSalidaBean.getCodigos());
		System.out.println(documentoSalidaBean.getCodigos());
		inputs.put(NQUITARFICHAS, documentoSalidaBean.getQuitarfichas());		
		System.out.println(documentoSalidaBean.getQuitarfichas());
		inputs.put(VANOENTRADA, documentoSalidaBean.getVanoentrada());		
		System.out.println(documentoSalidaBean.getVanoentrada());
		inputs.put(VNOTIFICADOR, documentoSalidaBean.getVnotificador());		
		System.out.println(documentoSalidaBean.getVnotificador());
		inputs.put(VFICHA_VISADOR, documentoSalidaBean.getFichaVisador());		
		System.out.println(documentoSalidaBean.getFichaVisador());
		inputs.put(VANEXO, documentoSalidaBean.getAnexo());		
		System.out.println(documentoSalidaBean.getAnexo());
		inputs.put(VINDREMITENTE, documentoSalidaBean.getInd_remitente());		
		System.out.println(documentoSalidaBean.getInd_remitente());
		return super.execute(inputs);
	}

	

}
