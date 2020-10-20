package Controlador;

import Modelo.Automovil;
import Modelo.Cliente;
import Modelo.Color;
import Vistas.CRUD.VCrudVehiculo;
import Vistas.Styles;
import Vistas.TextPrompt;
import Vistas.Validaciones;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.w3c.dom.events.EventException;
import Vistas.CRUD.VCrud;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class ControlCrudVehiculos {

    protected VCrudVehiculo crudVehiculo;
    private Automovil vehiculo;
    protected Color colores = new Color();
    protected Cliente cliente = new Cliente();
    private static int perSelecionada = -1;
    VCrud crud = new VCrud();
    protected Validaciones validacion = new Validaciones();
    protected Styles styles = new Styles();
    protected TextPrompt ph;

    public ControlCrudVehiculos(VCrudVehiculo crudVehiculo, Automovil vehiculo) {
        this.crudVehiculo = crudVehiculo;
        this.vehiculo = vehiculo;
        this.crudVehiculo.setVisible(true);
    }

    public void iniciarControl() {
        validarCampos();
        crudVehiculo.getBtnAtrasV().setVisible(true);
        placeHolderCampos();
        eventCerrarJDialogs();
                filtroBuscar(crudVehiculo.getTxt_filtovehiculo());
        setFilteField(crudVehiculo.getJcb_filtroAutos());
        CargarCombos();
        ComprobarCliente(crudVehiculo.getTxtCedula());
        crudVehiculo.getBtninsertarV().addActionListener(l -> abrirDialogos("registrar"));
        crudVehiculo.getBtn_guardarV().addActionListener(l -> GuardarVehiculos());
        crudVehiculo.getBtnEditarV().addActionListener(l -> CargarDatos());
        crudVehiculo.getBtnEliminarV().addActionListener(l -> EliminarAutomovil());
        crudVehiculo.getBtn_atrasV().addActionListener(l -> cerrarDialogos());
        crudVehiculo.getBtnAtrasV().addActionListener(l -> crudVehiculo.dispose());
    }

    public void abrirDialogos(String caso) {
        switch (caso.toLowerCase()) {
            case "registrar":
                crudVehiculo.getTxtCedula().setEditable(true);
                crudVehiculo.getTxtNombre().setEditable(false);
                crudVehiculo.getTxtApellido().setEditable(false);
                crudVehiculo.getTxtPlaca().setEditable(true);
                DialogoVehiculos("Registrar");
                break;
            case "actualizar":
                crudVehiculo.getTxtCedula().setEditable(false);
                crudVehiculo.getTxtNombre().setEditable(false);
                crudVehiculo.getTxtApellido().setEditable(false);
                crudVehiculo.getTxtPlaca().setEditable(false);
                DialogoVehiculos("Actualizar");
                break;
        }
    }

    public void DialogoVehiculos(String tittle) {
        crudVehiculo.getjDVehiculos().setTitle(tittle);
        crudVehiculo.getjDVehiculos().setSize(529, 320);
        crudVehiculo.getjDVehiculos().setLocationRelativeTo(crudVehiculo);
        crudVehiculo.getjDVehiculos().setModal(true);
        crudVehiculo.getjDVehiculos().setVisible(true);
    }

    public void cerrarDialogos() {
        limpiarCampos();
        crudVehiculo.getJtableVehiculos().clearSelection();
        crudVehiculo.getjDVehiculos().dispose();
    }

    public void CargarCombos() {
        crudVehiculo.getCombomodeloV().setEnabled(false);
        vehiculo.CargarComboMarca(crudVehiculo.getCombomarcaV());
        colores.CargarComboColor(crudVehiculo.getCombocolorV());
        crudVehiculo.getCombomarcaV().addItemListener(l -> {
            if (crudVehiculo.getCombomarcaV().getSelectedIndex() != 0) {
                crudVehiculo.getCombomodeloV().setEnabled(true);
            } else {
                crudVehiculo.getCombomodeloV().setEnabled(false);
            }
            vehiculo.CargarComboModelo(crudVehiculo.getCombomodeloV(), (String) crudVehiculo.getCombomarcaV().getSelectedItem());
        });
    }

    public void ComprobarCliente(JTextField caja) {
        caja.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cliente.verificarCliente(crudVehiculo.getTxtNombre(), crudVehiculo.getTxtApellido(), crudVehiculo.getTxtCedula().getText());

            }
        });
    }
        public void filtroBuscar(JTextField caja) {
        caja.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                mostrarDatosFiltrados(); 
                  styles.setStyleJTable(crudVehiculo.getJtableVehiculos());
            }
        });
    }

    public DefaultTableModel mostrarDatosFiltrados() {
                return vehiculo.mostrarDatosFiltradosTablaAutomovil(defaultJTableModel(),
                        crudVehiculo.getTxt_filtovehiculo().getText(), (String) crudVehiculo.getJcb_filtroAutos().getSelectedItem());
    }
    
    public void setFilteField(JComboBox jcb) {
        jcb.addActionListener((ActionEvent e) -> {
            String s = (String) jcb.getSelectedItem();
            switch (s) {
                case "Placa":
                    mostrarDatosFiltrados();
                    break;
                case "Marca":
                    mostrarDatosFiltrados();
                    break;
                case "Modelo":
                    mostrarDatosFiltrados();
                    break;
            }
           styles.setStyleJTable(crudVehiculo.getJtableVehiculos());
        });
    }
    public void EliminarAutomovil() {
        perSelecionada = crudVehiculo.getJtableVehiculos().getSelectedRow();
        if (perSelecionada < 0) {
            validacion.mostrarDIalogo(1000, "Seleccione una fila que desea eliminar", Validaciones.DIALOG_IMPORTANT);
        } else {
            int confirmacion = JOptionPane.showConfirmDialog(crudVehiculo, "¿Esta seguro que desea eliminar el registro?");
            if (confirmacion == 0) {
                String placa = (String) crudVehiculo.getJtableVehiculos().getValueAt(perSelecionada, 0);
                vehiculo = new Automovil(placa, null, 0, 0);
                vehiculo.EliminarActualizarVehiculos();
            } else {
                validacion.mostrarDIalogo(1000, "Accion Cancelada", Validaciones.DIALOG_INFO);
            }
        }
        actualizarJtautomovil(defaultJTableModel());
    }

    private boolean camposVacios() {
        return crudVehiculo.getTxtCedula().getText().isEmpty()  || crudVehiculo.getTxtPlaca().getText().isEmpty() || crudVehiculo.getCombomarcaV().getSelectedItem().equals("Escoger")
                || crudVehiculo.getCombocolorV().getSelectedItem().equals("Escoger")
                || crudVehiculo.getCombomodeloV().getSelectedItem() == null;
    }

    public void GuardarVehiculos() {
        if (camposVacios()) {
            validacion.mostrarDIalogo(1000, "LLene todos los campos", Validaciones.DIALOG_IMPORTANT);
        } else {
            String cedula = crudVehiculo.getTxtCedula().getText();
            String marca = (String) crudVehiculo.getCombomarcaV().getSelectedItem();
            String modelo = (String) crudVehiculo.getCombomodeloV().getSelectedItem();
            String color = (String) crudVehiculo.getCombocolorV().getSelectedItem();
            String placa = crudVehiculo.getTxtPlaca().getText();

            switch (crudVehiculo.getjDVehiculos().getTitle()) {
                case "Registrar":
                    if (vehiculo.validarCedula(cedula)) {
                        System.out.println("econtrado");
                        vehiculo = new Automovil(placa, null, 0, 0);
                        vehiculo.InsertarAutomovil(marca, modelo, cedula, color);
                       validacion.mostrarDIalogo(1000, "Registro exitoso", Validaciones.DIALOG_SUCCES);
                        crudVehiculo.getjDVehiculos().dispose();
                    } else {
                        validacion.mostrarDIalogo(1000, "No hay cliente registrado con esa cedula", Validaciones.DIALOG_INFO);
                    }
                    break;
                case "Actualizar":
                    vehiculo = new Automovil(placa, null, 0, 0);
                    vehiculo.ActualizarVehiculo(marca, modelo, cedula, color);
                    crudVehiculo.getjDVehiculos().dispose();
                    break;
            }
            limpiarCampos();
            actualizarJtautomovil(defaultJTableModel());
        }
    }

    public void CargarDatos() {
        perSelecionada = crudVehiculo.getJtableVehiculos().getSelectedRow();
        if (perSelecionada < 0) {
            validacion.mostrarDIalogo(1000, "Seleccione una fila para editar", Validaciones.DIALOG_IMPORTANT);
        } else {
            crudVehiculo.getTxtCedula().setEditable(false);
            crudVehiculo.getTxtNombre().setEditable(false);
            crudVehiculo.getTxtApellido().setEditable(false);
            crudVehiculo.getTxtPlaca().setEditable(false);
            String placa = (String) crudVehiculo.getJtableVehiculos().getValueAt(perSelecionada, 0);
            vehiculo.CargarDatosActualizar(placa, crudVehiculo);
            DialogoVehiculos("Actualizar");
        }
    }

    public void actualizarJtautomovil(DefaultTableModel dtModel) {
        vehiculo.mostrarDatosTablaAutomovil(dtModel);
        styles.setStyleJTable(crudVehiculo.getJtableVehiculos());
    }

    public DefaultTableModel defaultJTableModel() {
        DefaultTableModel dtModel = (DefaultTableModel) crudVehiculo.getJtableVehiculos().getModel();
        dtModel.setRowCount(0);
        return dtModel;
    }

    public final void JTableAutomovil() {
        String[] columnas = new String[]{"Placa", "Modelo", "Marca", "Color"};
        crudVehiculo.getJtableVehiculos().setModel(new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                boolean[] editable = new boolean[]{false, false, false, false};
                return editable[columna];
            }
        });
        actualizarJtautomovil(defaultJTableModel());
    }

    private void validarCampos() {
        JTextField[] letras = {crudVehiculo.getTxtNombre(), crudVehiculo.getTxtApellido()};
        validacion.createFilteredField(crudVehiculo.getTxtPlaca(), 8, Validaciones.MATCH_PLACAS);
        validacion.createFilteredField(crudVehiculo.getTxtCedula(), 10, Validaciones.MATCH_NUMEROS);
        for (JTextField field : letras) {
            validacion.createFilteredField(field, 49, Validaciones.MATCH_LETRAS);
        }
    }

    public void limpiarCampos() {
        crudVehiculo.getTxtCedula().setText("");
        crudVehiculo.getTxtNombre().setText("");
        crudVehiculo.getTxtApellido().setText("");
        crudVehiculo.getTxtPlaca().setText("");
        crudVehiculo.getCombomarcaV().setSelectedItem("Escoger");
        crudVehiculo.getCombocolorV().setSelectedItem("Escoger");
        crudVehiculo.getCombomodeloV().setSelectedItem(null);
    }

    private void eventCerrarJDialogs() {
        try {
            crudVehiculo.getjDVehiculos().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            crudVehiculo.getjDVehiculos().addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent we) {
                    limpiarCampos();
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    limpiarCampos();
                }
            });
        } catch (EventException e) {
            e.printStackTrace();
        }
    }

    private void placeHolderCampos() {
        JTextField[] jtf = {crudVehiculo.getTxtCedula(), crudVehiculo.getTxtNombre(), crudVehiculo.getTxtApellido(),
            crudVehiculo.getTxtPlaca(),};
        for (JTextField jTextField : jtf) {
            ph = new TextPrompt("Obligatorio", jTextField);
        }
    }

}
