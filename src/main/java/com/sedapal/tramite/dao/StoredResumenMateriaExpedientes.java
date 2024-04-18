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

public class StoredResumenMateriaExpedientes extends StoredProcedure implements RowMapper {

    private static final String SPROC_NAME = "ATDC_ESTADISTICO_GERENCIAL";
    private static final String FECHA_INI = "v_fechaIni";
    private static final String FECHA_FIN = "v_fechaFin";
    private static final String TIPO_REP  = "v_tiporeporte";
    private static final String NCODAREA  = "vcodarea";
    

    public StoredResumenMateriaExpedientes() {
        // TODO Auto-generated constructor stub
    }

    public StoredResumenMateriaExpedientes(DataSource dataSource) {
        super(dataSource, SPROC_NAME);
        declareParameter(new SqlParameter(FECHA_INI, Types.VARCHAR));
        declareParameter(new SqlParameter(FECHA_FIN, Types.VARCHAR));        
        declareParameter(new SqlParameter(TIPO_REP, Types.VARCHAR));
        declareParameter(new SqlParameter(NCODAREA, Types.VARCHAR));
        declareParameter(new SqlOutParameter("resultList", OracleTypes.CURSOR, this));
        compile();
    }

    public Map execute(String iniFecha, String finFecha, String tiporeporte, int codarea) {
        System.out.println("Valores para el Stored : "+iniFecha+", "+finFecha+" ");
        Map inputs = new HashMap();
        inputs.put(FECHA_INI, iniFecha);
        inputs.put(FECHA_FIN, finFecha);
        inputs.put(TIPO_REP, tiporeporte);
        inputs.put(NCODAREA, codarea);
        return super.execute(inputs);
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	EstadisticaDocEntrandaBean beanEstDocEntr = new EstadisticaDocEntrandaBean();        
        beanEstDocEntr.setNomColumna(rs.getString("nombre"));        
        beanEstDocEntr.setValColumna(rs.getString("cantidad"));
        return beanEstDocEntr;
    }
}