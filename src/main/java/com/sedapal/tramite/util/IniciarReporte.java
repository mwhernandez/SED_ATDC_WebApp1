package com.sedapal.tramite.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;

public class IniciarReporte {
	Connection conn = null;

	public IniciarReporte() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver()");// se carga el
			// driver
			conn = (Connection) DriverManager.getConnection(
					"jdbc:oracle://localhost:8095/reporte", "s_guia", "s_guia");
			JOptionPane.showMessageDialog(null, "Conexion Establecida");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void ejecutarReporte()
	{		
		try{
			String archivo = "//reports/Perfil.jasper";
			System.out.print("Cargando desde:"+ archivo);
			if(archivo==null)
			{
				System.out.println("No se encuentra el archivo");
				System.exit(2);
			}
			JasperReport masterReport = null;

		
		}
		catch(Exception j)
		{
			System.out.println("Mensaje de Errors"+j.getMessage());
		}
			
	}

	// Cerrar la conexion
	public void cerrar() {
		try {
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
