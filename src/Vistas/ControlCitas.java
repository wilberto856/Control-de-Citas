
package Vistas;

import Modelo.Conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControlCitas {

    public static void main(String[] args) {
        Conectar con = new Conectar();
        Connection cn = con.getConection();
        
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("select * from citas.empleado");
            while(rs.next())
                System.out.println(rs.getString(1)+" - "+ rs.getString(2));
        } catch (SQLException ex) {
            Logger.getLogger(ControlCitas.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            con.desconectar();
        }
        
        
    }
    
}
