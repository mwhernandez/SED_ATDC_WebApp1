package com.sedapal.tramite.nova.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class VisualizaReporte {
	public VisualizaReporte() throws SQLException{
		LlenarReporte();
		}

		public void LlenarReporte() throws SQLException {

		try{
		//Pasamos parametros al reporte Jasper.
		Map parameters = new HashMap();
		parameters.put("sql_query", new String("SELECT NCODPERFIL CODIGO,VDESCRIPCION NOMBRE,decode(VESTADO, 'A', 'ACTIVO','I', 'INACTIVO') AS ESTADO FROM PERFIL_SISTEMA WHERE NCODSISTEMA = 30 ORDER BY CODIGO"));
		//Preparacion del reporte (en esta etapa se inserta el valor del query en el reporte).
		conectarDB conec =new conectarDB();
		//D:\reporte
		JasperPrint reporte = JasperFillManager.fillReport("D:\\reporte\\Perfiles.jasper", parameters,conec.getConnection());
		//Finalmente visualizamos el reporte.
		JasperViewer.viewReport(reporte);
		conec.closeConexion(); 
		}catch(JRException ex){
		System.err.println( ex.getLocalizedMessage());
		}
		}

		public static void main(String args[]) throws SQLException{
		VisualizaReporte ver= new VisualizaReporte();
		} 


}
