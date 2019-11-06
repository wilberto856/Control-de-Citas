package Vistas;

import Modelo.Conectar;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ControlCitas {

    public static void main(String[] args) throws JRException {
        Conectar cn = new Conectar();
        Connection con = cn.getConection();

        try {
            try {
                JasperReport rep = (JasperReport) JRLoader.loadObject("EmpleadosPorTienda.jasper");
                JasperPrint j = JasperFillManager.fillReport(rep, null, con);
                JasperViewer jv = new JasperViewer(j, false);
                jv.setVisible(true);

            } catch (JRException e) {
                JOptionPane.showMessageDialog(null, "Error al Imprimir el ticket" + e);
            }
            con.close();
        } catch (SQLException ex) {
            
        }
    }

}
