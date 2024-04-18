package com.sedapal.tramite.dao;

import java.sql.Date;
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

public class StoredConsultaSegDocEntrantes extends StoredProcedure implements RowMapper {
	private static final String SPROC_NAME = "S_GUIA.ATDC_CONS_SEG_DOC_ENT";
	private static final String FECHA1 = "v_fecha1";
	private static final String FECHA2 = "v_fecha2";
	private static final String TIPOS = "v_tipos";	
	
	
	public StoredConsultaSegDocEntrantes() {
		// TODO Auto-generated constructor stub
	}
	

	public StoredConsultaSegDocEntrantes(DataSource dataSource) {
	super(dataSource, SPROC_NAME);
	declareParameter(new SqlParameter(FECHA1, Types.VARCHAR));
	declareParameter(new SqlParameter(FECHA2, Types.VARCHAR));
	declareParameter(new SqlParameter(TIPOS, Types.VARCHAR));		
	declareParameter(new SqlOutParameter("consulta",OracleTypes.CURSOR, this));//cuenta_cursor?
	compile();
	}

public Map execute(String fecha1, String fecha2, String tipos) {
	System.out.println("Execute Stored..."+ fecha1+"   "+ fecha2  );
	Map inputs = new HashMap();	
	inputs.put(FECHA1, fecha1);
	inputs.put(FECHA2, fecha2);
	inputs.put(TIPOS, tipos);	
	System.out.println("Envia de datos");
	System.out.println(fecha1);
	System.out.println(fecha2);
	System.out.println(tipos);
	return super.execute(inputs);
}

@Override
public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	EntranteBean bean = new EntranteBean();
    bean.setOrigen(rs.getString("origen"));  
    bean.setTipodoc(rs.getString("tipodoc"));
    bean.setNcorrelativo(rs.getInt("ncorrelativo"));
    bean.setVnumdoc(rs.getString("vnumdoc"));
    bean.setDfecdoc(rs.getDate("dfecdoc"));
    bean.setDfecregistro(rs.getDate("dfecregistro"));
    bean.setVdirigido(rs.getString("remite"));
    bean.setVasunto(rs.getString("vasunto"));
    bean.setVprioridad(rs.getString("vprioridad"));
    bean.setDfecplazo(rs.getDate("dfecplazo"));
    bean.setNdiasplazo(rs.getInt("ndiasplazo"));
    bean.setVestado(rs.getString("estado"));
    bean.setVremitente(rs.getString("area_derivado"));
    bean.setPersonaderivado(rs.getString("persona_derivado"));
    
    ///**********************************************//////
    
    
    //bean.setVmesaparte(rs.getString("vmesaparte"));
    //bean.setNremitente(rs.getInt("nremitente"));       
    //bean.setNdirigido(rs.getInt("ndirigido"));        
    //bean.setVreferencia(rs.getString("vreferencia"));
    //bean.setVobservacion(rs.getString("vobservacion"));    
    //bean.setVaccion(rs.getString("vaccion"));         
    //bean.setVubiarchivo(rs.getString("vubiarchivo"));   
    //bean.setNestado(rs.getString("vestado"));    
    //bean.setDfeccre(rs.getDate("dfeccre"));
    //bean.setDfecact(rs.getDate("dfecact"));
    //bean.setVrescre(rs.getString("vrescre"));
    //bean.setVresact(rs.getString("vresact"));
    //bean.setNcodarea(rs.getInt("ncodarea"));	        
    //bean.setArea(rs.getString("area"));
    //bean.setFicha_dirigido(rs.getLong("nficha_dirigido"));
    //bean.setFicha_dirigido(rs.getLong("nremitente"));
    //bean.setNcodarea_origen(rs.getInt("ncodarea_origen"));
    //bean.setArea_origen(rs.getString("areaorigen"));
    //bean.setNcodcentro(rs.getInt("ncodcentro"));
    //bean.setCentro(rs.getString("centro"));
    //bean.setNindicador(rs.getInt("nindicador"));    
    System.out.println(rs.getInt("nano"));
    System.out.println(rs.getString("origen"));
    System.out.println(rs.getInt("ncorrelativo"));
    System.out.println(rs.getString("vasunto"));
    return bean;
	 
}

}
