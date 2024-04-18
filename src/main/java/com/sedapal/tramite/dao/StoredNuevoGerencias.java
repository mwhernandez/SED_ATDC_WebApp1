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

import com.sedapal.tramite.beans.GrupoBean;
import com.sedapal.tramite.beans.TiposBean;

public class StoredNuevoGerencias extends StoredProcedure{
	private static final String SPROC_NAME = "ATDC_NUEVO_GRUPO";
		
	private static final String VDESCRIPCION = "v_descripcion";
	private static final String VABREVIATURA = "v_abreviatura";
	private static final String VESTADO = "v_estado";
	private static final String VRESPONSABLE = "v_responsable";
	private static final String VDIRIGIDOS = "v_dirigidos";
	
			
	public StoredNuevoGerencias() {
		// TODO Auto-generated constructor stub
	}

	public StoredNuevoGerencias(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
	
		declareParameter(new SqlParameter(VDESCRIPCION, Types.VARCHAR));
		declareParameter(new SqlParameter(VABREVIATURA, Types.VARCHAR));
		declareParameter(new SqlParameter(VESTADO, Types.VARCHAR));
		declareParameter(new SqlParameter(VRESPONSABLE, Types.VARCHAR));
		declareParameter(new SqlParameter(VDIRIGIDOS, Types.VARCHAR));
		declareParameter(new SqlOutParameter("out",OracleTypes.VARCHAR));
		compile();
	}

	public Map execute(GrupoBean grupoBean) {
		Map inputs = new HashMap();		
		System.out.println("Pinto las variables para un nuevo grupo");
		inputs.put(VDESCRIPCION, grupoBean.getDescripcion());
		System.out.println(grupoBean.getDescripcion());		
		inputs.put(VABREVIATURA, grupoBean.getAbreviatura());
		System.out.println(grupoBean.getAbreviatura());
		inputs.put(VESTADO, grupoBean.getEstado());
		System.out.println(grupoBean.getEstado());		
		inputs.put(VRESPONSABLE, grupoBean.getRespcrea());
		System.out.println(grupoBean.getRespcrea());
		inputs.put(VDIRIGIDOS, grupoBean.getVdirigido());
		System.out.println(grupoBean.getVdirigido());		
		return super.execute(inputs);
	}

}
