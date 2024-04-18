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

public class StoredCriteriosSalientes extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_CRITERIO_SALIENTE";	
	private static final String FECHA1 = "v_fecha1";
	private static final String FECHA2 = "v_fecha2";
	private static final String TIPOS = "v_tipos";
	private static final String CRITERIOS = "v_criterios";
	private static final String AREA = "v_area";
	private static final String DETALLECRITERIO = "v_detalle";
		
	public StoredCriteriosSalientes() {
		// TODO Auto-generated constructor stub
	}

	public StoredCriteriosSalientes(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(FECHA1, Types.VARCHAR));
		declareParameter(new SqlParameter(FECHA2, Types.VARCHAR));
		declareParameter(new SqlParameter(TIPOS, Types.VARCHAR));
		declareParameter(new SqlParameter(CRITERIOS, Types.VARCHAR));
		declareParameter(new SqlParameter(AREA, Types.VARCHAR));
		declareParameter(new SqlParameter(DETALLECRITERIO, Types.VARCHAR));
		declareParameter(new SqlOutParameter("criterios",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String fecha1, String fecha2, String tipos, String criterios,String area,String detallecriterio) {
		System.out.println("Execute Stored..."+ fecha1+"   "+ fecha2 +"  "+tipos);
		Map inputs = new HashMap();		
		inputs.put(FECHA1, fecha1);
		inputs.put(FECHA2, fecha2);
		inputs.put(TIPOS, tipos);
		inputs.put(CRITERIOS, criterios);
		inputs.put(AREA, area);
		inputs.put(DETALLECRITERIO, detallecriterio);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		DocumentoSalidaBean bean = new DocumentoSalidaBean();
		bean.setAno(rs.getInt("ano"));	
		bean.setNom_area(rs.getString("nom_areas"));		
		bean.setOrigenes(rs.getString("origen"));
		bean.setTipodoc(rs.getString("tipodoc"));
		bean.setNtipodoc(rs.getString("ncodtipo"));
		bean.setCodigo(rs.getInt("codigo"));
		bean.setNumerodoc(rs.getString("numerodoc"));		
		bean.setEstado(rs.getString("estado"));
		bean.setNestado(rs.getString("nestado"));
		bean.setFecha(rs.getDate("fecha"));
		bean.setDirigido(rs.getString("dirigido"));
		bean.setAsunto(rs.getString("asunto"));
		bean.setObservacion(rs.getString("observacion"));
		bean.setTrabajador(rs.getString("trabajador"));
		bean.setReferencia(rs.getString("referencia"));
		bean.setDoc_entrada(rs.getString("doc_entrante"));
		bean.setPrioridad(rs.getString("prioridad"));
		bean.setNdirigido(rs.getInt("ndirigido"));
		bean.setFicha_dirigido(rs.getLong("nficha_dirigido"));
		bean.setUbiarchivo(rs.getString("ubiarchivo"));			
		bean.setIndicador(rs.getInt("indicador"));		
		return bean;
	}
}