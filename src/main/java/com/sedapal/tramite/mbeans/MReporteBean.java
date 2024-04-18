package com.sedapal.tramite.mbeans;

import java.io.FileInputStream;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.lowagie.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;


import java.sql.Connection;

public class MReporteBean {
	@Resource(name = "sample")
    private DataSource sample;

    /** Creates a new instance of ReportesBean 
     * @return */	
    public void ReportesBean() {
    }

    public String visualizarReporte(){
        FacesContext contextoJSF = FacesContext.getCurrentInstance();
        ServletContext context = (ServletContext)
                contextoJSF.getExternalContext().getContext();

        HttpServletResponse response = (HttpServletResponse)
                contextoJSF.getExternalContext().getResponse();

        HttpServletRequest request = (HttpServletRequest)
                contextoJSF.getExternalContext().getRequest();

        String rutaFisica = context.getRealPath("\\WEB-INF\\reportes\\perfiles.jasper");
        //String rutaFisica = context.getRealPath("\\WEB-INF\\reportes\\report3.jasper");
        FileInputStream fis = null;
        ServletOutputStream out = null;
        Connection conn = null;
        JasperPrint jp = null;
        JRExporter exporter = null;
        try{
            fis = new FileInputStream(rutaFisica);
            //conn = sample.getConnection();
            conn = (Connection) sample.getConnection();
            jp = JasperFillManager.fillReport(fis, new HashMap(), conn);
            out = response.getOutputStream();
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment;filename=clientes.pdf");
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            // para permitir la impresion esta bloqueado y no deja hacer nada
            exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, true);
            exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, true);
            exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, "adobe");
            exporter.setParameter(JRPdfExporterParameter.IS_COMPRESSED, true);
            exporter.setParameter(JRPdfExporterParameter.METADATA_AUTHOR, "Eli Diaz Horna");
            // deja imprimir nada mas
            //exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, PdfWriter.ALLOW_PRINTING);
            // deja imprimir y grabar
            exporter.setParameter(JRPdfExporterParameter.PERMISSIONS,
                    new Integer(PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY));
            exporter.exportReport();


        }catch (Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al cargar el reporte", e.getMessage());
            contextoJSF.addMessage(null, msg);
        }
        contextoJSF.responseComplete();
        return null;
    }
        
}
