package Controlador;

import Modelo.Automovil;
import Modelo.Cliente;
import Modelo.ClienteAutoBD;
import Modelo.Empleado;
import Modelo.Color;
import Modelo.RegistroEmpleadoDB;
import Vistas.CRUD.VCrud;
import Vistas.Styles;
import Vistas.TextPrompt;
import Vistas.Validaciones;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Time;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import org.w3c.dom.events.EventException;

public class ControlCrud {

    protected SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    protected SpinnerNumberModel MinutosS = new SpinnerNumberModel();
    protected SpinnerNumberModel MinutosE = new SpinnerNumberModel();
    protected SpinnerNumberModel HorasE = new SpinnerNumberModel();
    protected SpinnerNumberModel HorasS = new SpinnerNumberModel();
    protected ClienteAutoBD mostrarClientes = new ClienteAutoBD();
    protected Empleado mostrarEmpleado = new Empleado();
    protected Validaciones validacion = new Validaciones();
    protected Empleado empleado = new Empleado();
    protected Automovil auto = new Automovil();
    protected Color colores = new Color();
    protected Styles style = new Styles();
    private static int perSelecionada = -1;
    protected TextPrompt ph;
    private Cliente cliente;
    protected VCrud crud;

    public ControlCrud(VCrud crud, Cliente cliente) {
        this.crud = crud;
        this.cliente = cliente;
        this.crud.setVisible(true);
    }

    public void ControlCrud() {
        placeHolderCampos();
        validarCampos();
        filtroBuscar(crud.getTxt_sfiltro());
        setFilteField(crud.getJc_filtrar());
        eventCerrarJDialogs(crud.getjDUsuario());
        eventCerrarJDialogs(crud.getjDPersonal());
        CargarCombo();
        crud.getBtn_agregar().addActionListener(l -> AbrirDailogos("registrar"));
        crud.getBtn_guardar().addActionListener(l -> GuardarCliente());
        crud.getBtn_guardarAdmin().addActionListener(l -> GuardarEmpleado());
        crud.getBtn_atrasAdmin().addActionListener(l -> cerrarDialogos());
        crud.getBtn_atras1().addActionListener(l -> cerrarDialogos());
        crud.getBtn_editar().addActionListener(l -> cargarDatos());
        crud.getBtn_atras().addActionListener(l -> crud.dispose());
        crud.getBtn_eliminar().addActionListener(l -> EliminarClienteEmpleado(crud.getTitle()));
    }

    public void DialogoUsuario(String tittle) {
        crud.getjDUsuario().setTitle(tittle);
        crud.getjDUsuario().setSize(741, 370);
        crud.getjDUsuario().setLocationRelativeTo(crud);
        crud.getjDUsuario().setModal(true);
        crud.getjDUsuario().setVisible(true);
    }

    public void DialogoEmpleado(String tittle) {
        crud.getjDPersonal().setTitle(tittle);
        crud.getjDPersonal().setSize(579, 370);
        crud.getjDPersonal().setLocationRelativeTo(crud);
        crud.getjDPersonal().setModal(true);
        crud.getjDPersonal().setVisible(true);

        HorasE.setMaximum(23);
        MinutosE.setMaximum(59);
        HorasE.setMinimum(00);
        MinutosE.setMinimum(00);
        HorasS.setMaximum(23);
        MinutosS.setMaximum(59);
        HorasS.setMinimum(00);
        MinutosS.setMinimum(00);
        crud.getEntradaHoras1().setModel(HorasE);
        crud.getSalidaHoras1().setModel(HorasS);
        crud.getEntradaMinutos1().setModel(MinutosE);
        crud.getSalidaMinutos1().setModel(MinutosS);

    }

    public void cerrarDialogos() {
        limpiarCampos();
        crud.getJtb_registros().clearSelection();
        crud.getjDUsuario().dispose();
        crud.getjDPersonal().dispose();
    }

    public void AbrirDailogos(String caso) {
        if (crud.getTitle().equalsIgnoreCase("Ingreso de Cliente")) {
            switch (caso.toLowerCase()) {
                case "actualizar":
                    DialogoUsuario("Actualizar Cliente");
                    crud.getCedula().setEditable(false);
                    break;
                case "registrar":
                    crud.getCedula().setEditable(true);
                    crud.getPlaca().setEditable(true);
                    crud.getLbl_titulo().setText("Agregar Usuario");
                    DialogoUsuario("Ingreso de Cliente");
                    break;
            }
        }
        if (crud.getTitle().equalsIgnoreCase("Ingreso de Empleado")) {
            switch (caso.toLowerCase()) {
                case "actualizar":
                    DialogoEmpleado("Actualizar Empleado");
                    crud.getTxtCedulaAdmin().setEditable(false);
                    break;
                case "registrar":
                    crud.getTxtCedulaAdmin().setEditable(true);
                    DialogoEmpleado("Ingreso Empleado");
                    break;
            }
        }
    }

    public void cargarDatos() {
        if (crud.getTitle().equalsIgnoreCase("Ingreso de Cliente")) {
            crud.getCedula().setEditable(false);
            mostrarDatos("Cliente");
            crud.getLbl_titulo().setText("Editar Usuario");
        }
        if (crud.getTitle().equalsIgnoreCase("Ingreso de empleado")) {
            crud.getTxtCedulaAdmin().setEditable(false);
            mostrarDatos("Empleado");

        }
    }

    //Cargar combobox
    public void CargarCombo() {
        crud.getjComboModelo().setEnabled(false);
        cliente.CargarComboTipo(crud.getJCombotipo());
        auto.CargarComboMarca(crud.getjComboMarca());
        colores.CargarComboColor(crud.getjComboColor());
        crud.getjComboMarca().addItemListener(l -> {
            if (crud.getjComboMarca().getSelectedIndex() != 0) {
                crud.getjComboModelo().setEnabled(true);
            } else {
                crud.getjComboModelo().setEnabled(false);
            }
            auto.CargarComboModelo(crud.getjComboModelo(), (String) crud.getjComboMarca().getSelectedItem());
        });
    }

    public void mostrarDatos(String tipo) {
        perSelecionada = crud.getJtb_registros().getSelectedRow();
        if (perSelecionada < 0) {
            validacion.mostrarDIalogo(1000, "Seleccione una fila para editar", Validaciones.DIALOG_IMPORTANT);
        } else {
            switch (tipo) {
                case "Cliente":
                    String id_cliente = (String) crud.getJtb_registros().getValueAt(perSelecionada, 0);
                    String placa = (String) crud.getJtb_registros().getValueAt(perSelecionada, 5);
                    cliente.mostrarDatoEditar(id_cliente, crud, placa);
                    AbrirDailogos("actualizar");
                    break;
                case "Empleado":
                    String id_empleado = (String) crud.getJtb_registros().getValueAt(perSelecionada, 0);
                    empleado.mostrarDatoEditarEmpleado(id_empleado, crud);
                    AbrirDailogos("actualizar");
                    break;
            }
            crud.getjComboMarca().setEditable(false);
            crud.getjComboModelo().setEditable(false);
            crud.getjComboColor().setEditable(true);
        }
    }

    public void filtroBuscar(JTextField caja) {
        caja.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String title = crud.getTitle();
                mostrarDatosFiltrados(title);
                style.setStyleJTable(crud.getJtb_registros());
            }
        });
    }

    public void setFilteField(JComboBox jcb) {
        jcb.addActionListener((ActionEvent e) -> {
            String s = (String) jcb.getSelectedItem();
            String title = crud.getTitle();
            switch (s) {
                case "Cedula":
                    mostrarDatosFiltrados(title);
                    break;
                case "Nombre":
                    mostrarDatosFiltrados(title);
                    break;
                case "Apellido":
                    mostrarDatosFiltrados(title);
                    break;
            }
            style.setStyleJTable(crud.getJtb_registros());
        });
    }

    public DefaultTableModel mostrarDatosFiltrados(String title) {
        switch (title) {
            case "Ingreso de empleado":

                return mostrarEmpleado.mostrarDatosFiltradosTablaEmpleado(defaultJTableModel(),
                        crud.getTxt_sfiltro().getText(), (String) crud.getJc_filtrar().getSelectedItem());

            case "Ingreso de cliente":
                return mostrarClientes.mostrarDatosFiltradosTablaCliente(defaultJTableModel(),
                        crud.getTxt_sfiltro().getText(), (String) crud.getJc_filtrar().getSelectedItem());
        }
        return null;
    }

    public void EliminarClienteEmpleado(String title) {
        perSelecionada = crud.getJtb_registros().getSelectedRow();
        if (perSelecionada < 0) {
            validacion.mostrarDIalogo(1000, "Seleccione una fila que desea eliminar", Validaciones.DIALOG_IMPORTANT);
        } else {
            int confirmacion = JOptionPane.showConfirmDialog(crud, "Seguro que desea eliminar el registro?", "", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null);
            if (confirmacion == JOptionPane.YES_OPTION) {
                String id_cliente = (String) crud.getJtb_registros().getValueAt(perSelecionada, 0);
                cliente = new Cliente(id_cliente, null);
                cliente.EliminarActualizarCliente();
                auto.EliminarActualizarCliVehiculo(id_cliente);
            } else {
                validacion.mostrarDIalogo(1000, "Accion Cancelada", Validaciones.DIALOG_INFO);
            }
        }
        switch (title) {
            case "Ingreso de empleado":
                actualizarJtempleados(defaultJTableModel());
                break;
            case "Ingreso de cliente":
                actualizarJtClientes(defaultJTableModel());
                break;
        }
    }

    public void actualizarJtClientes(DefaultTableModel dtModel) {
        mostrarClientes.mostrarDatosTablaCliente(dtModel);
        style.setStyleJTable(crud.getJtb_registros());
    }

    public void actualizarJtempleados(DefaultTableModel dtModel) {
        mostrarEmpleado.mostrarDatosTablaEmpleado(dtModel);
        style.setStyleJTable(crud.getJtb_registros());
    }

    public DefaultTableModel defaultJTableModel() {
        DefaultTableModel dtModel = (DefaultTableModel) crud.getJtb_registros().getModel();
        dtModel.setRowCount(0);
        style.setStyleJTable(crud.getJtb_registros());
        return dtModel;
    }

    public void JTableEmpleadoCliente(String tipo) {
        String[] columnas = null;
        switch (tipo.toLowerCase().trim()) {
            case "empleado":
                columnas = new String[]{"Cedula", "Nombre", "Apellido", "Celular", "Hora de Entrada", "Hora de Salida"};
                break;
            case "cliente":
                columnas = new String[]{"Cedula", "Nombre", "Apellido", "Celular", "Tipo", "Placa", "Marca", "Modelo", "Color"};
                break;
        }
        crud.getJtb_registros().setModel(new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                boolean[] editable = null;
                switch (tipo.toLowerCase().trim()) {
                    case "empleado":
                        editable = new boolean[]{false, false, false, false, false, false};
                        break;
                    case "cliente":
                        editable = new boolean[]{false, false, false, false, false, false, false, false, false};
                        break;
                }
                return editable[columna];
            }
        });
        if (tipo.equalsIgnoreCase("Empleado")) {
            actualizarJtempleados(defaultJTableModel());
        }
        if (tipo.equalsIgnoreCase("Cliente")) {
            actualizarJtClientes(defaultJTableModel());
        }
    }

    //Recuperar Datos Cliente
    public void GuardarCliente() {
        if (camposVaciosClientes()) {
            validacion.mostrarDIalogo(1000, "LLene todos los campos", Validaciones.DIALOG_IMPORTANT);
        } else {
            String cedula = crud.getCedula().getText();
            String nombre = crud.getNombre().getText();
            String apellido = crud.getApellido().getText();
            String celular = crud.getCelular().getText();
            String correo = crud.getCorreo().getText();
            String Fecha = sdf.format(crud.getJDateFecha().getDate());
            Date fechaNacimiento = Date.valueOf(Fecha);
            String sexo = (String) crud.getjComboSexo().getSelectedItem();
            String marca = (String) crud.getjComboMarca().getSelectedItem();
            String modelo = (String) crud.getjComboModelo().getSelectedItem();
            String color = (String) crud.getjComboColor().getSelectedItem();
            String placa = crud.getPlaca().getText();
            if (cliente.validarCedula(cedula, crud.getjDUsuario().getTitle())) {
                validacion.mostrarDIalogo(1000, "La cedula: " + cedula + " ya se encuentra registrada", Validaciones.DIALOG_WARNING);
                crud.getCedula().setText("");
            } else {
                if (crud.getjDUsuario().getTitle().equalsIgnoreCase("Ingreso de Cliente")) {
                    Cliente prueba = new Cliente(0, cedula, 0, cedula, nombre, apellido, celular, correo, sexo, fechaNacimiento, 0);
                    prueba.InsertarPersona();
                    Cliente insertarCliente = new Cliente(cedula, (String) crud.getJCombotipo().getSelectedItem());
                    insertarCliente.InsertarCliente();
                    Automovil insertarAuto = new Automovil(placa, null, 0, 0);
                    insertarAuto.InsertarAutomovil(marca, modelo, cedula, color);
                    validacion.mostrarDIalogo(1000, "Registro exitoso", Validaciones.DIALOG_SUCCES);
                }

                if (crud.getjDUsuario().getTitle().equalsIgnoreCase("Actualizar Cliente")) {
                    Cliente actualizar = new Cliente((String) crud.getJCombotipo().getSelectedItem(), cedula, nombre, apellido, celular, correo, sexo, fechaNacimiento, 0);
                    actualizar.ActualizarPersona();
                    actualizar.ActualizarCliente();
                    validacion.mostrarDIalogo(1000, "Registro actualizado", Validaciones.DIALOG_SUCCES);
                }
                crud.getjDUsuario().dispose();
                actualizarJtClientes(defaultJTableModel());
                limpiarCampos();
            }
        }
    }

    //Guardar empleado
    public void GuardarEmpleado() {
        if (camposVaciosEmpleado()) {
            validacion.mostrarDIalogo(1000, "LLene todos los campos", Validaciones.DIALOG_IMPORTANT);
        } else {
            String cedula = crud.getTxtCedulaAdmin().getText();
            String nombre = crud.getTxtNombreAdmin().getText();
            String apellido = crud.getTxtApellidoAdmin().getText();
            String celular = crud.getTxtCelularAdmin().getText();
            String correo = crud.getTxtCorreoAdmin().getText();
            String Fecha = sdf.format(crud.getjDateFechaAdmin().getDate());
            Date fechaNacimiento = Date.valueOf(Fecha);
            String sexo = (String) crud.getjComboSexoAdmin().getSelectedItem();
            String entradaHora = crud.getEntradaHoras1().getValue().toString() + ":" + crud.getEntradaMinutos1().getValue().toString() + ":" + 00;
            Time horaEntrada = Time.valueOf(entradaHora);
            String salidaHora = crud.getSalidaHoras1().getValue().toString() + ":" + crud.getSalidaMinutos1().getValue().toString() + ":" + 00;
            Time horaSalida = Time.valueOf(salidaHora);
            String clave = ObtenerClave();
            if (cliente.validarCedula(cedula, crud.getjDPersonal().getTitle())) {
                validacion.mostrarDIalogo(1000, "La cedula: " + cedula + " ya se encuentra registrada", Validaciones.DIALOG_WARNING);
                crud.getTxtCedulaAdmin().setText("");
            } else {
                if (crud.getjDPersonal().getTitle().equalsIgnoreCase("ingreso empleado")) {
                    RegistroEmpleadoDB registro = new RegistroEmpleadoDB(0, cedula, clave);
                    registro.InsertarUsuario();
                    Empleado insertarPersona = new Empleado(0, cedula, null, null, 0, cedula, nombre, apellido, celular, correo, sexo, fechaNacimiento, 0);
                    insertarPersona.InsertarPersona();
                    Empleado insertarEmpleado = new Empleado(cedula, horaEntrada, horaSalida, registro.ObtenerCodigo(cedula));
                    insertarEmpleado.InsertarEmpleado();
                    validacion.mostrarDIalogo(1000, "Registro exitoso", Validaciones.DIALOG_SUCCES);
                }
                if (crud.getjDPersonal().getTitle().equalsIgnoreCase("Actualizar empleado")) {
                    Empleado ActualizarEmpleado = new Empleado(horaEntrada, horaSalida, cedula, nombre, apellido, celular, correo, sexo, fechaNacimiento, 0);
                    ActualizarEmpleado.ActualizarPersona();
                    ActualizarEmpleado.ActualizarEmpleado();
                    validacion.mostrarDIalogo(1000, "Registro actualizado", Validaciones.DIALOG_SUCCES);
                }
                crud.getjDPersonal().dispose();
                actualizarJtempleados(defaultJTableModel());
            }
        }
    }

    public String ObtenerClave() {
        String guardarClave = "";
        char[] recorrerClave = crud.getClave1().getPassword();
        for (int i = 0; i < recorrerClave.length; i++) {
            guardarClave += recorrerClave[i];
        }
        return guardarClave;
    }

    private void placeHolderCampos() {
        JTextField[] jtf = {crud.getCedula(), crud.getNombre(), crud.getApellido(),
            crud.getCelular(), crud.getCorreo(), crud.getPlaca(), crud.getTxtCedulaAdmin(),
            crud.getTxtNombreAdmin(), crud.getTxtApellidoAdmin(),
            crud.getTxtCelularAdmin(), crud.getTxtCorreoAdmin(), crud.getClave1()
        };
        for (JTextField jTextField : jtf) {
            ph = new TextPrompt("Obligatorio", jTextField);
        }
    }

    private void limpiarCampos() {
        crud.getJtb_registros().clearSelection();
        JTextField[] jtf = {crud.getCedula(), crud.getNombre(), crud.getApellido(),
            crud.getCelular(), crud.getCorreo(), crud.getPlaca(), crud.getTxtCedulaAdmin(),
            crud.getTxtNombreAdmin(), crud.getTxtApellidoAdmin(),
            crud.getTxtCelularAdmin(), crud.getTxtCorreoAdmin(), crud.getClave1()
        };
        for (JTextField jTextField : jtf) {
            jTextField.setText("");
        }
        crud.getjComboSexo().setSelectedItem("Escoger");
        crud.getJDateFecha().setDate(null);
        crud.getJCombotipo().setSelectedItem("Escoger");
        crud.getjComboMarca().setSelectedItem("Escoger");
        crud.getjComboColor().setSelectedItem("Escoger");
        crud.getjComboModelo().setSelectedItem(null);
        crud.getjDateFechaAdmin().setDate(null);
        crud.getEntradaHoras1().setValue(0);
        crud.getjComboSexoAdmin().setSelectedItem("Escoger");
        crud.getEntradaMinutos1().setValue(0);
        crud.getSalidaHoras1().setValue(0);
        crud.getSalidaMinutos1().setValue(0);
    }

    private boolean camposVaciosEmpleado() {
        return crud.getTxtCedulaAdmin().getText().isEmpty()
                || crud.getTxtNombreAdmin().getText().isEmpty()
                || crud.getTxtApellidoAdmin().getText().isEmpty()
                || crud.getTxtCelularAdmin().getText().isEmpty()
                || crud.getTxtCorreoAdmin().getText().isEmpty()
                || crud.getjComboSexoAdmin().getSelectedItem().equals("Escoger")
                || crud.getjDateFechaAdmin().getDate() == null
                || new String(crud.getClave1().getPassword()).isEmpty();
    }

    private boolean camposVaciosClientes() {
        return crud.getCedula().getText().isEmpty() || crud.getNombre().getText().isEmpty() || crud.getApellido().getText().isEmpty()
                || crud.getCelular().getText().isEmpty() || crud.getCorreo().getText().isEmpty() || crud.getPlaca().getText().isEmpty()
                || crud.getJDateFecha().getDate() == null
                || crud.getjComboSexo().getSelectedItem().equals("Escoger") || crud.getJCombotipo().getSelectedItem().equals("Escoger")
                || crud.getjComboMarca().getSelectedItem().equals("Escoger") || crud.getjComboColor().getSelectedItem().equals("Escoger")
                || crud.getjComboModelo().getSelectedItem() == null;
    }

    private void eventCerrarJDialogs(JDialog jdl) {
        try {
            jdl.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jdl.addWindowListener(new WindowAdapter() {
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

    private void validarCampos() {
        JTextField[] emails = {crud.getCorreo(), crud.getTxtCorreoAdmin()};
        JTextField[] numeros = {crud.getCedula(), crud.getCelular(), crud.getTxtCedulaAdmin(), crud.getTxtCelularAdmin()};
        JTextField[] letras = {crud.getNombre(), crud.getApellido(), crud.getTxtNombreAdmin(), crud.getTxtApellidoAdmin()};

        validacion.createFilteredField(crud.getClave1(), 10, Validaciones.MATCH_PASSWORD);
        validacion.createFilteredField(crud.getPlaca(), 8, Validaciones.MATCH_PLACAS);
        for (JTextField field : numeros) {
            validacion.createFilteredField(field, 10, Validaciones.MATCH_NUMEROS);
        }
        for (JTextField field : letras) {
            validacion.createFilteredField(field, 49, Validaciones.MATCH_LETRAS);
        }
        for (JTextField field : emails) {
            validacion.createFilteredField(field, 49, Validaciones.MATCH_EMAILS);
        }
    }
}
