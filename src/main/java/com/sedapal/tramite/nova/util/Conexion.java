package com.sedapal.tramite.nova.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	static
    {
    	try {
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//Cambiar para SQL-MX
			Class.forName("oracle.jdbc.driver.OracleDriver");//Cambiar para SQL-MX
			//com.microsoft.sqlserver.jdbc.SQLServerDriver
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
    }
    
    public static Connection getConnection() throws SQLException
    {
       Connection conn = null;

 	   // constantes para utlizar en la cadena de conexion
 	   String url = "jdbc:oracle://";
 	   String servidor= "localhost";
 	   String puerto = "8095";
 	   String db= "DBAPLIC2";
 	   String user = "s_guia";
 	   String pass = "s_guia";
 	  
 	  String Url = ""+url+servidor+":"+puerto+";databaseName="+db+";";

    	try{
    	conn = DriverManager.getConnection(Url,user,pass);//cambiar para SQL-MX
    	 if(conn!=null) System.out.println("Conexion con" + db + " creada con exito.");

    	}
    	 catch(Exception e){
             e.printStackTrace();
             System.out.println("Error de seguimiento en getConnection() : " + e.getMessage());
         }

    	return conn;
    	
    }

}
