package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexion {

    public static Connection conn;
    Properties propiedades = new Properties();

    InputStream data;

    private static String  driver;
    private static  String user;
    private static  String pass;
    private static  String db;
    private static  String server;
    private static  String puerto;
    //true en useSSL 
    private static String uni;
    private final String unicode = "useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8&";
    private final String time = "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String url;

    public Conexion() {
        conn = null;
      
        try {
             data = new FileInputStream("src/com/connection/conexion.properties");
            try {
                propiedades.load(data);
            } catch (IOException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            driver = propiedades.getProperty("driver");
            user = propiedades.getProperty("user");
            pass = propiedades.getProperty("pass");
            db = propiedades.getProperty("db");
            server = propiedades.getProperty("server");
            puerto = propiedades.getProperty("puerto");
            uni = "&autoReconnect=true&useSSL=false";
            url = "jdbc:mysql://" + server + ":3306/" + db + time + uni;
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            // Connect?
            if (conn != null) {
                System.out.println("Conexión establecida exitosamente");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Conexión Fallida:\n\n" + ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public Connection getConnection() {
        return conn;
    }

    public void cerrar() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
   public static void main(String[] args)  {
             Conexion conexion2 = new Conexion();
        conexion2.getConnection();
   }
}
