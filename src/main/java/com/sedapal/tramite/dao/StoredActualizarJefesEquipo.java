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

public class StoredActualizarJefesEquipo extends StoredProcedure implements RowMapper {

    private static final String SPROC_NAME     = "ATDC_PKG_MAINT.ATDC_UPDATE_JEFE_EQUIPO_AREA";
    private static final String CODAREA        = "v_codArea";
    private static final String CODFICHA       = "v_codFicha";
    private static final String APEPATERNO     = "v_apePaterno";
    private static final String APEMATERNO     = "v_apeMaterno";
    private static final String NOMBRES        = "v_nombres";
    private static final String USURESPONSABLE = "v_usuResponsable";

    public StoredActualizarJefesEquipo() {
        // TODO Auto-generated constructor stub
    }

    public StoredActualizarJefesEquipo(DataSource dataSource) {
        super(dataSource, SPROC_NAME);        
        declareParameter(new SqlParameter(CODAREA, Types.VARCHAR));
        declareParameter(new SqlParameter(CODFICHA, Types.VARCHAR));
        declareParameter(new SqlParameter(APEPATERNO, Types.VARCHAR));
        declareParameter(new SqlParameter(APEMATERNO, Types.VARCHAR));
        declareParameter(new SqlParameter(NOMBRES, Types.VARCHAR));
        declareParameter(new SqlParameter(USURESPONSABLE, Types.VARCHAR));
        declareParameter(new SqlOutParameter("tipMsgSalida", Types.VARCHAR, this));
        declareParameter(new SqlOutParameter("msgSalida", OracleTypes.VARCHAR, this));
        compile();
    }

    public Map execute(String codArea, String codFicha, String apePaterno, String apeMaterno, String nombres, String usuResponsable) {
        System.out.println("Execute Stored..." + codArea + " " + codFicha + " " + apePaterno + " " + apeMaterno + " " + nombres + " " + usuResponsable);
        Map inputs = new HashMap();        
        inputs.put(CODAREA, codArea);
        inputs.put(CODFICHA, codFicha);
        inputs.put(APEPATERNO, apePaterno);
        inputs.put(APEMATERNO, apeMaterno);
        inputs.put(NOMBRES, nombres);
        inputs.put(USURESPONSABLE, usuResponsable);
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