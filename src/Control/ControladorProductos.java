
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

public class ControladorProductos {
    Conectar cn = new Conectar();
    public  Connection con = cn.getConection();
    
    
    public void llenarTablaProductos(JTable tabla) {
        
        DefaultTableModel modelo;
        String Cabecera[] = {"ID Producto", "Descripcion", "Precio"};
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
            String idProducto;
            String descripcionProducto;
            String precioProducto;
            
            String sql = "SELECT * FROM citas.producto ORDER BY idproducto ASC";

            try (
                Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    idProducto = rs.getString("idproducto");
                    descripcionProducto = rs.getString("descripcion");
                    precioProducto = rs.getString("precioMetro");
                   
                    Object datos[] = {idProducto, descripcionProducto,precioProducto};
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
    
     public String AgregarNuevoProducto(int id, String desccripcion, int precio){
        String resultado ="";
        
        String query = "INSERT INTO citas.producto VALUES ("+id+",'"+desccripcion+"', '"+precio+"')";
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
    
    public String DeleteProducto(int id){
        String resultado ="";
        
        String query = "DELETE FROM citas.producto WHERE idproducto = "+id;
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
    
    public String UpdateProdcuto(int id, String descripcion, int precio){
        String resultado ="";
        
        String query = "UPDATE citas.producto SET descripcion='"+descripcion+"', precioMetro = '"+precio+"' WHERE idproducto="+id+"";
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
