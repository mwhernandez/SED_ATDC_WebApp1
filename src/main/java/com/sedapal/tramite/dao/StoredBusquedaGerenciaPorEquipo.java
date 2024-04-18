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
import com.sedapal.tramite.beans.ReporteGerencialBean;

public class StoredBusquedaGerenciaPorEquipo extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_RPTE_GERENCIAL_EQUIPO";	
	private static final String AREA = "n_area";
	private static final String FECHA_INICIAL = "fec_inicial";
	private static final String FECHA_FIN = "fec_fin";	
	
	

	
		
	public StoredBusquedaGerenciaPorEquipo() {
		// TODO Auto-generated constructor stub
	}

	public StoredBusquedaGerenciaPorEquipo(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(AREA, Types.NUMERIC));
		declareParameter(new SqlParameter(FECHA_INICIAL, Types.VARCHAR));
		declareParameter(new SqlParameter(FECHA_FIN, Types.VARCHAR));
		declareParameter(new SqlOutParameter("reportegerenciaporequipo",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(int area, String fechainicial, String fechafin) {
		System.out.println("Execute Stored..."+ area+"   "+ fechainicial +" "+ fechafin );
		Map inputs = new HashMap();	
		inputs.put(AREA, area);
		inputs.put(FECHA_INICIAL, fechainicial);
		inputs.put(FECHA_FIN, fechafin);	
		return super.execute(inputs);
	}
	
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReporteGerencialBean bean = new ReporteGerencialBean();       
		    bean.setNcorrelativo(rs.getInt("REGISTRO"));
	        bean.setVnumdoc(rs.getString("NUMDOC"));
	        bean.setArearemitente(rs.getString("AREAREMITE"));
	        bean.setAsunto(rs.getString("ASUNTO"));
	        bean.setPrioridad(rs.getString("PRIORIDAD"));
	        bean.setTipDocumento(rs.getString("TIPODOC"));
	        bean.setFechadoc(rs.getDate("FECDOC"));
	        bean.setFechacrea(rs.getDate("FECCRE"));
	        bean.setFechaderivacion(rs.getDate("FECHADERIVACION"));
	        bean.setFechaplazo(rs.getDate("FECPLAZO"));
	        bean.setDiasplazo(rs.getInt("DIASPLAZO"));        
	        bean.setDiastransc(rs.getInt("DIASTRANSCURRIDOS"));
	        bean.setEstado(rs.getString("ESTADO"));
	        bean.setComentario(rs.getString("COMENTARIO"));
	        bean.setDerivacion(rs.getString("TRABAJADOR"));
	        //bean.setFechaatencion(rs.getDate("FECATENCION"));
	        //bean.setSituacion_documento(rs.getString("SITUACION_DOCUMENTO"));
        //bean.setVubiarchivo(rs.getString("UBIARCHIVO"));
        //bean.setVestado(rs.getString("CODESTADO"));
        //bean.setIndicador(rs.getInt("INDICADOR"));		
        return bean;
		 
	}

}
