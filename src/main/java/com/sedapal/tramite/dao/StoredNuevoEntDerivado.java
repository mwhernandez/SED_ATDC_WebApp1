package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleSql;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.EntranteBean;

public class StoredNuevoEntDerivado extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_DOC_ENT_INSERTAR";
		private static final String NORIGEN = "v_origen";
		private static final String VTIPODOC = "v_tipodoc";
		private static final String NCODAREA = "v_ncodarea";
		private static final String NCODAREA_ORIGEN = "v_codareaorigen";
		private static final String VREMITENTE = "v_remitente";
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
		private static final String DFECPLAZO = "d_fechaplazo";	
		private static final String VRESCRE = "v_responsable";
		private static final String VRESACT = "v_resactual";
		//private static final String NDIASPLAZO = "v_ndiasplazo";			
		//private static final String NINDICADOR = "v_nindicador";
		//private static final String NCODCENTRO = "v_ncodcentro";
	
	public StoredNuevoEntDerivado() {
		// TODO Auto-generated constructor stub
	}

	public StoredNuevoEntDerivado(DataSource dataSource) {
		super(dataSource, SPROC_NAME);	
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));		
		declareParameter(new SqlParameter(NCODAREA_ORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(VREMITENTE, Types.VARCHAR));
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
		declareParameter(new SqlParameter(DFECPLAZO, Types.DATE));
		declareParameter(new SqlParameter(VRESCRE, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESACT, Types.VARCHAR));
		//declareParameter(new SqlParameter(NDIASPLAZO, Types.VARCHAR));
		//declareParameter(new SqlParameter(NINDICADOR, Types.VARCHAR));
		//declareParameter(new SqlParameter(NCODCENTRO, Types.VARCHAR));
		
		compile();
	}

	public Map execute(EntranteBean entranteBean) {
		Map inputs = new HashMap();		
		inputs.put(NORIGEN, entranteBean.getOrigen());
		inputs.put(VTIPODOC, entranteBean.getTipodoc());
		inputs.put(NCODAREA, String.valueOf(entranteBean.getNcodarea()));	
		inputs.put(NCODAREA_ORIGEN, String.valueOf(entranteBean.getArea_origen()));	
		inputs.put(VREMITENTE, entranteBean.getVremitente());
		inputs.put(NDIRIGIDO, entranteBean.getVdirigido());
		inputs.put(NFICHA_DIRIGIDO, entranteBean.getFicha_dirigido());		
		inputs.put(VNUMDOC, entranteBean.getVnumdoc());
		inputs.put(VASUNTO, entranteBean.getVasunto());
		inputs.put(VREFERENCIA, entranteBean.getVreferencia());
		inputs.put(VOBSERVACION, entranteBean.getVobservacion());
		inputs.put(VACCION, entranteBean.getVaccion());
		inputs.put(VPRIORIDAD, entranteBean.getVprioridad());
		inputs.put(VESTADO, entranteBean.getVestado());
		inputs.put(DFECDOC, entranteBean.getDfecdoc());
		inputs.put(VRESCRE, entranteBean.getVrescre());
		inputs.put(VRESACT, entranteBean.getVresact());
		//inputs.put(NDIASPLAZO, String.valueOf(entranteBean.getNdiasplazo()));
		//inputs.put(NINDICADOR, String.valueOf(entranteBean.getNindicador()));
		//inputs.put(NCODCENTRO, String.valueOf(entranteBean.getNcodcentro()));
		System.out.println("Fin de Execute SP nuevo");
		return super.execute(inputs);
	}

}