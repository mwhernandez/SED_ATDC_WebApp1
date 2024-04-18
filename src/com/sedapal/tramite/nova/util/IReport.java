/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sedapal.tramite.nova.util;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;

public interface IReport {
    public byte[]  reporte(JasperReport source, Map parameters) throws JRException;
}
