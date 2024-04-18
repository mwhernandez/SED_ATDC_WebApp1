package com.sedapal.tramite.nova.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.sedapal.tramite.beans.SeguimientoEntranteBean;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;



public class GenerarReportesNuevo {
	private Map<String , Object> parametros;
	
	private DataSource dataSource=null;
	private String nombreReporteJasper;
	private static Logger logger = Logger.getLogger("R1");
	private List<SeguimientoEntranteBean> seguimientoDoc;
	 
    public  byte[] generarReporte() {
        byte[] result = null;
        logger.debug("[GenerarReportes.generarReporte]3) Inicio Generando");
        JasperPrint reportPrint = generateJasperPrint();
        System.out.println(reportPrint);

        try {

            result = JasperExportManager.exportReportToPdf(reportPrint);
        } catch (Exception ex) {
            logger.error("[GenerarReporte.generarReporte]", ex);
            ex.printStackTrace();
        }
        return result;

    }

    protected  JasperPrint generateJasperPrint() {
    	logger.debug("[GenerarReportes.generateJasperPrint]4)Inicio JasperPrint");
        JasperPrint result = null;
        InputStream xmlDesign = getXMLDesign();
        JasperReport reporte = null;
        Connection connection =null;
        try {
		
			//Paramters puts

        	logger.debug("[GenerarReportes.generateJasperPrint]7)Cargando Objeto");
            reporte = (JasperReport) JRLoader.loadObject(xmlDesign);
            logger.debug("[GenerarReporte.generateJasperPrint]8)Cargado ok.");
    //        Map<String, Object> parameters = new HashMap<String, Object>();
    //        parameters.put("P_LIMITE", "100");
    //        parameters.put("P_TITULO", "BCP");
            logger.debug("[GenerarReportes.generateJasperPrint]9)Obteniendo Conexion jdbc.");
            connection= dataSource.getConnection();         
            logger.debug("[GenerarReportes.generateJasperPrint]10)Conexion ok.");
            System.out.println("Veo Datos de las Genera Reporte");
            System.out.println(reporte);
            System.out.println(parametros);
            System.out.println(connection);
            if(seguimientoDoc==null)              
             result = JasperFillManager.fillReport(reporte, parametros, connection);             
            else
             result = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(seguimientoDoc));
            

        } catch (Exception ex) {
        	logger.error("[GenerarReportes.generateJasperPrint]",ex);
            ex.printStackTrace();
        }
        finally{
        	if(connection!=null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return result;
    }

    public  InputStream getXMLDesign() {
        InputStream result = null;
        logger.debug("[GenerarReportes.getXMLDesign] 5) Obteniendo Recurso Jasper");
        try {
            result = GenerarReportesNuevo.class.getResourceAsStream(this.nombreReporteJasper);
            logger.debug("[GenerarReportes.getXMLDesign] 6) Recurso Obtenido:"+ result);
        	//result = GenerarReporte.class.getResourceAsStream("doc_salidas.jasper");
        	//result = GenerarReporte.class.getResourceAsStream("prueba.jasper");

        } catch (Exception ex) {
            logger.error("[GenerarReportes.getXMLDesign]",ex);
            ex.printStackTrace();
        }
        return result;
    }

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public String getNombreReporteJasper() {
		return nombreReporteJasper;
	}

	public void setNombreReporteJasper(String nombreReporteJasper) {
		this.nombreReporteJasper = nombreReporteJasper;
	}

	public List<SeguimientoEntranteBean> getSeguimientoDoc() {
		return seguimientoDoc;
	}

	public void setSeguimientoDoc(List<SeguimientoEntranteBean> seguimientoDoc) {
		this.seguimientoDoc = seguimientoDoc;
	}
	
	
    
}
