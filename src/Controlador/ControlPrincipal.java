package Controlador;

import Modelo.Automovil;
import Modelo.Cliente;
import Modelo.RegistroEmpleadoDB;
import Vistas.CRUD.VCrud;
import Vistas.CRUD.VCrudVehiculo;
import Vistas.Info;
import Vistas.Styles;
import Vistas.TextPrompt;
import Vistas.VCamara;
import Vistas.VLogin;
import Vistas.VPrincipal;
import Vistas.Validaciones;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javaanpr.gui.windows.FrameHelp;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import Modelo.ConexionDB;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import net.sf.jasperreports.engine.JRException;

public class ControlPrincipal {

    protected RegistroEmpleadoDB registro = new RegistroEmpleadoDB(0, null, null);
    protected Validaciones validaciones = new Validaciones();
    private ControlCrudVehiculos vehiculo;
    private VCrudVehiculo crudVehiculo;
    private ControlCamara controlc;
    private VPrincipal contenedor;
    private Automovil coneAuto;
    private ControlCrud ventana;
    private final VLogin vlogin;
    protected TextPrompt ph;
    private VCamara vcam;
    private Cliente cone;
    private Styles styles;
    private VCrud crud;
    private Info info;
    protected ConexionDB conDB = new ConexionDB();

    public ControlPrincipal(VLogin vlogin) {
        this.vlogin = vlogin;
        styles = new Styles(null);
        vlogin.setLocationRelativeTo(null);
        vlogin.setVisible(true);
        vlogin.setResizable(false);
        styles.JTextFieldFont(vlogin.getTxt_cedula(), 24);
        styles.JTextFieldFont(vlogin.getTxt_password(), 24);

        validaciones.createFilteredField(vlogin.getTxt_cedula(), 10, Validaciones.MATCH_NUMEROS);
        validaciones.createFilteredField(vlogin.getTxt_password(), 10, Validaciones.MATCH_PASSWORD);
        styles.setearIcoJLabel(vlogin.getLbl_cedula(), null, "icons8_user_40px");
        styles.setearIcoJLabel(vlogin.getLbl_password(), null, "icons8_show_password_35px");
        vlogin.getTxt_password().setEchoChar((char) '\u2022');
        vlogin.getBtn_iniciarsesion().addActionListener(action);
        vlogin.getTxt_password().addActionListener(action);//para ingresar dando a enter
        vlogin.getTxt_cedula().addActionListener(action);//para ingresar dando a enter
    }

    public void iniciarcontrol() {

        styles.setearIcoJFrame(vlogin, "Login", "pi_20px_1");
        styles.setearIcoJLabel(vlogin.getLbl_icono(), "Parking ISTA", "logo_ParkingISTA_200px");
        styles.JframePanelColor(vlogin);
        styles.JlabelFont(vlogin.getLbl_cedula(), 20);
        setearPH();
        vlogin.setVisible(true);
        styles.mouseListenerPassword(vlogin.getLbl_password(), vlogin.getTxt_password());
    }

    public void AbrirCrudVehiculo() {
        coneAuto = new Automovil();
        crudVehiculo = new VCrudVehiculo();
        vehiculo = new ControlCrudVehiculos(crudVehiculo, coneAuto);
        vehiculo.iniciarControl();

        JFrameMaximizado(crudVehiculo, 1, 6);
        styles.VentanasJIF(crudVehiculo, contenedor.getDtp_principal());
        styles.JIFListeners(crudVehiculo, contenedor.getDtp_principal(), vcam);
        contenedor.getDtp_principal().add(crudVehiculo);
        contenedor.getDtp_principal().updateUI();

        styles.JInternalrameColor(crudVehiculo);
        styles.setearIcoIFrame(crudVehiculo, "Vehiculos", "icons8_pickup_front_view_20px");
        styles.setearIcoJDialog(crudVehiculo.getjDVehiculos(), "Vehiculos", "icons8_pickup_front_view_20px");
        vehiculo.JTableAutomovil();
        styles.setJComboBoxColor(crudVehiculo.getCombocolorV());
    }

    public void VentanaCrud() {
        styles = new Styles(3);//carga el tipo de fuentes para los JInternalFrames 
        contenedor.getItemUsuario().addActionListener(l -> {
            contenedor.getDtp_principal().removeAll();
            AbrirCrudCliente();
        });

        contenedor.getItemEmpleado().addActionListener(l -> {
            contenedor.getDtp_principal().removeAll();
            AbrirCrudEmpleado();
        });
        contenedor.getItemVehiculos().addActionListener(l -> {
            contenedor.getDtp_principal().removeAll();
            AbrirCrudVehiculo();
        });

        contenedor.getRitem_clientes().addActionListener(e -> {
            mostrarReporte("parkingIstaReport", contenedor.getRitem_clientes().getText());

        });
        contenedor.getRitem_empleado().addActionListener(e -> {
            mostrarReporte("reporteEmpleados", contenedor.getRitem_empleado().getText());
        });
        contenedor.getItem_salir().addActionListener(l -> salir());
        contenedor.getItem_logout().addActionListener(l -> CerrarSesion());

        contenedor.getJmi_about().addActionListener((e) -> {
            info = new Info(FrameHelp.SHOW_ABOUT);
            styles.setearIcoJDialog(info, "JavaANPR", "icons8_artificial_intelligence_20px");
            info.setLocationRelativeTo(null);
            info.setVisible(true);
        });
    }

    private void iniciarPrograma() {
        contenedor = new VPrincipal();
        vcam = new VCamara();
        controlc = new ControlCamara(vcam);
        controlc.iniciarControl();
        vlogin.dispose();
        styles.VentanasJIF(vcam, contenedor.getDtp_principal());
        vcam.setClosable(false);
        contenedor.getDtp_principal().add(vcam);
        contenedor.setVisible(true);
        cerrarAPP();
        JFrameMaximizado(vcam, 1, 4);
        CargarIconos();
        cargarFontColor();
        contenedor.getJm_consultas().setVisible(false);
        styles.setearIcoJLabel(contenedor.getLbl_user_name(), registro.datoNotificacion(vlogin.getTxt_cedula().getText()), "icons8_green_circle_20px");
        VentanaCrud();
    }

    private boolean ComprobarUsuario() {
        int comprobante;
        comprobante = registro.VerificarUsuario(vlogin.getTxt_cedula().getText(), new String(vlogin.getTxt_password().getPassword()));
        if (comprobante != 0) {
            registro.InsertarHoraEntrada(comprobante);
            registro.setCodRegistro(comprobante);
            return true;
        } else {
            return false;
        }
    }

    private void AbrirCrudCliente() {
        instanciarCRUDUE();
        styles.setearIcoIFrame(crud, "Ingreso de cliente", "CRUD_ICO_20x20");
        styles.setearIcoJDialog(crud.getjDUsuario(), "Nuevo Usuario", "icons8_management_20px");
        ventana.JTableEmpleadoCliente("Cliente");
    }

    private void AbrirCrudEmpleado() {
        instanciarCRUDUE();
        styles.setearIcoIFrame(crud, "Ingreso de empleado", "CRUD_ICO_20x20");
        styles.setearIcoJDialog(crud.getjDPersonal(), "Nuevo Empleado", "icons8_management_20px");
        ventana.JTableEmpleadoCliente("Empleado");

    }

    private void instanciarCRUDUE() {
        crud = new VCrud();
        cone = new Cliente();
        ventana = new ControlCrud(crud, cone);
        ventana.ControlCrud();
        styles.VentanasJIF(crud, contenedor.getDtp_principal());
        styles.JIFListeners(crud, contenedor.getDtp_principal(), vcam);
        contenedor.getDtp_principal().add(crud);
        contenedor.getDtp_principal().updateUI();
        styles.JInternalrameColor(crud);
        styles.JPanelColor(crud.getPanelUsuario());
        styles.JPanelColor(crud.getPnl_vehiculo());
        styles.JlabelColor(crud.getLbl_titulo());
        styles.setJComboBoxColor(crud.getjComboColor());
        styles.setearIcoJLabel(crud.getLbl_pass(), null, "icons8_show_password_35px");
        crud.getClave1().setEchoChar((char) '\u2022');
        styles.mouseListenerPassword(crud.getLbl_pass(), crud.getClave1());

    }

    private void CerrarSesion() {
        registro.InsertarHoraSalida(registro.ObtenerCodigoHoraMR());
        registro.ObtenerHorasTrabajadas(registro.ObtenerCodigoHoraMR());
        contenedor.dispose();
        vlogin.getTxt_cedula().setText("");
        vlogin.getTxt_password().setText("");
        vlogin.setVisible(true);
        try {
            controlc.ApagarCamara();
        } catch (Exception e) {
        }
    }

    private void salir() {
        CerrarSesion();
        contenedor.setVisible(false);
        contenedor.dispose();
        System.exit(0);
    }

    private void CargarIconos() {
        styles.setearIcoJMItem(contenedor.getItemEmpleado(), "icons8_member_20px");
        styles.setearIcoJMItem(contenedor.getItemVehiculos(), "icons8_pickup_front_view_20px");
        styles.setearIcoJMItem(contenedor.getItemUsuario(), "icons8_add_user_male_20px");
        styles.setearIcoJMItem(contenedor.getItem_logout(), "icons8_logout_rounded_left_20px");
        styles.setearIcoJMItem(contenedor.getItem_salir(), "icons8_exit_20px");
        styles.setearIcoJMItem(contenedor.getJmi_about(), "icons8_information_20px");
        styles.setearIcoJMItem(contenedor.getCitem_usuario(), "icons8_driver_20px");
        styles.setearIcoJMItem(contenedor.getCitem_trabajadores(), "icons8_more_info_20px");
        styles.setearIcoJMItem(contenedor.getCitem_vehiculos(), "icons8_weighbridge_20px");
        styles.setearIcoJMItem(contenedor.getRitem_clientes(), "icons8_shared_document_20px");
        styles.setearIcoJMItem(contenedor.getRitem_empleado(), "icons8_shared_document_20px");
        styles.setearIcoJFrame(contenedor, "Parking ISTA", "pi_20px_1");
        styles.setearIcoIFrame(vcam, "C\u00e1\nmara", "icons8_camera_20px");
    }

    private void cargarFontColor() {
        styles.JMenuBarColor(contenedor.getJmb_general());
        styles.JMenuColor(contenedor.getJm_info());
        styles.JMenuColor(contenedor.getJm_mantenimiento());
        styles.JMenuColor(contenedor.getJm_reportes());
        styles.JMenuColor(contenedor.getJm_consultas());
        styles.JMenuItemColor(contenedor.getCitem_trabajadores(), "Empleados");
        styles.JMenuItemColor(contenedor.getCitem_usuario(), "Clientes");
        styles.JMenuItemColor(contenedor.getCitem_vehiculos(), "Veh\u00ed\nculos");
        styles.JMenuItemColor(contenedor.getItemEmpleado(), "Empleados");
        styles.JMenuItemColor(contenedor.getItemUsuario(), "Usuarios");
        styles.JMenuItemColor(contenedor.getItem_logout(), "Cerrar sesi\u00f3\nn");
        styles.JMenuItemColor(contenedor.getItem_salir(), "Salir");
        styles.JMenuItemColor(contenedor.getRitem_clientes(), "Clientes");
        styles.JMenuItemColor(contenedor.getRitem_empleado(), "Empleados");
        styles.JMenuItemColor(contenedor.getJmi_about(), "About");
        styles.JMenuItemColor(contenedor.getItemVehiculos(), "Veh\u00ed\nculos");
        styles.mouseJmenuListener(contenedor.getJm_consultas());
        styles.mouseJmenuListener(contenedor.getJm_mantenimiento());
        styles.mouseJmenuListener(contenedor.getJm_reportes());
        styles.mouseJmenuListener(contenedor.getJm_info());
        styles.JMenuListener(contenedor.getJm_mantenimiento());
    }

    private void setearPH() {

        ph = new TextPrompt("C\u00e9\ndula", vlogin.getTxt_cedula());
        ph = new TextPrompt("Password", vlogin.getTxt_password());
    }

    public Action action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ComprobarUsuario()) {
                iniciarPrograma();
                System.out.println("Valido");
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o clave incorrecta");
            }
        }
    };

    public void resized(JInternalFrame jif, JDesktopPane jdp, int maxdesmax, int div) {
        if (jif.isShowing()) {
            switch (maxdesmax) {
                case 1:
                    jif.setLocation((jdp.getWidth() - jif.getWidth()) / div, (jdp.getHeight() - jif.getHeight()) / div);
                    break;
                case 2:
                    jif.setLocation((jdp.getWidth() - jif.getWidth()) / div, (jdp.getHeight() - jif.getHeight()) / div);
                    break;
            }
        }
    }

    public void cerrarAPP() {
        try {
            contenedor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            contenedor.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    validaciones.mostrarDIalogo(700, "Cierre sesi\u00f3n\n", Validaciones.DIALOG_IMPORTANT);
                }
            });
            contenedor.setVisible(true);
        } catch (Exception e) {
        }
    }

    public void JFrameMaximizado(JInternalFrame jif, int max, int min) {
        contenedor.addWindowStateListener((WindowEvent e) -> {
            boolean maximizado = (e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
            boolean fueMaximizado = (e.getOldState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
            if (maximizado && !fueMaximizado) {
                resized(jif, contenedor.getDtp_principal(), 1, max);
                System.out.println("Ventana Maximizada:");
            } else if (fueMaximizado && !maximizado) {
                resized(jif, contenedor.getDtp_principal(), 2, min);
                System.out.println("Ventana no Maximizada");
            }
        });
    }

    private void mostrarReporte(String Reporte, String Tittle) {
        JasperReport masterReport;
        JasperPrint jp;
        JasperViewer jv;
        Image imagen1 = new ImageIcon(getClass().getResource("/Vistas/Iconos/ISTA_LOGO.png")).getImage();
        Image imagen2 = new ImageIcon(getClass().getResource("/Vistas/Iconos/ISTA_ICO.png")).getImage();
        Map<String, Object> parametros = new HashMap<>();
        try {
            masterReport = (JasperReport) JRLoader.loadObject(getClass().getResource("/Vistas/Reports/" + Reporte + ".jasper"));
            parametros.put("imagen1", imagen1);
            parametros.put("imagen2", imagen2);
            jp = JasperFillManager.fillReport(masterReport, parametros, ConexionDB.getConexion());
            jv = new JasperViewer(jp, false);
//            jv.setVisible(true);

            JDialog dialog = new JDialog();
            dialog.setContentPane(jv.getContentPane());
            dialog.setSize(jv.getSize());
            dialog.setLocationRelativeTo(null);
            styles.setearIcoJDialog(dialog, Tittle, "pi_20px");
            dialog.setResizable(true);
            dialog.setModal(true);
            dialog.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(ControlPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

//        contenedor.getCitem_usuario().addActionListener(e -> {
//
//        });
//        contenedor.getCitem_trabajadores().addActionListener(e -> {
//
//        });
//        contenedor.getCitem_vehiculos().addActionListener(e -> {
//
//        });
