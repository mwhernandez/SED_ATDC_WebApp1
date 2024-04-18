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
import com.sedapal.tramite.beans.estadisticas.EstadisticaDocEntrandaBean;

public class StoredResumenPorSituacionDoc extends StoredProcedure implements RowMapper {

    private static final String SPROC_NAME = "ATDC_ESTADISTICO_SITUACION";
    private static final String FECHA_INI = "v_fechaIni";
    private static final String FECHA_FIN = "v_fechaFin";
    private static final String COD_AREA = "v_codarea";

    public StoredResumenPorSituacionDoc() {
        // TODO Auto-generated constructor stub
    }

    public StoredResumenPorSituacionDoc(DataSource dataSource) {
        super(dataSource, SPROC_NAME);
        declareParameter(new SqlParameter(FECHA_INI, Types.VARCHAR));
        declareParameter(new SqlParameter(FECHA_FIN, Types.VARCHAR));
        declareParameter(new SqlParameter(COD_AREA, Types.VARCHAR));
        declareParameter(new SqlOutParameter("resultList", OracleTypes.CURSOR, this));
        compile();
    }

    public Map execute(String iniFecha, String finFecha, int codArea) {
        System.out.println("Valores para el Stored : "+iniFecha+", "+finFecha+", "+codArea);
        System.out.println("sp ....ATDC_ESTADISTICO_SITUACION");
        Map inputs = new HashMap();
        inputs.put(FECHA_INI, iniFecha);
        inputs.put(FECHA_FIN, finFecha);
        inputs.put(COD_AREA, String.valueOf(codArea));        
        return super.execute(inputs);
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        EstadisticaDocEntrandaBean beanEstDocEntr = new EstadisticaDocEntrandaBean();
        beanEstDocEntr.setNomArea(rs.getString("AREA"));
        beanEstDocEntr.setNomColumna(rs.getString("SITUACION"));
        beanEstDocEntr.setValColumna(rs.getString("TOTAL"));
        return beanEstDocEntr;
    }
}