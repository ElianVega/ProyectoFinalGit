package Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

public class RegistroEmpleadoDB {

    private int codRegistro, codEmpelado;
    private String usuario;
    private String clave;
    ConexionDB conectar = new ConexionDB();
    private LocalTime Hora_E, Hora_S;
    private Time HoraPre;

    public RegistroEmpleadoDB() {
    }

    public RegistroEmpleadoDB(int codRegistro, String usuario, String clave) {
        this.codRegistro = codRegistro;
        this.usuario = usuario;
        this.clave = clave;
    }

    public int getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(int codRegistro) {
        this.codRegistro = codRegistro;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean InsertarUsuario() {
        String sql;
        sql = "Insert into registro";
        sql += " (usuario, clave)";
        sql += "VALUES ('" + getUsuario() + "', '" + getClave() + "')";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }

    public int ObtenerCodigo(String cedula) {
        try {
            String sql = "select cod_registro from registro where usuario = '" + cedula + "'";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {
                return (rs.getInt("cod_registro"));
            }
        } catch (SQLException ex) {
            System.out.println("codigo");
        }
        return 0;
    }

    public int VerificarUsuario(String cedula, String clave) {
        int codRegistro = 0;
        try {
            String sql = "select cod_registro from registro where usuario = '" + cedula + "' and clave = '" + clave + "'";
            ResultSet rs = conectar.query(sql);
            if (rs.next()) {
                codRegistro = (rs.getInt("cod_registro"));
            }
        } catch (SQLException ex) {
            System.out.println("codigo");
        }
        return codRegistro;
    }

    public String datoNotificacion(String cedula) {

        try {
            String sql = "select nombre,apellido, cedula from persona where cedula= '" + cedula + "'";
            ResultSet rs = conectar.query(sql);
            if (rs.next()) {
                return ("" + rs.getString("apellido") + " " + rs.getString("nombre") + "  C.I: " + rs.getString("cedula"));
            }
        } catch (SQLException ex) {
            System.out.println("notificacionErroea");
        }
        return null;
    }

    public boolean InsertarHoraEntrada(int Usuario) {
        LocalTime ahora = LocalTime.now();
        int hora = ahora.getHour();
        int minuto = ahora.getMinute();
        int segundo = ahora.getSecond();
        Hora_E = ahora;
        String HoraEntrada = (hora + ":" + minuto + ":" + segundo);
        String sqlE;
        try {
            sqlE = "SELECT hora_entrada_predeterminada from empleado where cod_registro = " + Usuario + "";
            ResultSet rs = conectar.query(sqlE);
            while (rs.next()) {
                HoraPre = (rs.getTime("hora_entrada_predeterminada"));
            }
        } catch (SQLException ex) {
            System.out.println("codigo");
        }
        System.out.println(HoraPre);
        String sql;
        sql = " insert into hora_de_marcaje VALUES(null,To_timestamp('" + HoraEntrada + "','HH24:MI:SS'),null,null,CalculaHorasTarde('" + HoraEntrada + "','" + HoraPre + "')," + Usuario + ")";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean InsertarHoraSalida(int Cod_Horas) {
        LocalTime ahora = LocalTime.now();
        int hora = ahora.getHour();
        int minuto = ahora.getMinute();
        int segundo = ahora.getSecond();
        Hora_S = ahora;
        String sql;
        sql = "Update hora_de_marcaje SET hora_salida_empleado = To_timestamp('" + hora + ":" + minuto + ":" + segundo + "','HH24:MI:SS') where cod_horas = " + Cod_Horas + "";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }

    public int ObtenerCodigoHoraMR() {//Mas reciente
        int codhoras = 0;
        try {
            String sql = "select max(cod_horas) from hora_de_marcaje";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {
                codhoras = (rs.getInt("max(cod_horas)"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("codigo mal");
        }
        return codhoras;
    }

    public boolean ObtenerHorasTrabajadas(int Cod_horas) {
        int hora = Hora_E.getHour();
        int minuto = Hora_E.getMinute();
        int segundo = Hora_E.getSecond();
        int hora1 = Hora_S.getHour();
        int minuto1 = Hora_S.getMinute();
        int segundo1 = Hora_S.getSecond();
        String sql;
        sql = "Update hora_de_marcaje SET Horas_trabajadas ="
                + " CalculaHorasTrabajadas('" + hora + ":" + minuto + ":" + segundo + "','" + hora1 + ":" + minuto1 + ":" + segundo1 + "') where cod_horas = " + Cod_horas + "";
        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }
}
