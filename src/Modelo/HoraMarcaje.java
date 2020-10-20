
package Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class HoraMarcaje {
    private int codHoras;
    private Timestamp horaEntradaE; // hora a la que el empleado se registra
    private Timestamp horaSalidaE; // hora a la que se registra la salidal del empleado
    private int horasTrabajadas; // hora trabajadas por parte del empleado
    private int horasTarde; //horas o tiempo en los qu el empleado a llegado tarde
    private int codregistro;
    ConexionDB conectar = new ConexionDB();

    public HoraMarcaje() {
    }

    public HoraMarcaje(int codHoras, Timestamp horaEntradaE, Timestamp horaSalidaE, int horasTrabajadas, int horasTarde, int codregistro) {
        this.codHoras = codHoras;
        this.horaEntradaE = horaEntradaE;
        this.horaSalidaE = horaSalidaE;
        this.horasTrabajadas = horasTrabajadas;
        this.horasTarde = horasTarde;
        this.codregistro = codregistro;
    }

    public int getCodHoras() {
        return codHoras;
    }

    public void setCodHoras(int codHoras) {
        this.codHoras = codHoras;
    }

    public Timestamp getHoraEntradaE() {
        return horaEntradaE;
    }

    public void setHoraEntradaE(Timestamp horaEntradaE) {
        this.horaEntradaE = horaEntradaE;
    }

    public Timestamp getHoraSalidaE() {
        return horaSalidaE;
    }

    public void setHoraSalidaE(Timestamp horaSalidaE) {
        this.horaSalidaE = horaSalidaE;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public int getHorasTarde() {
        return horasTarde;
    }

    public void setHorasTarde(int horasTarde) {
        this.horasTarde = horasTarde;
    }

    public int getCodregistro() {
        return codregistro;
    }

    public void setCodregistro(int codregistro) {
        this.codregistro = codregistro;
    }   
}
