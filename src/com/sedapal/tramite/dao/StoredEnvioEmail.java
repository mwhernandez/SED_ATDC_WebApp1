package com.sedapal.tramite.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.EntranteBean;

public class StoredEnvioEmail extends StoredProcedure implements RowMapper {

	private static final String SPROC_NAME = "ATDC_ENVIO_EMAIL";
	private static final String NCODAREA = "v_codarea";
		
	public StoredEnvioEmail() {
		// TODO Auto-generated constructor stub
	}

	public StoredEnvioEmail(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
		declareParameter(new SqlOutParameter("email",OracleTypes.CURSOR, this));//cuenta_cursor?
		compile();
	}

	public Map execute(int area) {
		Map inputs = new HashMap();
		inputs.put(NCODAREA, area);
		return super.execute(inputs);
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		EntranteBean beanEntrante = new EntranteBean();
		beanEntrante.setVnumdoc(rs.getString("vnumdoc"));
        beanEntrante.setNcorrelativo(rs.getInt("ncorrelativo"));
        beanEntrante.setVtipodoc(rs.getString("vtipodoc"));
        beanEntrante.setTipodoc(rs.getString("tipodoc"));
        beanEntrante.setVasunto(rs.getString("vasunto"));
        beanEntrante.setNremitente(rs.getInt("nremitente"));
        beanEntrante.setVremitente(rs.getString("vremitente"));
        beanEntrante.setNdirigido(rs.getInt("ndirigido"));
        beanEntrante.setVdirigido(rs.getString("vdirigido"));
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        beanEntrante.setFechaplazo(rs.getString("fecplazo"));		
		return beanEntrante;
	}
}