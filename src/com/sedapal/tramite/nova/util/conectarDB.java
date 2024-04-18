package com.sedapal.tramite.nova.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conectarDB {

    static Connection conn;

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        String driver = "oracle.jdbc.driver.OracleDriver"; //usamos el driver seg�n el tipo de base de datos
        String connectString = "jdbc:oracle:thin:@1.36.1.147:2004:DBAPLIC2";
        String url = "jdbc:oracle:thin:@1.36.1.147:2004:DBAPLIC2";
        String user = "s_guia";
        String password = "s_guia";
        //Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);
        //.getConnection(connectString,user,password);
        //Retornamos la conexi�n establecida.
        return conn;
    }
// Y UN M�TODO para cerrar la conexi�n

    public void closeConexion() {
        try {
            conn.close();
        } catch (SQLException onConClose) {
            System.out.println("error on closing");
            onConClose.printStackTrace();
        }
    }
}
