
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


public class ControladorTiendas {
    
    Conectar cn = new Conectar();
    public  Connection con = cn.getConection();
    
    
    
    public void llenatablaTiendas(JTable tabla) {
        
        DefaultTableModel modelo;
        String Cabecera[] = {"ID Tienda", "Nombre"};
        String Datos[][] = {};
        modelo = new DefaultTableModel(Datos, Cabecera) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla.setModel(modelo);
        tabla.getColumnModel().getColumn(0).setMaxWidth(120);
        tabla.getColumnModel().getColumn(0).setMinWidth(120);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(120);

        try {
            String idTienda;
            String nombre;
            
            String sql = "SELECT * FROM citas.tienda ORDER BY idtienda ASC";

            try (
                Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    idTienda = rs.getString("idtienda");
                    nombre = rs.getString("nombre");
                   
                    Object datos[] = {idTienda, nombre};
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
    
    public String AgregarNuevaTienda(int id, String nombre){
        String resultado ="";
        
        String query = "INSERT INTO citas.tienda VALUES ("+id+",'"+nombre+"')";
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
    
    public String DeleteTienda(int id){
        String resultado ="";
        
        String query = "DELETE FROM citas.tienda WHERE idTienda = "+id;
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
    
    public String UpdateTienda(int id, String nombre){
        String resultado ="";
        
        String query = "UPDATE citas.tienda SET nombre='"+nombre+"' WHERE idTienda="+id+"";
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
