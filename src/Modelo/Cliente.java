package Modelo;

import Vistas.CRUD.VCrud;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class Cliente extends Persona {

    ConexionDB conectar = new ConexionDB();
    private int codCliente;
    private String cedulaCliente;
    private int codTipoCliente;
    private String tipoCliente;

    public Cliente() {
    }

    public Cliente(int codCliente, String cedulaCliente, int codTipoCliente, String Cedula, String Nombre, String Apellido, String Celular, String Correo, String Sexo, Date fechaNacimiento, int edad) {
        super(Cedula, Nombre, Apellido, Celular, Correo, Sexo, fechaNacimiento, edad);
        this.codCliente = codCliente;
        this.cedulaCliente = cedulaCliente;
        this.codTipoCliente = codTipoCliente;
    }

    public Cliente(String cedulaCliente, String tipoCliente) {
        this.cedulaCliente = cedulaCliente;
        this.tipoCliente = tipoCliente;
    }

    public Cliente(String tipoCliente, String Cedula, String Nombre, String Apellido, String Celular, String Correo, String Sexo, Date fechaNacimiento, int edad) {
        super(Cedula, Nombre, Apellido, Celular, Correo, Sexo, fechaNacimiento, edad);
        this.tipoCliente = tipoCliente;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public int getCodTipoCliente() {
        return codTipoCliente;
    }

    public void setCodTipoCliente(int codTipoCliente) {
        this.codTipoCliente = codTipoCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    //consultas SQL
    public boolean InsertarPersona() {
        String sql;
        sql = "Insert into persona";
        sql += " (cedula, nombre, apellido, celular, correo, sexo, fecha_nacimiento, edad, eliminado)";
        sql += " VALUES('" + getCedula() + "','" + getNombre() + "','" + getApellido() + "','" + getCelular() + "','" + getCorreo() + "','" + getSexo() + "',TO_DATE('" + getFechaNacimiento() + "','YYYY-MM-DD')," + getEdad() + ", 1)";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }

    public void CargarComboTipo(JComboBox comboTipo) {
        try {
            String sql = "select * from tiposclientes";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {
                comboTipo.addItem(rs.getString("tipo_cliente"));
            }
        } catch (SQLException ex) {
            System.out.println("error combo");
        }
    }

    public boolean InsertarCliente() {
        String sql, sqlTipo;

        try {
            sqlTipo = "select cod_tipo from tipo_cliente where tipo_cliente ='" + getTipoCliente() + "'";
            ResultSet rs = conectar.query(sqlTipo);
            while (rs.next()) {
                setCodTipoCliente(rs.getInt("cod_tipo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "Insert into cliente";
        sql += " (cedula_cliente, cod_tipo_cliente)";
        sql += " values('" + getCedulaCliente() + "'," + getCodTipoCliente() + ")";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("Error al registrar al cliente.");
            return false;
        }
    }

    public void mostrarDatoEditar(String id_cliente, VCrud vistaC, String placa) {
          vistaC.getPlaca().setEditable(false);
        try {
            String sql = "SELECT p.cedula, p.nombre, p.apellido,p.celular,"
                    + "p.correo,  p.sexo,p.fecha_nacimiento, ct.tipo_cliente,"
                    + "ct.placa, ct.marca, ct.modelo,ct.color "
                    + "FROM persona p  JOIN cliente_tabla   ct "
                    + "ON p.cedula = ct.cedula "
                    + "WHERE p.cedula ='" + id_cliente + "' and ct.placa='" + placa + "'";
            try (ResultSet rs = conectar.query(sql)) {
                while (rs.next()) {
                    vistaC.getCedula().setText(rs.getString("cedula").toUpperCase());
                    vistaC.getNombre().setText(rs.getString("nombre").toUpperCase());
                    vistaC.getApellido().setText(rs.getString("apellido").toUpperCase());
                    vistaC.getCelular().setText(rs.getString("celular").toUpperCase());
                    vistaC.getCorreo().setText(rs.getString("correo"));
                    vistaC.getjComboSexo().setSelectedItem(rs.getString("sexo").toUpperCase());
                    vistaC.getJDateFecha().setDate(rs.getDate("fecha_nacimiento"));
                    vistaC.getJCombotipo().setSelectedItem(rs.getString("tipo_cliente").toUpperCase());
                    vistaC.getPlaca().setText(rs.getString("placa").toUpperCase());
                    
                    vistaC.getjComboMarca().setSelectedItem(rs.getString("marca").toUpperCase());
                    vistaC.getjComboModelo().setSelectedItem(rs.getString("modelo").toUpperCase());
                    vistaC.getjComboColor().setSelectedItem(rs.getString("color").toUpperCase());
                    
                }
              
                rs.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean ActualizarPersona() {
        String nsql = "update persona set nombre = '" + getNombre() + "', apellido = '" + getApellido() + "', celular = '" + getCelular() + "', correo = '" + getCorreo() + "', "
                + "sexo = '" + getSexo() + "',  fecha_nacimiento = to_date('" + getFechaNacimiento() + "','YYYY-MM-DD') where cedula = '" + getCedula() + "'";

        if (conectar.noQuery(nsql) == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ActualizarCliente() {
        String nsqlcod = "update cliente set cod_tipo_cliente = (select cod_tipo from tipo_cliente where tipo_cliente = '" + getTipoCliente() + "') "
                + "where cedula_cliente = '" + getCedula() + "'";
        if (conectar.noQuery(nsqlcod) == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarActualizarCliente() {
        String nsqlcod = "update persona set eliminado = 0 where cedula = '" + getCedulaCliente() + "'";
        if (conectar.noQuery(nsqlcod) == null) {
            return true;
        } else {
            return false;
        }
    }

    public void verificarCliente(JTextField nombre, JTextField apellido, String cedula) {
        try {
            String sql = "select p.nombre, p.apellido from persona p join cliente c on p.cedula = c.cedula_cliente where cedula = '" + cedula + "'";
            try (ResultSet rs = conectar.query(sql)) {
                if (rs.next()) {
                    nombre.setText(rs.getString("nombre"));
                    apellido.setText(rs.getString("apellido"));
//                    nombre.setEditable(false);
//                    apellido.setEditable(false);
                } else {
                    nombre.setText("");
                    apellido.setText("");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public boolean validarCedula(String cedula, String titulo) {

        try {
            String sql = "select cedula from persona where cedula='" + cedula + "'";
            try (ResultSet rs = conectar.query(sql)) {
                return rs.next() && titulo.contains("Ingreso");
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

}
