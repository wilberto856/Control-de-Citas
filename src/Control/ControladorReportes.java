
package Control;

import Modelo.Conectar;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ControladorReportes {
    
    public void viewReport(int noReporte){
        Conectar cn = new Conectar();
        Connection con = cn.getConection();
        JasperReport rep= null;
        try {
            try {
                
                switch(noReporte){
                    case 1 :
                        rep = (JasperReport) JRLoader.loadObject("BitacoraCitas.jasper");
                        break;
                    case 2: 
                        rep = (JasperReport) JRLoader.loadObject("CitasPorCliente.jasper");
                        break;
                    case 3:
                        rep = (JasperReport) JRLoader.loadObject("CitasPorTienda.jasper");
                        break;
                    case 4:
                        rep = (JasperReport) JRLoader.loadObject("EmpleadosPorTienda.jasper");
                        break;
                    case 5:
                        rep = (JasperReport) JRLoader.loadObject("IngresosPorTienda.jasper");
                        break;                          
                  
                }
                
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
