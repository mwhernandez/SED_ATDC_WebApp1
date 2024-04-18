package com.sedapal.tramite.dao;


import com.sedapal.tramite.beans.TiposBean;

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

public class StoredBusquedaAsuntos extends StoredProcedure implements RowMapper {

    private static final String SPROC_NAME = "ATDC_BUSQUEDA_POPUP_ASUNTO";    
    private static final String OPCION  = "v_opcion";
	private static final String DETALLE = "v_detalle";

    public StoredBusquedaAsuntos() {
        // TODO Auto-generated constructor stub
    }

    public StoredBusquedaAsuntos(DataSource dataSource) {
        super(dataSource, SPROC_NAME);
        declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
        declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));
        declareParameter(new SqlOutParameter("busquedaasunto", OracleTypes.CURSOR, this));
        compile();
    }

    public Map execute(String opcion_asunto, String detalle_asunto) {
        System.out.println("Execute Stored..." + opcion_asunto + "   " + detalle_asunto);
        Map inputs = new HashMap();
        inputs.put(OPCION, opcion_asunto);
        inputs.put(DETALLE, detalle_asunto);
        return super.execute(inputs);
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	TiposBean bean = new TiposBean();
		bean.setCodigo(rs.getString("CODIGO"));
		bean.setDescripcion(rs.getString("ASUNTO"));		
		bean.setFecha(rs.getDate("FECCREACION"));
		bean.setResponsable(rs.getString("USUARIO"));
		bean.setEstado(rs.getString("ESTADO"));		
		return bean;
    }
}