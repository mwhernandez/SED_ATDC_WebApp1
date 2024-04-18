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

public class StoredBusquedaDocumentoGerencia extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_DOC_DERIVADO_GERENCIA";	
	private static final String AREA = "n_area";
	private static final String FECHA_INICIAL = "fec_inicial";
	private static final String FECHA_FIN = "fec_fin";	
	
	

	
		
	public StoredBusquedaDocumentoGerencia() {
		// TODO Auto-generated constructor stub
	}

	public StoredBusquedaDocumentoGerencia(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(AREA, Types.NUMERIC));
		declareParameter(new SqlParameter(FECHA_INICIAL, Types.VARCHAR));
		declareParameter(new SqlParameter(FECHA_FIN, Types.VARCHAR));
		declareParameter(new SqlOutParameter("documentogerencia",OracleTypes.CURSOR, this));//cuenta_cursor?
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
	        bean.setAreaderivado(rs.getString("AREADERIVADO"));
	        bean.setAsunto(rs.getString("ASUNTO"));
	        bean.setPrioridad(rs.getString("PRIORIDAD"));	        
	        bean.setTipDocumento(rs.getString("TIPODOC"));
	        bean.setFechadoc(rs.getDate("FECDOC"));
	        bean.setFechacrea(rs.getDate("FECCRE"));
	        bean.setFecharecepcion(rs.getDate("FECRECEPCION"));
	        bean.setDiasplazo(rs.getInt("DIASPLAZO")); 
	        bean.setFechaplazo(rs.getDate("FECHAPLAZO"));
	        bean.setEstado(rs.getString("ESTADO"));
	        bean.setVestado(rs.getString("VESTADO"));
	        
        return bean;
		 
	}

}
