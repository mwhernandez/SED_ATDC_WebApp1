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

public class StoredFiltrosEntGrupo extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "S_GUIA.ATDC_FILTRO_ENTRANTE_GRUPO";
	private static final String FECHA1 = "v_fecha1";
	private static final String FECHA2 = "v_fecha2";
	private static final String TIPOS = "v_tipos";	
	private static final String AREA = "v_area";
	private static final String ESTADO = "v_estado";
	private static final String REMITENTE = "v_remitente";
	
	public StoredFiltrosEntGrupo() {
		// TODO Auto-generated constructor stub
	}

	public StoredFiltrosEntGrupo(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(FECHA1, Types.VARCHAR));
		declareParameter(new SqlParameter(FECHA2, Types.VARCHAR));
		declareParameter(new SqlParameter(TIPOS, Types.VARCHAR));
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlParameter(ESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(REMITENTE, Types.VARCHAR));
		declareParameter(new SqlOutParameter("filtros",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String fecha1, String fecha2, String tipos, String area, String item2Seleccionado, String areaseleccionado) {
		System.out.println("Execute Stored..."+ fecha1+"   "+ fecha2 +"  "+tipos +"  "+ area + " " +item2Seleccionado+ " " + areaseleccionado);
		Map inputs = new HashMap();		
		inputs.put(FECHA1, fecha1);
		inputs.put(FECHA2, fecha2);
		inputs.put(TIPOS, tipos);
		inputs.put(AREA, area);
		inputs.put(ESTADO, item2Seleccionado);
		inputs.put(REMITENTE, areaseleccionado);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		EntranteBean bean = new EntranteBean();
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
        bean.setDfecdoc(rs.getTime(("dfeccre")));
        bean.setDhoraderivacion(rs.getTime(("dfeccre")));
        bean.setDthoradoc(rs.getTime("dfecregistro"));
        bean.setDhoraregistro(rs.getTime("dfecregistro"));
        bean.setDfecregistro(rs.getDate("dfecregistro"));
        bean.setDthoradoc(rs.getTime("dfecregistro"));
        bean.setDhoraregistro(rs.getTime("dfecregistro"));
        bean.setDfecregistro(rs.getDate("dfecregistro"));
        bean.setDthoradoc(rs.getTime("dfecregistro"));
        bean.setNdirigido(rs.getInt("ndirigido"));
        bean.setVdirigido(rs.getString("areadirigido"));
        bean.setVasunto(rs.getString("vasunto"));
        bean.setVreferencia(rs.getString("vreferencia"));
        bean.setVobservacion(rs.getString("vobservacion"));
        bean.setVprioridad(rs.getString("vprioridad"));
        bean.setVaccion(rs.getString("vaccion"));
        //bean.setAccion(rs.getString("accion"));	        
        bean.setVubiarchivo(rs.getString("vubiarchivo"));
        bean.setUbicacion_entrada(rs.getString("UBICACION"));
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
        bean.setNfolio(rs.getInt("NFOLIO"));
        bean.setSistema(rs.getString("SISTEMA"));
        bean.setNrecepcion(rs.getInt("NRECEPCION"));
        bean.setDfecharecepcion(rs.getDate("DFECRECEPCION"));
		return bean;
	}
}