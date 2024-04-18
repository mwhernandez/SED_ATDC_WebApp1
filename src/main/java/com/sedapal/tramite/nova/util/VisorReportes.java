package com.sedapal.tramite.nova.util;

import java.io.FileInputStream;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;

import java.sql.Connection;

public class VisorReportes {
	private String rutaBase;
    private String rutaFisica;
    private ServletContext contexto;
    private FacesContext contextoJSF;
    private String nombreArchivo;
    private boolean descarga;
    //private EstrategiaExportacion estrategia;

    public VisorReportes(String rutaBase) {
        contextoJSF = FacesContext.getCurrentInstance();
        contexto = (ServletContext) contextoJSF.getExternalContext().getContext();
        this.rutaBase = rutaBase;
        rutaFisica = contexto.getRealPath(rutaBase)+"\\";
    }

    

}
