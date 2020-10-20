package Modelo;

import Vistas.CRUD.VCrud;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Empleado extends Persona {

    private int codEmpleado;
    private String cedualEmpleado;
    private Time horaEntradaPre;
    private Time horaSalidaPre;
    private int codRegistro;
    ConexionDB conectar = new ConexionDB();

    public Empleado() {
    }

    public Empleado(Time horaEntradaPre, Time horaSalidaPre, String Cedula, String Nombre, String Apellido, String Celular, String Correo, String Sexo, Date fechaNacimiento, int edad) {
        super(Cedula, Nombre, Apellido, Celular, Correo, Sexo, fechaNacimiento, edad);
        this.horaEntradaPre = horaEntradaPre;
        this.horaSalidaPre = horaSalidaPre;
    }

    public Empleado(int codEmpleado, String cedualEmpleado, Time horaEntradaPre, Time horaSalidaPre, int codRegistro, String Cedula, String Nombre, String Apellido, String Celular, String Correo, String Sexo, Date fechaNacimiento, int edad) {
        super(Cedula, Nombre, Apellido, Celular, Correo, Sexo, fechaNacimiento, edad);
        this.codEmpleado = codEmpleado;
        this.cedualEmpleado = cedualEmpleado;
        this.horaEntradaPre = horaEntradaPre;
        this.horaSalidaPre = horaSalidaPre;
        this.codRegistro = codRegistro;
    }

    public Empleado(String cedualEmpleado, Time horaEntradaPre, Time horaSalidaPre, int codRegistro) {
        this.cedualEmpleado = cedualEmpleado;
        this.horaEntradaPre = horaEntradaPre;
        this.horaSalidaPre = horaSalidaPre;
        this.codRegistro = codRegistro;
    }

    public int getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(int codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public String getCedualEmpleado() {
        return cedualEmpleado;
    }

    public void setCedualEmpleado(String cedualEmpleado) {
        this.cedualEmpleado = cedualEmpleado;
    }

    public Time getHoraEntradaPre() {
        return horaEntradaPre;
    }

    public void setHoraEntradaPre(Time horaEntradaPre) {
        this.horaEntradaPre = horaEntradaPre;
    }

    public Time getHoraSalidaPre() {
        return horaSalidaPre;
    }

    public void setHoraSalidaPre(Time horaSalidaPre) {
        this.horaSalidaPre = horaSalidaPre;
    }

    public int getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(int codRegistro) {
        this.codRegistro = codRegistro;
    }

    public boolean InsertarPersona() {
        String sql;
        sql = "Insert into persona";
        sql += " (cedula, nombre, apellido, celular, correo, sexo, fecha_nacimiento, edad, eliminado)";
        sql += " VALUES('" + getCedula() + "','" + getNombre() + "','" + getApellido() + "','"
                + getCelular() + "','" + getCorreo() + "','"
                + getSexo() + "',TO_DATE('" + getFechaNacimiento() + "','YYYY-MM-DD')," + getEdad() + ", 1)";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean InsertarEmpleado() {
        String sql;
        sql = "Insert into empleado";
        sql += " (cedula_empleado, hora_entrada_predeterminada, hora_salida_predeterminada, cod_registro)";
        sql += "VALUES ('" + getCedualEmpleado() + "', TO_TIMESTAMP('" + getHoraEntradaPre() + "', 'HH24:MI:SS'), TO_TIMESTAMP('" + getHoraSalidaPre() + "', 'HH24:MI:SS'), " + getCodRegistro() + ")";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }

    public DefaultTableModel mostrarDatosTablaEmpleado(DefaultTableModel dtm) {
        try {
            String sql = "select * from empleado_tabla";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {
                String horaentrada = rs.getString("horadeentrada");
                String horasalida = rs.getString("horadesalida");
                dtm.addRow(new Object[]{rs.getString(1).toUpperCase(), rs.getString(2).toUpperCase(), 
                    rs.getString(3).toUpperCase(), rs.getString(4).toUpperCase(),
                    Time.valueOf(horaentrada + ":" + 00), Time.valueOf(horasalida + ":" + 00)
                });
            }
            return dtm;
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public DefaultTableModel mostrarDatosFiltradosTablaEmpleado(DefaultTableModel dtm,String valorBuscar, String campo) {
        try {
            String sql = "select * from empleado_tabla where "+campo+" like upper('"+valorBuscar+"%')  order by "+campo+" desc";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {
                String horaentrada = rs.getString("horadeentrada");
                String horasalida = rs.getString("horadesalida");
                dtm.addRow(new Object[]{rs.getString(1).toUpperCase(), rs.getString(2).toUpperCase(),
                    rs.getString(3).toUpperCase(), rs.getString(4).toUpperCase(),
                    Time.valueOf(horaentrada + ":" + 00), Time.valueOf(horasalida + ":" + 00)
                });
            }
            return dtm;
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void mostrarDatoEditarEmpleado(String id_empleado, VCrud vistaE) {
        System.out.println("entra al metodo");
        try {
            String sql = "select p.cedula, p.nombre, p.apellido, p.celular, p.correo, p.sexo, p.fecha_nacimiento, "
                    + "to_number(extract(hour from hora_entrada_predeterminada)) as horaentrada, "
                    + "to_number(extract(minute from hora_entrada_predeterminada)) as minutoentrada, "
                    + "to_number(extract(hour from hora_salida_predeterminada)) as horasalida, "
                    + "to_number(extract(minute from hora_salida_predeterminada)) as minutosalida "
                    + "from persona p join empleado e on e.cedula_empleado = p.cedula "
                    + "where p.cedula = '" + id_empleado + "'";
            try (ResultSet rs = conectar.query(sql);) {
                System.out.println("envia la consulta");
                while (rs.next()) {
                    vistaE.getTxtCedulaAdmin().setText(rs.getString("cedula"));
                    vistaE.getTxtNombreAdmin().setText(rs.getString("nombre"));
                    vistaE.getTxtApellidoAdmin().setText(rs.getString("apellido"));
                    vistaE.getTxtCelularAdmin().setText(rs.getString("celular"));
                    vistaE.getTxtCorreoAdmin().setText(rs.getString("correo"));
                    vistaE.getjDateFechaAdmin().setDate(rs.getDate("fecha_nacimiento"));
                    vistaE.getjComboSexoAdmin().setSelectedItem(rs.getString("sexo"));
                    vistaE.getClave1().setText("**********");
                    vistaE.getEntradaHoras1().setValue(rs.getInt("horaentrada"));
                    vistaE.getEntradaMinutos1().setValue(rs.getInt("minutoentrada"));
                    vistaE.getSalidaHoras1().setValue(rs.getInt("horasalida"));
                    vistaE.getSalidaMinutos1().setValue(rs.getInt("minutosalida"));
                }
                rs.close();
            }

        } catch (Exception e) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, e);
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

    public boolean ActualizarEmpleado() {
        String nsql = "update empleado set hora_entrada_predeterminada = TO_TIMESTAMP('" + getHoraEntradaPre() + "', 'HH24:MI:SS'), "
                + "hora_salida_predeterminada = TO_TIMESTAMP('" + getHoraSalidaPre() + "', 'HH24:MI:SS') where cedula_empleado = '" + getCedula() + "'";
        if (conectar.noQuery(nsql) == null) {
            return true;
        } else {
            return false;
        }
    }

}
