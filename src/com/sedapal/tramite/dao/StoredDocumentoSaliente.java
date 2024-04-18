package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.ServidorBean;
import com.sedapal.tramite.beans.Usuario;



public class StoredDocumentoSaliente extends StoredProcedure implements RowMapper {
	
	private static final String SPROC_NAME = "S_GUIA.ATDC_DOCUMENTO_SALIENTE";
	//private static final String NUM_TARJETA = "v_numero_tarjeta";
	private static final String NCODAREA = "v_codarea";
	
	public StoredDocumentoSaliente() {
		// TODO Auto-generated constructor stub
	}

	
	public StoredDocumentoSaliente(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		//declareParameter(new SqlParameter(NUM_TARJETA, Types.VARCHAR));	
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));		
		declareParameter(new SqlOutParameter("documento",OracleTypes.CURSOR, this));//cuenta_cursor?		
		compile();
	}

	public Map execute(String area) {		
		Map inputs = new HashMap();		
		//inputs.put(NUM_TARJETA, "");
		inputs.put(NCODAREA, area);
		return super.execute(inputs);
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		DocumentoSalidaBean bean = new DocumentoSalidaBean();		        
		bean.setAno(rs.getInt("ano"));		
		bean.setNumerodoc(rs.getString("numerodoc"));	
		bean.setNom_area(rs.getString("nom_areas"));
		bean.setArea(rs.getInt("area"));
		bean.setOrigenes(rs.getString("origen"));
		bean.setOrigen(rs.getInt("norigen"));
		bean.setTipodoc(rs.getString("tipodoc"));
		bean.setNtipodoc(rs.getString("ncodtipo"));
		bean.setCodigo(rs.getInt("codigo"));			
		bean.setEstado(rs.getString("estado"));
		bean.setNestado(rs.getString("nestado"));
		bean.setFecha(rs.getDate("fecha"));
		bean.setDirigido(rs.getString("dirigido"));
		bean.setVdirigidos(rs.getString("vdirigido"));		
		bean.setAsunto(rs.getString("asunto"));
		bean.setObservacion(rs.getString("observacion"));
		bean.setTrabajador(rs.getString("trabajador"));
		bean.setReferencia(rs.getString("referencia"));
		bean.setDoc_entrada(rs.getString("doc_entrante"));
		bean.setPrioridad(rs.getString("prioridad"));
		bean.setNdirigido(rs.getInt("ndirigido"));
		bean.setFicha_dirigido(rs.getLong("nficha_dirigido"));
		bean.setNficha_jefe_equipo(rs.getInt("nficha_dirigido"));
		bean.setUbiarchivo(rs.getString("ubiarchivo"));
		bean.setUbicacion_salida(rs.getString("UBICACION"));
		bean.setIndicador(rs.getInt("indicador"));	
		bean.setDfecact(rs.getDate("dfecact"));
		bean.setDfeccre(rs.getDate("dfeccre"));
		bean.setVresact(rs.getString("vresact"));
		bean.setVrescre(rs.getString("vrescre"));		
		//bean.setRemitente(rs.getInt("remitente"));
		bean.setDfecplazo(rs.getDate("fecplazo"));
		bean.setFicha_remitente(rs.getLong("remitente"));
		bean.setVaccion(rs.getString("accion"));
		bean.setDias(rs.getInt("diasplazo"));
		bean.setIndicaadjunto(rs.getInt("indicaadjunto"));
		bean.setOpcion_seguimiento(String.valueOf(rs.getInt("nopcion")));
		bean.setValor_entrada(rs.getString("valor"));
		bean.setVcodigo_verificador(rs.getString("CODIGOVERIFICADOR"));
		bean.setVnotificador(String.valueOf(rs.getInt("NOTIFICADOR")));
		
		return bean;
		 
	}
	
	
	

}
