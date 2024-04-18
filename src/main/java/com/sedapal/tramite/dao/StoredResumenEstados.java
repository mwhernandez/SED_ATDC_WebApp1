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

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.estadisticas.EstadisticaDocEntrandaBean;

public class StoredResumenEstados extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_RESUMEN_ESTADOS_ENTR";
	private static final String FECHA_INI = "v_fechaIni";
    private static final String FECHA_FIN = "v_fechaFin";    
    private static final String NCODAREA  = "vcodarea";
		
	public StoredResumenEstados() {
		// TODO Auto-generated constructor stub
	}

	public StoredResumenEstados(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(FECHA_INI, Types.VARCHAR));
        declareParameter(new SqlParameter(FECHA_FIN, Types.VARCHAR));
        declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
        declareParameter(new SqlOutParameter("resultList", OracleTypes.CURSOR, this));
        compile();
	}

	public Map execute(String iniFecha, String finFecha, int codarea) {
		System.out.println("Valores para el Stored : "+iniFecha+", "+finFecha+" ");
        Map inputs = new HashMap();
        inputs.put(FECHA_INI, iniFecha);
        inputs.put(FECHA_FIN, finFecha);
        inputs.put(NCODAREA, codarea);
        return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		EstadisticaDocEntrandaBean beanEstDocEntr = new EstadisticaDocEntrandaBean();
    	beanEstDocEntr.setMes(rs.getString("nombre"));
    	beanEstDocEntr.setEstado(rs.getString("estado"));
    	beanEstDocEntr.setCantidad(rs.getString("cantidad"));    	
        return beanEstDocEntr;
	}
}