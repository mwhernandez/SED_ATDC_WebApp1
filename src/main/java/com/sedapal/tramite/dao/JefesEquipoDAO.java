package com.sedapal.tramite.dao;

import com.sedapal.tramite.beans.admin.JefeEquipoBean;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

public class JefesEquipoDAO implements IJefesEquipoDAO {

    //Agregado el 11/01/2012 - A. Panitz
    // ***** Clase Implementada para el Mantenimiento de Jefes de Equipo ***** //    
    private JdbcTemplate jdbcTemplate;
    private StoredActualizarJefesEquipo spActualizarJefesEquipo;
    private StoredObtenerJefesEquipo spObtenerJefesEquipo;

    public JefeEquipoBean getJefeEquipoArea(String codArea) {
        Map output = getSpObtenerJefesEquipo().execute(codArea);
        JefeEquipoBean beanResult = new JefeEquipoBean();
        beanResult.setCodFicha((String)output.get("v_codFicha"));
        beanResult.setApePaterno((String)output.get("v_apePaterno"));
        beanResult.setApeMaterno((String)output.get("v_apeMaterno"));
        beanResult.setNombres((String)output.get("v_nombres"));
        return beanResult;
    }

    public Map updateJefeEquipoArea(String codArea, String codFicha, String apePaterno, String apeMaterno, String nombres, String usuResponsable) {
        Map output = getSpActualizarJefesEquipo().execute(codArea, codFicha, apePaterno, apeMaterno, nombres, usuResponsable);
        return output;
    }

    /*****************************/
    /***** Metodos de Acceso *****/
    /*****************************/
    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return the spActualizarJefesEquipo
     */
    public StoredActualizarJefesEquipo getSpActualizarJefesEquipo() {
        return spActualizarJefesEquipo;
    }

    /**
     * @param spActualizarJefesEquipo the spActualizarJefesEquipo to set
     */
    public void setSpActualizarJefesEquipo(StoredActualizarJefesEquipo spActualizarJefesEquipo) {
        this.spActualizarJefesEquipo = spActualizarJefesEquipo;
    }

    /**
     * @return the spObtenerJefesEquipo
     */
    public StoredObtenerJefesEquipo getSpObtenerJefesEquipo() {
        return spObtenerJefesEquipo;
    }

    /**
     * @param spObtenerJefesEquipo the spObtenerJefesEquipo to set
     */
    public void setSpObtenerJefesEquipo(StoredObtenerJefesEquipo spObtenerJefesEquipo) {
        this.spObtenerJefesEquipo = spObtenerJefesEquipo;
    }

    

}
