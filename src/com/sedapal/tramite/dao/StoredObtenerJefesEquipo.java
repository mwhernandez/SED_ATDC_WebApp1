package com.sedapal.tramite.dao;

import com.sedapal.tramite.beans.admin.JefeEquipoBean;
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

public class StoredObtenerJefesEquipo extends StoredProcedure implements RowMapper {

    private static final String SPROC_NAME = "ATDC_PKG_MAINT.ATDC_OBTENER_JEFE_EQUIPO_AREA";
    private static final String CODAREA    = "v_codArea";

    public StoredObtenerJefesEquipo() {
        // TODO Auto-generated constructor stub
    }

    public StoredObtenerJefesEquipo(DataSource dataSource) {
        super(dataSource, SPROC_NAME);        
        declareParameter(new SqlParameter(CODAREA, Types.VARCHAR));
        declareParameter(new SqlOutParameter("v_codFicha", Types.VARCHAR, this));
        declareParameter(new SqlOutParameter("v_apePaterno", Types.VARCHAR, this));
        declareParameter(new SqlOutParameter("v_apeMaterno", Types.VARCHAR, this));
        declareParameter(new SqlOutParameter("v_nombres", Types.VARCHAR, this));
        compile();
    }

    public Map execute(String codArea) {
        System.out.println("Execute Stored..." + codArea);
        Map inputs = new HashMap();        
        inputs.put(CODAREA, codArea);
        return super.execute(inputs);
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        JefeEquipoBean beanJE = new JefeEquipoBean();
        beanJE.setCodFicha(rs.getString("v_codficha"));
        beanJE.setApePaterno(rs.getString("v_apepaterno"));
        beanJE.setApeMaterno(rs.getString("v_apematerno"));
        beanJE.setNombres(rs.getString("v_nombres"));
        return beanJE;
    }
}