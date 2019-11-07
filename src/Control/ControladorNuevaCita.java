package Control;

import Modelo.Conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorNuevaCita {

    Conectar cn = new Conectar();
    public Connection con = cn.getConection();

    public void llenarTablaCita(JTable tabla) {

        DefaultTableModel modelo;
        String Cabecera[] = {"ID Cita", "Fecha Inicio", "Fecha Fin"};
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
            String idCita;
            String fechainicio;
            String fechafin;

            String sql = "SELECT idcita, TO_CHAR( fechainicio, 'FMMonth DD, YYYY' )  fechainicio, TO_CHAR( fechainicio, 'FMMonth DD, YYYY' ) fechafin from citas.cita order by idcita ASC";

            try (
                    Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    idCita = rs.getString("idcita");
                    fechainicio = rs.getString("fechainicio");
                    fechafin = rs.getString("fechafin");

                    Object datos[] = {idCita, fechainicio, fechafin};
                    modelo.addRow(datos);
                }
                tabla.setModel(modelo);
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error en cargar datos desde DB" + ex);
        }

    }

    public void llenacbxProducto(JComboBox jcbCombobox) {
        try {

            String sql = "SELECT descripcion FROM citas.producto";

            try (
                    Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {

                    jcbCombobox.addItem(rs.getString("descripcion"));
                }

                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error en cargar datos desde DB" + ex);
        }
    }

    public void llenacbxCliente(JComboBox jcbCombobox) {
        try {

            String sql = "SELECT nombre FROM citas.cliente";

            try (
                    Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {

                    jcbCombobox.addItem(rs.getString("nombre"));
                }

                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error en cargar datos desde DB" + ex);
        }
    }

    public void llenacbxGrupo(JComboBox jcbCombobox) {
        try {

            String sql = "SELECT nombregrupo FROM citas.grupos_tienda";

            try (
                    Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {

                    jcbCombobox.addItem(rs.getString("nombregrupo"));
                }

                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error en cargar datos desde DB" + ex);
        }
    }

    public void llenacbxtotal(JComboBox jcbCombobox) {
        try {

            String sql = "SELECT idpreciotienda FROM citas.cita_precio_tienda";

            try (
                    Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {

                    jcbCombobox.addItem(rs.getString("idpreciotienda"));
                }

                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error en cargar datos desde DB" + ex);
        }
    }

    public String agregarCita(int idcita, String fechainicio, String fechafin, String nombreProducto, String nombreCliente, String nombreGrupo, int total) {

        String resultado = "";

        String query = "INSERT INTO citas.cita values ("+idcita+",'"+fechainicio+"', '"+fechafin+"', "
                + "(select idproducto from citas.producto where descripcion='"+nombreProducto+"'),\n"
                + "(select idcliente from citas.cliente where nombre='"+nombreCliente+"'),"
                + "(select idgrupo from CITAS.grupos_tienda where nombregrupo='"+nombreGrupo+"'),"+total+")";
        try {
            Statement st = con.createStatement();
            if (st.executeUpdate(query) > 0) {
                resultado = "ok";
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorTiendas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
