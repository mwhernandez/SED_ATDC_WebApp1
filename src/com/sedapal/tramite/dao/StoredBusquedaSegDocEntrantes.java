package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.DocumentoSalidaBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
import com.sedapal.tramite.beans.UsersBean;

public class StoredBusquedaSegDocEntrantes extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_BUSQUEDA_SEGUIMIENTO";	
	private static final String NANO = "v_ano";
	private static final String NORIGEN = "v_origen";
	private static final String NCORRELATIVO = "v_correlativo";
	private static final String AREA = "v_area";
	private static final String OPCION = "v_opcion";
	private static final String DETALLE = "v_detalle";
	
			
	public StoredBusquedaSegDocEntrantes() {
		// TODO Auto-generated constructor stub
	}

	public StoredBusquedaSegDocEntrantes(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));		
		declareParameter(new SqlOutParameter("busquedas_seguimiento",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(int ano, int origen, long correlativo, String area, String opcion, String detalle) {
		System.out.println("Execute Stored..."+ opcion+"   "+ detalle);
		Map inputs = new HashMap();	
		inputs.put(NANO, ano);
		inputs.put(NORIGEN, origen);
		inputs.put(NCORRELATIVO, correlativo);
		inputs.put(AREA, area);
		inputs.put(OPCION, opcion);
		inputs.put(DETALLE, detalle);		
		return super.execute(inputs);
	}
	
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		SeguimientoEntranteBean bean = new SeguimientoEntranteBean();		
		bean.setAno(rs.getInt("nano"));		
		bean.setOrigen(rs.getString("origen"));
		bean.setNorigen(rs.getInt("norigen"));
		bean.setTipodoc(rs.getString("tipodoc"));
		bean.setDestipodoc(rs.getString("destipodoc"));
		bean.setCorrelativo(rs.getInt("correlativo"));
		bean.setAsunto(rs.getString("asunto"));
		bean.setNumdoc(rs.getString("numdoc"));
		bean.setFecrecepcion(rs.getDate("fecrecepcion"));
		bean.setFecplazo(rs.getDate("fecplazo"));
		bean.setVestado(rs.getString("vestado"));
		bean.setEstado(rs.getString("estado"));
		bean.setNombre_derivado(rs.getString("nombre_derivado"));
		//bean.setAccion(rs.getString("accion"));				
		bean.setComentario(rs.getString("comentario"));		
		bean.setSeguimiento(rs.getInt("seguimiento"));
		bean.setVestado(rs.getString("vestado"));
		bean.setDerivado(rs.getLong("FICHADERIVADO"));
		bean.setFecderivado(rs.getDate("fecderivacion"));
		bean.setResact(rs.getString("actualizador"));
		bean.setRescre(rs.getString("responsable"));
		bean.setFecact(rs.getDate("factual"));
		bean.setFeccre(rs.getDate("fcrea"));
		bean.setAccion(rs.getString("vaccion"));
		bean.setNombre_remitente(rs.getString("nombre_remitente"));
		bean.setAreaderivado(rs.getInt("areaderivado"));
		bean.setArearemitente(rs.getInt("arearemitente"));
		bean.setFicharemitente(rs.getInt("ficharemitente"));
		bean.setAbrevderivado(rs.getString("abrevderivado"));
		bean.setAbrevremite(rs.getString("abrevremite"));
		bean.setFicha_dirigido(rs.getInt("dirigido"));
		bean.setIndicador(rs.getInt("indicador"));
		bean.setUbicaarchivo(rs.getString("ubiarchivo"));
		bean.setUbicacion_seguimiento(rs.getString("UBICACION"));
		bean.setIndicaarchivo(rs.getInt("indicaarchivo"));
		bean.setNrecepcion(rs.getInt("recepcion"));
		bean.setNplazo(rs.getInt("plazo"));
		return bean;	
		 
	}

}
