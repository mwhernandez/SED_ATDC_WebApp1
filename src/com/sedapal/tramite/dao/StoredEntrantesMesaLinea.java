package com.sedapal.tramite.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.EntranteMesaBean;

public class StoredEntrantesMesaLinea extends StoredProcedure implements RowMapper {
	
	private static final String SPROC_NAME = "ATDC_DOC_ENTRAN_MESA_LINEA";	
	private static final String NCODAREA = "v_codarea";

		
	public StoredEntrantesMesaLinea() {
		// TODO Auto-generated constructor stub
	}

	public StoredEntrantesMesaLinea(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("entrantesmesa",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String ncodarea) {
		Map inputs = new HashMap();		
		inputs.put(NCODAREA, ncodarea);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		EntranteMesaBean bean = new EntranteMesaBean();
        bean.setNano(rs.getInt("nano"));
        bean.setNorigen(rs.getInt("norigen"));
        bean.setOrigen(rs.getString("origen"));
        bean.setVtipodoc(rs.getString("vtipodoc"));
        bean.setTipodoc(rs.getString("tipodoc"));
        bean.setNcorrelativo(rs.getInt("ncorrelativo"));
        bean.setVnumdoc(rs.getString("vnumdoc"));
        bean.setVmesaparte(rs.getString("vmesaparte"));
        bean.setNremitente(rs.getInt("nremitente"));
        bean.setVremitente(rs.getString("vremitente"));
        //bean.setDfecdoc(rs.getDate("dfecdoc"));
        bean.setDfecdoc(rs.getDate(("dfeccre")));
        bean.setDhoraderivacion(rs.getTime(("dfeccre")));
        bean.setDfecregistro(rs.getDate("dfecregistro"));
        bean.setDhoraregistro(rs.getTime("dfecregistro"));
        bean.setNdirigido(rs.getInt("ndirigido"));
        bean.setVdirigido(rs.getString("areadirigido"));
        bean.setVasunto(rs.getString("vasunto"));
        bean.setVreferencia(rs.getString("vreferencia"));
        bean.setVobservacion(rs.getString("vobservacion"));
        bean.setVprioridad(rs.getString("vprioridad"));
        bean.setVaccion(rs.getString("vaccion"));
        //bean.setAccion(rs.getString("accion"));	        
        //bean.setVubiarchivo(rs.getString("vubiarchivo"));
        bean.setVubiarchivo(rs.getString("vubiarchivo"));
        bean.setUbicacion_mesa(rs.getString("ubicacion"));
        bean.setDfecplazo(rs.getDate("dfecplazo"));
        bean.setNdiasplazo(rs.getInt("ndiasplazo"));
        bean.setNestado(rs.getString("vestado"));
        bean.setVestado(rs.getString("estado"));
        bean.setDfeccre(rs.getDate("dfeccre"));
        bean.setDfecact(rs.getDate("dfecact"));
        bean.setVrescre(rs.getString("vrescre"));
        bean.setVresact(rs.getString("vresact"));
        bean.setNcodarea(rs.getInt("ncodarea"));	        
        bean.setArea(rs.getString("area"));
        //bean.setFicha_dirigido(rs.getLong("nficha_dirigido"));
        bean.setFicha_dirigido(rs.getLong("nremitente"));
        bean.setNcodarea_origen(rs.getInt("ncodarea_origen"));
        bean.setArea_origen(rs.getString("areaorigen"));
        bean.setNcodcentro(rs.getInt("ncodcentro"));
        bean.setCentro(rs.getString("centro"));
        bean.setNindicador(rs.getInt("nindicador"));
        bean.setNfolio(rs.getInt("FOLIOS"));
        bean.setSistema(rs.getString("SISTEMA"));
        bean.setNrecepcion(rs.getInt("NRECEPCION"));
        bean.setDfecharecepcion(rs.getDate("DFECRECEPCION"));
        bean.setVtipoorigen(rs.getString("TIPOORIGEN"));
        bean.setVlote(rs.getString("LOTE"));
	return bean;
}
}