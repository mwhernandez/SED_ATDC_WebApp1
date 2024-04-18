package com.sedapal.tramite.dao;


import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.object.StoredProcedure;
import com.sedapal.tramite.beans.RemitenteBPM;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import oracle.jdbc.driver.OracleTypes;
import java.sql.Types;

public class StoredRegistraRemitenteBPM extends StoredProcedure{
	private static final String SPROC_NAME = "PCK_BPM_GENERAL.BPM_NUEVO_REMITENTE"; 
	private static final String TIPO_DOC = "v_tipdoc";
	private static final String NUM_DOC = "v_numdoc";
	private static final String NOMBRE = "v_nombre";
	private static final String CORREO = "v_correo";
	private static final String TELEFONO = "v_telefono";
	private static final String DIRECCION = "v_direccion";
	private static final String INDCASILLA = "n_indcasilla";
	
	

	
	public StoredRegistraRemitenteBPM(DataSource dataSource) {
		super(dataSource, SPROC_NAME);
		declareParameter(new SqlParameter(TIPO_DOC, Types.VARCHAR));	
		declareParameter(new SqlParameter(NUM_DOC, Types.VARCHAR));
		declareParameter(new SqlParameter(NOMBRE, Types.VARCHAR));
		declareParameter(new SqlParameter(CORREO, Types.VARCHAR));
		declareParameter(new SqlParameter(TELEFONO, Types.VARCHAR));
		declareParameter(new SqlParameter(DIRECCION, Types.VARCHAR));
		declareParameter(new SqlParameter(INDCASILLA, Types.INTEGER));
		declareParameter(new SqlOutParameter("o_correlativo",OracleTypes.INTEGER));
		declareParameter(new SqlOutParameter("o_mensaje",OracleTypes.VARCHAR));
		declareParameter(new SqlOutParameter("o_retorno",OracleTypes.INTEGER));
		compile();
	}
	
	public Map execute(RemitenteBPM remitente) {
		Map inputs = new HashMap();		
		inputs.put(TIPO_DOC, remitente.getTipoDocumento());
		inputs.put(NUM_DOC, remitente.getNroDocumento());
		inputs.put(NOMBRE, remitente.getNombre());
		inputs.put(CORREO, remitente.getCorreo());
		inputs.put(TELEFONO, remitente.getTelefono());
		inputs.put(DIRECCION, remitente.getDireccion());
		inputs.put(INDCASILLA, remitente.getIndCasilla());
		return super.execute(inputs);
	}
	
	public StoredRegistraRemitenteBPM() {
		// TODO Auto-generated constructor stub
	}

	

}
