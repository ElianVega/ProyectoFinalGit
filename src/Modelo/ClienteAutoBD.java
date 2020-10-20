package Modelo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ClienteAutoBD extends Cliente {

    private String placa;
    private String marca;
    private String modelo;
    private String color;

    public ClienteAutoBD() {
    }

    public ClienteAutoBD(String placa, String marca, String modelo, String color, int codCliente, String cedulaCliente, int codTipoCliente, String Cedula, String Nombre, String Apellido, String Celular, String Correo, String Sexo, Date fechaNacimiento, int edad) {
        super(codCliente, cedulaCliente, codTipoCliente, Cedula, Nombre, Apellido, Celular, Correo, Sexo, fechaNacimiento, edad);
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public DefaultTableModel mostrarDatosTablaCliente(DefaultTableModel dtm) {
        try {
            String sql = "select * from cliente_tabla order by cedula asc";
            try (ResultSet rs = conectar.query(sql)) {
                while (rs.next()) {
                    dtm.addRow(new Object[]{rs.getString("cedula").toUpperCase(), rs.getString("nombre").toUpperCase(),
                        rs.getString("apellido").toUpperCase(),
                        rs.getString("celular").toUpperCase(), rs.getString("tipo_cliente").toUpperCase(), rs.getString("placa").toUpperCase(),
                        rs.getString("marca").toUpperCase(),
                        rs.getString("modelo").toUpperCase(), rs.getString("color").toUpperCase()
                    });
                }
            }
            return dtm;
        } catch (SQLException e) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public DefaultTableModel mostrarDatosFiltradosTablaCliente(DefaultTableModel dtm, String valorBuscar, String campo) {
        try {
            String sql = "select * from cliente_tabla where "+campo+" like upper('"+valorBuscar+"%')  order by "+campo+" asc";
            try (ResultSet rs = conectar.query(sql)) {
                while (rs.next()) {
                    dtm.addRow(new Object[]{rs.getString("cedula").toUpperCase(), rs.getString("nombre").toUpperCase(),
                        rs.getString("apellido").toUpperCase(),
                        rs.getString("celular").toUpperCase(), rs.getString("tipo_cliente").toUpperCase(), rs.getString("placa").toUpperCase(),
                        rs.getString("marca").toUpperCase(),
                        rs.getString("modelo").toUpperCase(), rs.getString("color").toUpperCase()
                    });
                }
            }
            return dtm;
        } catch (SQLException e) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
