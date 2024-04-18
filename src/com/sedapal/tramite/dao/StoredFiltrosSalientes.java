package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.DocumentoSalidaBean;

public class StoredFiltrosSalientes extends StoredProcedure implements RowMapper {
													 
	private static final String SPROC_NAME = "ATDC_FILTRO_SALIENTE";	
	private static final String FECHA1 = "v_fecha1";
	private static final String FECHA2 = "v_fecha2";
	private static final String OPCION = "v_opcion";
	private static final String AREA = "v_area";
	private static final String TIPO = "v_tipo";
	private static final String REMITENTE = "v_remitente";
	
		
	public StoredFiltrosSalientes() {
		// TODO Auto-generated constructor stub
	}

	public StoredFiltrosSalientes(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(FECHA1, Types.VARCHAR));
		declareParameter(new SqlParameter(FECHA2, Types.VARCHAR));
		declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlParameter(TIPO, Types.VARCHAR));
		declareParameter(new SqlParameter(REMITENTE, Types.VARCHAR));
		declareParameter(new SqlOutParameter("filtros",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String fecha1, String fecha2, String opcion, String area, String vtipo, String areaseleccionado) {
		System.out.println("ATDC_FILTRO_SALIENTE");
		System.out.println("Execute Stored..."+ fecha1+"   "+ fecha2 +"  "+ opcion +"  "+ area + " " + vtipo + " " + areaseleccionado);
		Map inputs = new HashMap();		
		inputs.put(FECHA1, fecha1);
		inputs.put(FECHA2, fecha2);
		inputs.put(OPCION, opcion);
		inputs.put(AREA, area);
		inputs.put(TIPO, vtipo);
		inputs.put(REMITENTE, areaseleccionado);
		return super.execute(inputs);
	}
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
		bean.setUbiarchivo(rs.getString("ubiarchivo"));	
		bean.setUbicacion_salida(rs.getString("UBICACION"));
		bean.setIndicador(rs.getInt("indicador"));	
		bean.setDfecact(rs.getDate("dfecact"));
		bean.setDfeccre(rs.getDate("dfeccre"));
		bean.setVresact(rs.getString("vrescre"));
		bean.setVrescre(rs.getString("vresact"));
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