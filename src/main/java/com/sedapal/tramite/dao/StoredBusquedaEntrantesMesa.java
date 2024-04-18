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

public class StoredBusquedaEntrantesMesa extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_BUSQUEDA_DOC_ENT_MESA";	
	private static final String AREA = "v_area";
	private static final String OPCION = "v_opcion";
	private static final String DETALLE = "v_detalle";
	

	
		
	public StoredBusquedaEntrantesMesa() {
		// TODO Auto-generated constructor stub
	}

	public StoredBusquedaEntrantesMesa(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));		
		declareParameter(new SqlOutParameter("busquedas_mesa",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String area, String opcion, String detalle) {
		System.out.println("Execute Stored busqueda mesa..."+ opcion+"   "+ detalle + " " +area);
		Map inputs = new HashMap();	
		inputs.put(AREA, area);
		inputs.put(OPCION, opcion);
		inputs.put(DETALLE, detalle);		
		return super.execute(inputs);
	}
	
	@Override
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
        bean.setDfecdoc(rs.getTime(("dfeccre")));
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
        bean.setNfolio(rs.getInt("NFOLIO"));
        bean.setSistema(rs.getString("SISTEMA"));
        bean.setNrecepcion(rs.getInt("NRECEPCION"));
        bean.setDfecharecepcion(rs.getDate("DFECRECEPCION"));	
        bean.setVtipoorigen(rs.getString("TIPOORIGEN"));
      //cambio 07/09/2022 EDH
        bean.setVlote(rs.getString("LOTE"));
        bean.setVnumero_disco(rs.getString("NUMDISCO"));
        bean.setDfecdisco(rs.getDate("FECHA_DISCO"));
        bean.setNvalor_legal(rs.getInt("VALOR"));
        bean.setVnumexp(rs.getString("VNUMEXP"));
	return bean;
}

}
