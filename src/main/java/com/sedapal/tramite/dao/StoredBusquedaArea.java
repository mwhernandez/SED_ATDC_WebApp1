package com.sedapal.tramite.dao;

import com.sedapal.tramite.beans.AreaBean;
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

public class StoredBusquedaArea extends StoredProcedure implements RowMapper {

    private static final String SPROC_NAME = "ATDC_PKG_BUSQUEDAS.ATDC_BUSQUEDA_AREA";
    private static final String OPCION = "v_optFiltro";
    private static final String DETALLE = "v_txtFiltro";

    public StoredBusquedaArea() {
        // TODO Auto-generated constructor stub
    }

    public StoredBusquedaArea(DataSource dataSource) {
        super(dataSource, SPROC_NAME);
        declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
        declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));
        declareParameter(new SqlOutParameter("resultList", OracleTypes.CURSOR, this));
        compile();
    }

    public Map execute(String optFiltro, String txtFiltro) {
        System.out.println("Execute Stored..." + optFiltro + "   " + txtFiltro);
        Map inputs = new HashMap();
        inputs.put(OPCION, optFiltro);
        inputs.put(DETALLE, txtFiltro);
        return super.execute(inputs);
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        AreaBean beanArea = new AreaBean();
        beanArea.setCodigo(rs.getInt("CODIGO"));
        beanArea.setNombre(rs.getString("NOMBRE"));
        return beanArea;
    }
}