
package Control;

import Modelo.Conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
