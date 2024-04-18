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

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EntranteMesaBean;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;

public class StoredConsultaDocEntrantesSeg extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "S_GUIA.ATDC_CONSULTA_DOC_SEG_ALL";
	private static final String ANNO = "v_ano";
	private static final String REGISTRO = "v_correlativo";
	private static final String OPCION = "v_opcion";
	
		
		
	public StoredConsultaDocEntrantesSeg() {
		// TODO Auto-generated constructor stub
	}

	public StoredConsultaDocEntrantesSeg(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(ANNO, Types.VARCHAR));
		declareParameter(new SqlParameter(REGISTRO, Types.VARCHAR));		
		declareParameter(new SqlOutParameter("consulta_seguimiento",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String registro, String anno) {
		System.out.println("Execute Stored..."+ registro+"   "+ anno );
		Map inputs = new HashMap();	
		inputs.put(REGISTRO, registro);
		inputs.put(ANNO, anno);
		
		return super.execute(inputs);
	}
	
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		SeguimientoEntranteBean bean = new SeguimientoEntranteBean();
		//bean.setAno(rs.getInt("nano"));		
		//bean.setOrigen(rs.getString("origen"));
		//bean.setNorigen(rs.getInt("norigen"));
		//bean.setTipodoc(rs.getString("tipodoc"));
		//bean.setDestipodoc(rs.getString("destipodoc"));
		bean.setCorrelativo(rs.getInt("correlativo"));
		bean.setSeguimiento(rs.getInt("seguimiento"));
		bean.setFecderivado(rs.getDate("fecderivacion"));
		bean.setEstado(rs.getString("estado"));
		bean.setAbrevremite(rs.getString("remitente"));
		bean.setNombre_remitente(rs.getString("nombre_remitente"));
		bean.setAbrevderivado(rs.getString("nombrederivado"));		
		bean.setNombre_derivado(rs.getString("nombre_derivado"));
		//bean.setAsunto(rs.getString("asunto"));
		//bean.setNumdoc(rs.getString("numdoc"));
		//bean.setFecrecepcion(rs.getDate("fecrecepcion"));
		//bean.setFecplazo(rs.getDate("fecplazo"));
		//bean.setVestado(rs.getString("vestado"));		
		//bean.setNombre_derivado(rs.getString("nombre_derivado"));
		//bean.setAccion(rs.getString("accion"));				
		bean.setComentario(rs.getString("comentario"));		
		
		//bean.setVestado(rs.getString("vestado"));
		//bean.setDerivado(rs.getLong("FICHADERIVADO"));
		
		//bean.setResact(rs.getString("actualizador"));
		//bean.setRescre(rs.getString("responsable"));
		//bean.setFecact(rs.getDate("factual"));
		//bean.setFeccre(rs.getDate("fcrea"));
		//bean.setAccion(rs.getString("vaccion"));
		//bean.setNombre_remitente(rs.getString("nombre_remitente"));
		//bean.setArearemitente(rs.getInt("arearemitente"));
		//bean.setFicharemitente(rs.getInt("ficharemitente"));
		
		//bean.setAbrevremite(rs.getString("abrevremite"));
		//bean.setFicha_dirigido(rs.getInt("dirigido"));
		//bean.setIndicador(rs.getInt("indicador"));
		//bean.setUbicaarchivo(rs.getString("ubiarchivo"));
		//bean.setIndicaarchivo(rs.getInt("indicaarchivo"));
		//System.out.println(rs.getInt("norigen"));
		return bean;
		 
	}

}
