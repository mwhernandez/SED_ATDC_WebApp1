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
import com.sedapal.tramite.beans.admin.SecuencialAdmBean;

public class StoredListadoSecuenciales extends StoredProcedure implements RowMapper {

    private static final String SPROC_NAME = "ATDC_PKG_MAINT.ATDC_LISTADO_SECUENCIALES";
    private static final String ANNIO   = "v_annio";
    private static final String OPCION  = "v_optFiltro";
    private static final String DETALLE = "v_txtFiltro";

    public StoredListadoSecuenciales() {
        // TODO Auto-generated constructor stub
    }

    public StoredListadoSecuenciales(DataSource dataSource) {
        super(dataSource, SPROC_NAME);
        declareParameter(new SqlParameter(ANNIO, Types.VARCHAR));
        declareParameter(new SqlParameter(OPCION, Types.VARCHAR));
        declareParameter(new SqlParameter(DETALLE, Types.VARCHAR));
        declareParameter(new SqlOutParameter("resultList", OracleTypes.CURSOR, this));
        compile();
    }

    public Map execute(String annio, String optFiltro, String txtFiltro) {
        System.out.println("Execute Stored..."+ annio + " " + optFiltro + " " + txtFiltro);
        Map inputs = new HashMap();
        inputs.put(ANNIO, annio);
        inputs.put(OPCION, optFiltro);
        inputs.put(DETALLE, txtFiltro);
        return super.execute(inputs);
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        SecuencialAdmBean beanSecuencial = new SecuencialAdmBean();
        beanSecuencial.setCodTipoDocumento(rs.getString("CODIGO"));
        beanSecuencial.setDescTipoDocumento(rs.getString("DESCRIPCION"));
        beanSecuencial.setOrdenCombo(rs.getString("VALCOMBO"));
        beanSecuencial.setCodArea(rs.getString("CODAREA"));
        beanSecuencial.setDescArea(rs.getString("DESCAREA"));
        beanSecuencial.setValorSecuencial(rs.getString("VALORSECUENCIAL"));
        beanSecuencial.setEstado(rs.getString("ESTADO"));
        beanSecuencial.setDescEstado(rs.getString("DESCESTADO"));
        return beanSecuencial;
    }
}