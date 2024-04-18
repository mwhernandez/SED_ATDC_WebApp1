package com.sedapal.tramite.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sedapal.tramite.beans.AreaBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.dao.IAreaDAO;

public class AreaDAO implements IAreaDAO{
	
	private JdbcTemplate jdbcTemplate;
	private StoredAreas StoredAreas;
	private static Logger logger = Logger.getLogger("R1");	
    private StoredBusquedaArea storedBusquedaArea;	
		
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	
	
	@Override
	public List<AreaBean> areasSP() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();		
		Map output = StoredAreas.execute();
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored ATDC_AREAS Duracion:" + nowDif);
		List areas = (ArrayList)output.get("areas");
		return areas;
	}

	public void setStoredAreas(StoredAreas storedAreas) {
		this.StoredAreas = storedAreas;
	}
	
	@Override
	public void nuevoSP(AreaBean areaBean) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<AreaBean> areas() {
		// TODO Auto-generated method stub
		return null;
	}

//Agregado el 10/10/2011 - A. Panitz
    // ***** Metodo para la busqueda de Areas ***** //
    public List<AreaBean> busquedaArea(String optFiltro, String txtFiltro) {
        Map output = getStoredBusquedaArea().execute(optFiltro, txtFiltro);
        List resultList = (ArrayList) output.get("resultList");
        return resultList;
    }

    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @return the StoredAreas
     */
    public StoredAreas getStoredAreas() {
        return StoredAreas;
    }

    /**
     * @return the storedBusquedaArea
     */
    public StoredBusquedaArea getStoredBusquedaArea() {
        return storedBusquedaArea;
    }

    /**
     * @param storedBusquedaArea the storedBusquedaArea to set
     */
    public void setStoredBusquedaArea(StoredBusquedaArea storedBusquedaArea) {
        this.storedBusquedaArea = storedBusquedaArea;
    }
    
    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * @param aLogger the logger to set
     */
    public static void setLogger(Logger aLogger) {
        logger = aLogger;
    }
}
