package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleSql;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.PerfilBean;
import com.sedapal.tramite.beans.RemitenteBean;
import com.sedapal.tramite.beans.Usuario;


public class StoredNuevoLogIngreso extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_INSERTARLOG";	
	private static final String VUSUARIO = "v_responsable";
	private static final String VCODIP = "v_codip";
	private static final String NAREA = "n_area";
		
	
	public StoredNuevoLogIngreso(DataSource dataSource) {
		super(dataSource, SPROC_NAME);		
		declareParameter(new SqlParameter(VUSUARIO, Types.VARCHAR));
		declareParameter(new SqlParameter(VCODIP, Types.VARCHAR));
		declareParameter(new SqlParameter(NAREA, Types.NUMERIC));
		//declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(Usuario usuarioBean) {
		Map inputs = new HashMap();
		System.out.println("Inicio del SP ATDC_INSERTARLOG");
		inputs.put(VUSUARIO, usuarioBean.getLogin());
		System.out.println(usuarioBean.getLogin());
		inputs.put(VCODIP, usuarioBean.getIp_desktop());
		System.out.println(usuarioBean.getIp_desktop());
		inputs.put(NAREA, usuarioBean.getCodarea());
		System.out.println(usuarioBean.getCodarea());
		System.out.println("Fin del SP ATDC_INSERTARLOG");				
		return super.execute(inputs);
	}

}
