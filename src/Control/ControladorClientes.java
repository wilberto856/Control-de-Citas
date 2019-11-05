
package Control;

import Modelo.Conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorClientes {
    
    Conectar cn = new Conectar();
    public  Connection con = cn.getConection();
    
    
    public void llenatablaClientes(JTable tabla) {
        
        DefaultTableModel modelo;
        String Cabecera[] = {"ID Cliente", "Nombre", "Direccion"};
        String Datos[][] = {};
        modelo = new DefaultTableModel(Datos, Cabecera) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla.setModel(modelo);
        tabla.getColumnModel().getColumn(0).setMaxWidth(60);
        tabla.getColumnModel().getColumn(0).setMinWidth(60);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);

        try {
            String idCliente;
            String nombreCliente;
            String direccionCliente;
            
            String sql = "SELECT * FROM citas.cliente ORDER BY idCliente ASC";

            try (
                Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    idCliente = rs.getString("idcliente");
                    nombreCliente = rs.getString("nombre");
                    direccionCliente = rs.getString("direccion");
                   
                    Object datos[] = {idCliente, nombreCliente,direccionCliente};
                    modelo.addRow(datos);
                }
                tabla.setModel(modelo);
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error en cargar datos desde DB");
        }
        
    }
    
     public String AgregarNuevoCliente(int id, String nombre, String direccion){
        String resultado ="";
        
        String query = "INSERT INTO citas.cliente VALUES ("+id+",'"+nombre+"', '"+direccion+"')";
        try {
            Statement st= con.createStatement();
            if(st.executeUpdate(query)>0){
                resultado = "ok";
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
    }
    
    public String DeleteCliente(int id){
        String resultado ="";
        
        String query = "DELETE FROM citas.cliente WHERE idCliente = "+id;
        try {
            Statement st= con.createStatement();
            if(st.executeUpdate(query)>0){
                resultado = "ok";
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
    }
    
    public String UpdtateCliente(int id, String nombre, String direccion){
        String resultado ="";
        
        String query = "UPDATE citas.cliente SET nombre='"+nombre+"', direccion = '"+direccion+"' WHERE ="+id+"";
        try {
            Statement st= con.createStatement();
            if(st.executeUpdate(query)>0){
                resultado = "ok";
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
    }
}
