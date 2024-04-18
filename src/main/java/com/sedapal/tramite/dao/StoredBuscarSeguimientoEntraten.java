package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;




public class StoredBuscarSeguimientoEntraten extends StoredProcedure implements RowMapper {
	
	private static final String SPROC_NAME = "ATDC_BUSCAR_DOC_SEG_ALL";	
	private static final String NANO = "v_ano";
	private static final String NORIGEN = "v_origen";
	private static final String VTIPODOC = "v_tipodoc";
	private static final String NCORRELATIVO = "v_correlativo";
	private static final String AREA = "v_area";
	
	
	public StoredBuscarSeguimientoEntraten() {
		// TODO Auto-generated constructor stub
	}

	
	public StoredBuscarSeguimientoEntraten(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(NANO, Types.VARCHAR));	
		declareParameter(new SqlParameter(NORIGEN, Types.VARCHAR));	
		declareParameter(new SqlParameter(VTIPODOC, Types.VARCHAR));	
		declareParameter(new SqlParameter(NCORRELATIVO, Types.VARCHAR));	
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("buscar_seguimiento",OracleTypes.CURSOR, this));//cuenta_cursor?		
		compile();
	}

	public Map execute(String ano, String origen, String tipo, String correlativo, String area) {		
		Map inputs = new HashMap();		
		inputs.put(NANO, ano);
		inputs.put(NORIGEN, origen);
		inputs.put(VTIPODOC, tipo);
		inputs.put(NCORRELATIVO, correlativo);
		inputs.put(AREA, area);
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
		bean.setNdiasplazo(rs.getInt("ndiasplazo"));		
		bean.setComentario(rs.getString("comentario"));		
		bean.setSeguimiento(rs.getInt("seguimiento"));
		bean.setVestado(rs.getString("vestado"));
		bean.setDerivado(rs.getLong("derivado"));
		bean.setFecderivado(rs.getDate("fecderivacion"));
		bean.setResact(rs.getString("actualizador"));
		bean.setRescre(rs.getString("responsable"));
		bean.setFecact(rs.getDate("factual"));
		bean.setFeccre(rs.getDate("fcrea"));
		bean.setVaccion(rs.getString("vaccion"));
		bean.setNombre_remitente(rs.getString("nombre_remitente"));
		bean.setAreaderivado(rs.getInt("areaderivado"));
		bean.setArearemitente(rs.getInt("arearemitente"));
		bean.setFicharemitente(rs.getInt("ficharemitente"));
		bean.setAbrevderivado(rs.getString("abrevderivado"));
		bean.setAbrevremite(rs.getString("abrevremite"));
		//bean.setFicha_dirigido(rs.getInt("dirigido"));
		//System.out.println(rs.getInt("norigen"));
		return bean;
		 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
