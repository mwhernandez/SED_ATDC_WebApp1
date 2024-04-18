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

public class StoredFiltrosEnt extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "S_GUIA.ATDC_FILTRO_ENTRANTE";
	private static final String FECHA1 = "v_fecha1";
	private static final String FECHA2 = "v_fecha2";
	private static final String OPCION = "v_opcion";	
	private static final String AREA = "v_area";
	private static final String ESTADOS = "v_estado";
	private static final String REMITENTE = "v_remitente";
	
	public StoredFiltrosEnt() {
		// TODO Auto-generated constructor stub
	}

	public StoredFiltrosEnt(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(FECHA1, Types.VARCHAR));
		declareParameter(new SqlParameter(FECHA2, Types.VARCHAR));
		declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlParameter(ESTADOS, Types.VARCHAR));
		declareParameter(new SqlParameter(REMITENTE, Types.VARCHAR));		
		declareParameter(new SqlOutParameter("filtros",OracleTypes.CURSOR, this));//cuenta_cursor?...
		compile();
	}

	
	//inicio, fin, this.tipoopcion, ncodarea, this.item2Seleccionado, this.getItemareaSeleccionado()
	
	public Map execute(String fecha1, String fecha2, String tipoopcion, String area, String item2Seleccionado, String areaseleccionado) {
		System.out.println("Execute Stored..."+ fecha1+"   "+ fecha2 +"  "+tipoopcion +"  "+ area + " " + item2Seleccionado + " " + areaseleccionado);
		Map inputs = new HashMap();		
		inputs.put(FECHA1, fecha1);
		inputs.put(FECHA2, fecha2);
		inputs.put(OPCION, tipoopcion);
		inputs.put(AREA, area);
		inputs.put(ESTADOS, item2Seleccionado);
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
        bean.setDfecdoc(rs.getDate(("dfeccre")));
        bean.setDhoraderivacion(rs.getTime(("dfeccre")));      
        bean.setDthoradoc(rs.getTime("dfecregistro"));
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
        //cambio 07/09/2022 EDH
        bean.setVlote(rs.getString("LOTE"));
        bean.setVnumero_disco(rs.getString("NUMDISCO"));
        bean.setDfecdisco(rs.getDate("FECHA_DISCO"));
        bean.setNvalor_legal(rs.getInt("VALOR"));
        //SED-FON-161 INI
        bean.setNumeroBPM(rs.getLong("NUMEROBPM"));
        bean.setnIndRemitente(rs.getInt("NINDREMITENTE"));
        bean.setVnumdniruc(rs.getString("NUMERODNIRUC"));
        //bean.setNis_rad(rs.getLong("NIS_RAD"));
        //SED-FIN-161 FIN
		return bean;
	}
}