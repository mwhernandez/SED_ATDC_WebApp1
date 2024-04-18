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





import com.sedapal.tramite.beans.CorrelativosBean;
import com.sedapal.tramite.beans.TiposBean;

public class StoredListaCorrelativos extends StoredProcedure implements RowMapper {
	


	private static final String SPROC_NAME = "ATDC_PKG_MAINT.ATDC_LISTADO_SECUENCIALES";
	private static final String ANNIO   = "v_annio";
    private static final String OPCION  = "v_optFiltro";
    private static final String DETALLE = "v_txtFiltro";
		
	public StoredListaCorrelativos() {
		// TODO Auto-generated constructor stub
	}

	public StoredListaCorrelativos(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(ANNIO, Types.VARCHAR));
        declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
        declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));
		declareParameter(new SqlOutParameter("correlativos",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(String annio, String optFiltro, String txtFiltro) {
		Map inputs = new HashMap();
		inputs.put(ANNIO, annio);
        inputs.put(OPCION, optFiltro);
        inputs.put(DETALLE, txtFiltro);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CorrelativosBean bean = new CorrelativosBean();
		bean.setTipo(rs.getString("CODIGO"));
		bean.setDescripcion(rs.getString("DESCRIPCION"));
		bean.setNcorrelativo(rs.getInt("VALCOMBO"));
		bean.setCodarea(rs.getInt("CODAREA"));
		bean.setVdescarea(rs.getString("DESCAREA"));
		bean.setNvalorsecuencial(rs.getInt("VALORSECUENCIAL"));
		bean.setEstado(rs.getString("ESTADO"));
		bean.setVdesestado(rs.getString("DESCESTADO"));
		return bean;
	}

}
