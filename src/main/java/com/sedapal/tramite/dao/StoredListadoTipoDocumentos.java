package com.sedapal.tramite.dao;

import com.sedapal.tramite.beans.TiposDocumentosBean;
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

public class StoredListadoTipoDocumentos extends StoredProcedure implements RowMapper {

    private static final String SPROC_NAME = "ATDC_PKG_MAINT.ATDC_LISTADO_TIPO_DOCUMENTO";
    private static final String AREA       = "v_codArea";
    private static final String TIPLISTADO = "v_tipListado";
    private static final String OPTFILTRO  = "v_optFiltro";
    private static final String TXTFILTRO  = "v_txtFiltro";

    public StoredListadoTipoDocumentos() {
        // TODO Auto-generated constructor stub
    }

    public StoredListadoTipoDocumentos(DataSource dataSource) {
        super(dataSource, SPROC_NAME);        
        declareParameter(new SqlParameter(AREA, Types.VARCHAR));
        declareParameter(new SqlParameter(TIPLISTADO, Types.VARCHAR));
        declareParameter(new SqlParameter(OPTFILTRO, Types.VARCHAR));
        declareParameter(new SqlParameter(TXTFILTRO, Types.VARCHAR));
        declareParameter(new SqlOutParameter("resultList", OracleTypes.CURSOR, this));
        compile();
    }

    public Map execute(String codArea, String tipListado, String optFiltro, String txtFiltro) {
        System.out.println("Execute Stored..."+ codArea + " " + tipListado + " " + optFiltro + " " + txtFiltro);
        Map inputs = new HashMap();        
        inputs.put(AREA, codArea);
        inputs.put(TIPLISTADO, tipListado);
        inputs.put(OPTFILTRO, optFiltro);
        inputs.put(TXTFILTRO, txtFiltro);
        return super.execute(inputs);
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        TiposDocumentosBean beanTiposDoc = new TiposDocumentosBean();
        beanTiposDoc.setCodigo(rs.getString("CODTIPO"));
        beanTiposDoc.setDescripcion(rs.getString("DESCRIPCION"));
        return beanTiposDoc;
    }
}