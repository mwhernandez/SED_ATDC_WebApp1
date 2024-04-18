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
import com.sedapal.tramite.beans.SeguimientoEntranteBean;

public class StoredFiltrosSegEnt extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_FILTRO_SEGUIMIENTO";
	private static final String FECHA1 = "v_fecha1";
	private static final String FECHA2 = "v_fecha2";
	private static final String TIPOS = "v_tipos";	
	private static final String AREA = "v_area";
	
	public StoredFiltrosSegEnt() {
		// TODO Auto-generated constructor stub
	}

	public StoredFiltrosSegEnt(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(FECHA1, Types.VARCHAR));
		declareParameter(new SqlParameter(FECHA2, Types.VARCHAR));
		declareParameter(new SqlParameter(TIPOS, Types.VARCHAR));
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("filtros_seguimiento",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String fecha1, String fecha2, String tipos, String area) {
		System.out.println("Execute Stored..."+ fecha1+"   "+ fecha2 +"  "+tipos +"  "+ area);
		Map inputs = new HashMap();		
		inputs.put(FECHA1, fecha1);
		inputs.put(FECHA2, fecha2);
		inputs.put(TIPOS, tipos);
		inputs.put(AREA, area);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		SeguimientoEntranteBean bean = new SeguimientoEntranteBean();
		bean.setAno(rs.getInt("ano"));		
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
		bean.setAccion(rs.getString("accion"));
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
		return bean;
	}
}