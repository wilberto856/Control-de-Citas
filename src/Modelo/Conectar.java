
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conectar {
    
    private static Connection conn;
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    private static final String user = "system";
    private static final String password = "1234";
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    
    public Conectar() {
        conn =  null;
        
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user, password);
            if (conn != null) {
                System.out.println("Conexion exitosa");
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error Conectando"+e);
        }
    }
    
    
    public Connection getConection(){
        return conn;
    }
    
    
    public void desconectar(){
        conn = null;
        if(conn == null){
            System.out.println("Conexion terminada...");
        }
    }
}
