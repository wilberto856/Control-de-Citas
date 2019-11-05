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

public class ControladorEmpleados {

    Conectar cn = new Conectar();
    public Connection con = cn.getConection();

    public void llenarTablaEmpleados(JTable tabla) {

        DefaultTableModel modelo;
        String Cabecera[] = {"ID Empleado", "Nombre", "Grupo Asignado"};
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
            String idEmpleado;
            String nombre;
            String grupoEmpleado;

            String sql = "SELECT e.idempleado, e.nombre, gt.nombregrupo FROM citas.empleado e JOIN citas.grupo_empleado ge on e.idempleado = ge.idempleado JOIN citas.grupos_tienda gt on ge.idgrupo = gt.idgrupo ORDER BY idempleado ASC";

            try (
                    Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    idEmpleado = rs.getString("idempleado");
                    nombre = rs.getString("nombre");
                    grupoEmpleado = rs.getString("nombregrupo");

                    Object datos[] = {idEmpleado, nombre, grupoEmpleado};
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

    public void llenaTiendasCbx(JComboBox jcbxTiendas) {
        try {

            String sql = "SELECT nombre FROM citas.tienda";

            try (
                    Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    jcbxTiendas.addItem(rs.getString("nombre"));
                }

                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error en cargar datos desde DB" + ex);
        }
    }

    public void llenaGrupoCbx(JComboBox jcbxGrupos, String tienda) {
        try {

            String sql = "SELECT gt.nombregrupo FROM citas.grupos_tienda gt JOIN citas.tienda t on t.idtienda = gt.Tienda_idtienda WHERE t.nombre = '" + tienda + "'";

            try (
                    Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    jcbxGrupos.addItem(rs.getString("nombregrupo"));
                }

                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error en cargar datos desde DB" + ex);
        }
    }

    public String AgregarNuevoEmpleado(int id, String nombre) {

        String resultado = "";

        String query = "INSERT INTO citas.empleado VALUES (" + id + ",'" + nombre + "')";
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

    public String insertaGrupoEmpleado(String tienda, String grupo) {
        String resultado = "";

        String query = "INSERT INTO citas.grupo_empleado VALUES((SELECT max(idgrupoempleado+1) idgrupoempleado from citas.grupo_empleado), (SELECT max(idempleado) idempleado from citas.empleado), (SELECT gt.idgrupo idgrupo FROM citas.GRUPOS_TIENDA GT JOIN citas.TIENDA T ON GT.TIENDA_IDTIENDA=T.IDTIENDA WHERE T.NOMBRE = '"+tienda+"' AND GT.NOMBREGRUPO='"+grupo+"')"
                + ")";
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

    public String DeleteTienda(int id) {
        String resultado = "";

        String query = "DELETE FROM citas.tienda WHERE idTienda = " + id;
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

    public String UpdateTienda(int id, String nombre) {
        String resultado = "";

        String query = "UPDATE citas.tienda SET nombre='" + nombre + "' WHERE idTienda=" + id + "";
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
