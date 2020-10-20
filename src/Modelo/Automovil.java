package Modelo;

import Vistas.CRUD.VCrudVehiculo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

public class Automovil {

    private String placa;
    private String color;
    private int marca;
    private int codCliente;
    ConexionDB conectar = new ConexionDB();

    public Automovil() {
    }

    public Automovil(String placa, String color, int marca, int codCliente) {
        this.placa = placa;
        this.color = color;
        this.marca = marca;
        this.codCliente = codCliente;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    //Sentencias SQL
    public void obtenerCodigos(String Marca, String Modelo, String Cedula, String color) {
        try {
            String sqlMarca = "select cod_marca from marca where marca = '" + Marca + "' and modelo = '" + Modelo + "'";
            ResultSet rs = conectar.query(sqlMarca);
            while (rs.next()) {
                setMarca(rs.getInt("cod_marca"));
            }
            System.out.println(getMarca());
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String sqlCodCliente = "select cod_cliente from cliente where cedula_cliente = '" + Cedula + "'";
            ResultSet rs = conectar.query(sqlCodCliente);
            while (rs.next()) {
                setCodCliente(rs.getInt("cod_cliente"));
            }
            System.out.println(getCodCliente());
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String sqlColor = "select cod_color from vehiculo_color where color = '" + color + "'";
            ResultSet rs = conectar.query(sqlColor);
            while (rs.next()) {
                setColor(rs.getString("cod_color"));
            }
            System.out.println(getColor());
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean InsertarAutomovil(String Marca, String Modelo, String Cedula, String color) {
        obtenerCodigos(Marca, Modelo, Cedula, color);
        String sql;
        sql = "Insert into automovil";
        sql += " (placa, cod_color, cod_marca, cod_cliente, eliminado)";
        sql += " values('" + getPlaca() + "','" + getColor() + "'," + getMarca() + "," + getCodCliente() + ",1)";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ActualizarVehiculo(String Marca, String Modelo, String Cedula, String color) {
        obtenerCodigos(Marca, Modelo, Cedula, color);
        String sql;
        sql = "update automovil set cod_color = '" + getColor() + "', cod_marca = '" + getMarca() + "' "
                + "where placa = '" + getPlaca() + "'";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarActualizarVehiculos() {
        String sql;
        sql = "update automovil set eliminado = 0 "
                + "where placa = '" + getPlaca() + "'";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarActualizarCliVehiculo(String cedula) {
        String sql;
        obtenerCodigos(null, null, cedula, null);
        sql = "update automovil set eliminado = 0 "
                + "where cod_cliente = '" + getCodCliente() + "'";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }
    public boolean validarCedula(String cedula){
          try {
            String sql = "select cedula from persona where cedula='"+cedula+"'";
            try (ResultSet rs = conectar.query(sql)) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);  
            return false;
        }
    
    }
    public void CargarDatosActualizar(String placa, VCrudVehiculo cajas) {
        try {
            String sql = "select ct.cedula, ct.nombre, ct.apellido,  ct.placa, ct.marca, ct.modelo, ct.color from "
                    + "cliente_tabla ct join automovil a on ct.placa = a.placa where a.placa = '" + placa + "'";
            try (ResultSet rs = conectar.query(sql)) {
                while (rs.next()) {
                    cajas.getTxtCedula().setText(rs.getString("cedula"));
                    cajas.getTxtNombre().setText(rs.getString("nombre").toUpperCase());
                    cajas.getTxtApellido().setText(rs.getString("apellido").toUpperCase());
                    cajas.getTxtPlaca().setText(rs.getString("placa").toUpperCase());
                    cajas.getCombomarcaV().setSelectedItem(rs.getString("marca").toUpperCase());
                    cajas.getCombomodeloV().setSelectedItem(rs.getString("marca").toUpperCase());
                    cajas.getCombocolorV().setSelectedItem(rs.getString("color").toUpperCase());
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public DefaultTableModel mostrarDatosTablaAutomovil(DefaultTableModel dtm) {
        try {
            String sql = "select * from cliente_tabla";
            try (ResultSet rs = conectar.query(sql)) {
                while (rs.next()) {
                    dtm.addRow(new Object[]{rs.getString("placa").toUpperCase(),
                        rs.getString("modelo").toUpperCase(),
                        rs.getString("marca").toUpperCase(), rs.getString("color").toUpperCase()
                    });
                }
                rs.close();
            }
            return dtm;
        } catch (SQLException e) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
public DefaultTableModel mostrarDatosFiltradosTablaAutomovil(DefaultTableModel dtm, String valorBuscar, String campo) {
        try {
             String sql ="select * from cliente_tabla where "+campo+" like upper('"+valorBuscar+"%')  order by "+campo+" asc";
            try (ResultSet rs = conectar.query(sql)) {
                while (rs.next()) {
                    dtm.addRow(new Object[]{rs.getString("placa").toUpperCase(),
                        rs.getString("modelo").toUpperCase(),
                        rs.getString("marca").toUpperCase(), rs.getString("color").toUpperCase()
                    });
                }
                rs.close();
            }
            return dtm;
        } catch (SQLException e) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    public void CargarComboMarca(JComboBox comboMarca) {
        try {
            String sql = "select * from marcaauto";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {
                comboMarca.addItem(rs.getString("marca"));
            }
        } catch (SQLException ex) {
            System.out.println("error combo");
        }
    }

    public void CargarComboModelo(JComboBox comboModelo, String marca) {
        comboModelo.removeAllItems();
        try {
            String sql = "select modelo from marca where marca = '" + marca + "'";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {
                comboModelo.addItem(rs.getString("modelo"));
            }
        } catch (SQLException ex) {
            System.out.println("error combo");
        }
    }
}
