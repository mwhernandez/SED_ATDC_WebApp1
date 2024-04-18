/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sedapal.tramite.nova.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ReportImpl implements IReport {


    private DataSource dataSource;

    public byte[] reporte(JasperReport jasperReport, Map parameters) throws JRException {

        Connection conn = null;
        JasperPrint jasperprint;
        byte[] pdfasbytes = null;
        try {
            //InitialContext initialContext = new InitialContext();
            //dataSource = (DataSource)initialContext.lookup("java:comp/env/jdbc/Tienda");
            conn = dataSource.getConnection();
            jasperprint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            pdfasbytes = JasperExportManager.exportReportToPdf(jasperprint);
        
        } catch (SQLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return pdfasbytes;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

