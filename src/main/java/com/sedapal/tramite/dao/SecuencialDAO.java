package com.sedapal.tramite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.sedapal.tramite.beans.SecuencialBean;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.beans.Usuario;
import com.sedapal.tramite.beans.admin.SecuencialAdmBean;

public class SecuencialDAO implements ISecuencialDAO{
	
	
	private static Logger logger = Logger.getLogger("R1");
	
	Calendar c = Calendar.getInstance();
	
	private JdbcTemplate jdbcTemplate;
	
	private StoredSecuenciaAdjunto storedSecuenciaAdjunto;
		
	
    //Agregado el 06/01/2012 - A. Panitz
    private StoredListadoSecuenciales storedListadoSecuenciales;
    private StoredListadoTipoDocumentos storedListadoTipoDocumentos;
    private StoredNuevoSecuencial storedNuevoSecuencial;
    private StoredValidacionSecuencial storedValidacionSecuencial;
    private StoredActualizarSecuencial storedActualizarSecuencial;
    private StoredEliminarSecuencial storedEliminarSecuencial;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List<SecuencialBean> secuencial() {
		// TODO Auto-generated method stub
		return null;
	}
		
	
	@Override
	public String correlativo()
	{
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuario = null;
		usuario=(Usuario)session.getAttribute("sUsuario");
		long nowIn =0, nowOut=0, nowDif =0;
		nowIn = System.currentTimeMillis();
		
		Map output = storedSecuenciaAdjunto.execute(usuario.getLogin());
		
		nowOut = System.currentTimeMillis();
		nowDif = nowOut - nowIn;
		logger.debug("["+usuario+"]"+"Stored S_GUIA.ATDC_SECUENCIAL_ADJUNTO Duracion:" + nowDif);
		String out = (String) output.get("out");
		return out;
	}
	
    //Agregado el 06/01/2012 - A. Panitz
    // ***** Metodos para el mantenimiento de Secuenciales ***** //
    public List<SecuencialAdmBean> getListadoSecuenciales(String annio, String optFiltro, String txtFiltro) {
        Map output = getStoredListadoSecuenciales().execute(annio, optFiltro, txtFiltro);
        List resultList = (ArrayList) output.get("resultList");
        return resultList;
    }
    
    public List<TiposDocumentosBean> getListadoTipoDocumentos(String codArea, String tipListado, String optFiltro, String txtFiltro) {
        Map output = getStoredListadoTipoDocumentos().execute(codArea, tipListado, optFiltro, txtFiltro);
        List resultList = (ArrayList) output.get("resultList");
        return resultList;
    }

    public Map insertSecuencial(String codArea, String annio, String codTipDoc, String usuResponsable) {
        Map output = getStoredNuevoSecuencial().execute(codArea, annio, codTipDoc, usuResponsable);
        return output;
    }

    public Map validarSecuencial(String secProp, String codArea, String annio, String codTipDoc) {
        Map output = getStoredValidacionSecuencial().execute(secProp, codArea, annio, codTipDoc);
        return output;
    }

    public Map updateSecuencial(String codArea, String annio, String codTipDoc, String secuencial, String estado) {
        Map output = getStoredActualizarSecuencial().execute(codArea, annio, codTipDoc, secuencial, estado);
        return output;
    }

    public Map deleteSecuencial(String codArea, String annio, String codTipDoc) {
        Map output = getStoredEliminarSecuencial().execute(codArea, annio, codTipDoc);
        return output;
    }

    /******* Metodos de Acceso *******/
	
    public void setStoredSecuenciaAdjunto(StoredSecuenciaAdjunto storedSecuenciaAdjunto) {
        this.storedSecuenciaAdjunto = storedSecuenciaAdjunto;
    }

    /**
     * @return the storedListadoSecuenciales
     */
    public StoredListadoSecuenciales getStoredListadoSecuenciales() {
        return storedListadoSecuenciales;
    }

    /**
     * @param storedListadoSecuenciales the storedListadoSecuenciales to set
     */
    public void setStoredListadoSecuenciales(StoredListadoSecuenciales storedListadoSecuenciales) {
        this.storedListadoSecuenciales = storedListadoSecuenciales;
    }

    /**
     * @return the storedListadoTipoDocumentos
     */
    public StoredListadoTipoDocumentos getStoredListadoTipoDocumentos() {
        return storedListadoTipoDocumentos;
    }

    /**
     * @param storedListadoTipoDocumentos the storedListadoTipoDocumentos to set
     */
    public void setStoredListadoTipoDocumentos(StoredListadoTipoDocumentos storedListadoTipoDocumentos) {
        this.storedListadoTipoDocumentos = storedListadoTipoDocumentos;
    }

    /**
     * @return the storedNuevoSecuencial
     */
    public StoredNuevoSecuencial getStoredNuevoSecuencial() {
        return storedNuevoSecuencial;
    }

    /**
     * @param storedNuevoSecuencial the storedNuevoSecuencial to set
     */
    public void setStoredNuevoSecuencial(StoredNuevoSecuencial storedNuevoSecuencial) {
        this.storedNuevoSecuencial = storedNuevoSecuencial;
    }

    /**
     * @return the storedValidacionSecuencial
     */
    public StoredValidacionSecuencial getStoredValidacionSecuencial() {
        return storedValidacionSecuencial;
    }

    /**
     * @param storedValidacionSecuencial the storedValidacionSecuencial to set
     */
    public void setStoredValidacionSecuencial(StoredValidacionSecuencial storedValidacionSecuencial) {
        this.storedValidacionSecuencial = storedValidacionSecuencial;
    }

    /**
     * @return the storedActualizarSecuencial
     */
    public StoredActualizarSecuencial getStoredActualizarSecuencial() {
        return storedActualizarSecuencial;
    }

    /**
     * @param storedActualizarSecuencial the storedActualizarSecuencial to set
     */
    public void setStoredActualizarSecuencial(StoredActualizarSecuencial storedActualizarSecuencial) {
        this.storedActualizarSecuencial = storedActualizarSecuencial;
    }

    /**
     * @return the storedEliminarSecuencial

     */
    public StoredEliminarSecuencial getStoredEliminarSecuencial() {
        return storedEliminarSecuencial;
    }

    /**
     * @param storedEliminarSecuencial the storedEliminarSecuencial to set
     */
    public void setStoredEliminarSecuencial(StoredEliminarSecuencial storedEliminarSecuencial) {
        this.storedEliminarSecuencial = storedEliminarSecuencial;
    }
}
