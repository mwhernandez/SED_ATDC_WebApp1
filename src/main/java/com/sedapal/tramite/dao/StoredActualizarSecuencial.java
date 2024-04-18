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

public class StoredActualizarSecuencial extends StoredProcedure implements RowMapper {

    private static final String SPROC_NAME = "ATDC_PKG_MAINT.ATDC_UPDATE_SECUENCIAL_AREA";
    private static final String CODAREA    = "v_codArea";
    private static final String ANNIO      = "v_annio";
    private static final String CODTIPDOC  = "v_codTipDoc";
    private static final String SECUENCIAL = "v_secuencial";
    private static final String ESTADO     = "v_estado";

    public StoredActualizarSecuencial() {
        // TODO Auto-generated constructor stub
    }

    public StoredActualizarSecuencial(DataSource dataSource) {
        super(dataSource, SPROC_NAME);        
        declareParameter(new SqlParameter(CODAREA, Types.VARCHAR));
        declareParameter(new SqlParameter(ANNIO, Types.VARCHAR));
        declareParameter(new SqlParameter(CODTIPDOC, Types.VARCHAR));
        declareParameter(new SqlParameter(SECUENCIAL, Types.VARCHAR));
        declareParameter(new SqlParameter(ESTADO, Types.VARCHAR));
        declareParameter(new SqlOutParameter("tipMsgSalida", Types.VARCHAR, this));
        declareParameter(new SqlOutParameter("msgSalida", OracleTypes.VARCHAR, this));
        compile();
    }

    public Map execute(String codArea, String annio, String codTipDoc, String secuencial, String estado) {
        System.out.println("Execute Stored..." + codArea + " " + annio + " " + codTipDoc + " " + secuencial + " " + estado);
        Map inputs = new HashMap();        
        inputs.put(CODAREA, codArea);
        inputs.put(ANNIO, annio);
        inputs.put(CODTIPDOC, codTipDoc);
        inputs.put(SECUENCIAL, secuencial);
        inputs.put(ESTADO, estado);
        return super.execute(inputs);
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map datosSalida = new HashMap();
        datosSalida.put("tipMsgSalida", rs.getString("tipMsgSalida"));
        datosSalida.put("msgSalida", rs.getString("msgSalida"));
        return datosSalida;
    }
}