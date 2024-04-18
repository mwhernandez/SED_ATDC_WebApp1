package com.sedapal.tramite.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.sedapal.tramite.beans.EntranteBean;
import com.sedapal.tramite.beans.Usuario;


public class StoredNuevoEmail extends StoredProcedure{
	private static final String SPROC_NAME = "S_GUIA.ATDC_NUEVO_EMAIL";
	private static final String FICHA = "v_ficha";
	private static final String ANNIO = "v_annio";
	private static final String VRESCRE = "v_responsable";
	
	
	
	
		
	public StoredNuevoEmail(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(FICHA, Types.VARCHAR));
		declareParameter(new SqlParameter(ANNIO, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESCRE, Types.VARCHAR));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(Usuario usuarioBean) {
		Map inputs = new HashMap();
		///verificar las asignaciones de los campos
		System.out.println("Verificado los datos");
		inputs.put(FICHA, usuarioBean.getFicha());	
		System.out.println(usuarioBean.getFicha());
		inputs.put(ANNIO, usuarioBean.getAnnio());	
		System.out.println(usuarioBean.getAnnio());
		inputs.put(VRESCRE, usuarioBean.getLogin());	
		System.out.println(usuarioBean.getLogin());		
		
		return super.execute(inputs);
	}

}
