package com.sedapal.tramite.nova.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.icesoft.faces.context.Resource;
import com.sedapal.tramite.beans.SeguimientoEntranteBean;
public class RecursoReporteNuevo implements Resource, Serializable {
	 
    private String sURL;
    private String resourceName;
    private String nombreReporteJasper;
    private Date lastModified;
    private ExternalContext extContext;
    GenerarReportesNuevo reportenuevo = null;
    List<SeguimientoEntranteBean> seguimientoDoc;
    private static Logger logger = Logger.getLogger("R1");

    public RecursoReporteNuevo() {
    	reportenuevo = new GenerarReportesNuevo();
    }
    
    public void asignar(String resourceName, ExternalContext extContext, Map<String, Object> parametros, String nombreReporteJasper) {
    	logger.debug("[RecursoReporte.asignar]1) Asignando");
    	//this.seguimientoDoc=null;
    	reportenuevo.setSeguimientoDoc(null);
        this.resourceName = resourceName;
        this.extContext = extContext;
        this.lastModified = new Date();
        reportenuevo.setParametros(parametros);
        reportenuevo.setNombreReporteJasper(nombreReporteJasper);
        reportenuevo.setSeguimientoDoc(null);
    }
    public void asignar(String resourceName, ExternalContext extContext, 
    		Map<String, Object> parametros, String nombreReporteJasper,
    		List<SeguimientoEntranteBean> seguimientoDoc) {
    	logger.debug("[RecursoReporte.asignar]1) Asignando");
        this.resourceName = resourceName;
        this.extContext = extContext;
        this.lastModified = new Date();
        reportenuevo.setParametros(parametros);
        reportenuevo.setNombreReporteJasper(nombreReporteJasper);
        reportenuevo.setSeguimientoDoc(seguimientoDoc);
        this.seguimientoDoc = seguimientoDoc;
    }

    public String calculateDigest() {
        return resourceName;
    }

    public InputStream open() throws IOException {
        try {
        	logger.debug("[RecursoReporte.asignar]2) openInputStrema");
            HttpServletRequest thisRequest = (HttpServletRequest) extContext.getRequest();
            sURL = "http://" + thisRequest.getServerName() + ":" + thisRequest.getServerPort() + extContext.getRequestContextPath();
            System.out.println(sURL);
            return new ByteArrayInputStream(reportenuevo.generarReporte());
        } catch (Exception ex) {
            logger.error("RecursoReporte.open]Error generando Stream:",ex);
            ex.printStackTrace();
        }
        return null;

    }

    public Date lastModified() {
        return lastModified;
    }


    
    public void withOptions(Options arg0) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

	public List<SeguimientoEntranteBean> getSeguimientoDoc() {
		return seguimientoDoc;
	}

	public void setSeguimientoDoc(List<SeguimientoEntranteBean> seguimientoDoc) {
		this.seguimientoDoc = seguimientoDoc;
	}

	

	public void setReportenuevo(GenerarReportesNuevo reportenuevo) {
		this.reportenuevo = reportenuevo;
	}

	public String getNombreReporteJasper() {
		return nombreReporteJasper;
	}

	public void setNombreReporteJasper(String nombreReporteJasper) {
		this.nombreReporteJasper = nombreReporteJasper;
	}
	
    
}