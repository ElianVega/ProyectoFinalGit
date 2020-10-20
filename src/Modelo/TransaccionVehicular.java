package Modelo;

import java.awt.Image;
import java.sql.Date;
import java.sql.Timestamp;

public class TransaccionVehicular {

    private int codTransaccion;
    private Timestamp horaEntradaV;//hora de entrada del vehiculo
    private Timestamp horaSalidaV;//hora de salida del vehiculo
    private Date fechaVehiculo;//fecha en la que se realizo la entrada y salida del vehiculo
    private Image foto;//foto del vehiculo al momento de la entrada
    private String placa;

    public TransaccionVehicular() {
    }

    public TransaccionVehicular(int codTransaccion, Timestamp horaEntradaV, Timestamp horaSalidaV, Date fechaVehiculo, Image foto, String placa) {
        this.codTransaccion = codTransaccion;
        this.horaEntradaV = horaEntradaV;
        this.horaSalidaV = horaSalidaV;
        this.fechaVehiculo = fechaVehiculo;
        this.foto = foto;
        this.placa = placa;
    }

    public int getCodTransaccion() {
        return codTransaccion;
    }

    public void setCodTransaccion(int codTransaccion) {
        this.codTransaccion = codTransaccion;
    }

    public Timestamp getHoraEntradaV() {
        return horaEntradaV;
    }

    public void setHoraEntradaV(Timestamp horaEntradaV) {
        this.horaEntradaV = horaEntradaV;
    }

    public Timestamp getHoraSalidaV() {
        return horaSalidaV;
    }

    public void setHoraSalidaV(Timestamp horaSalidaV) {
        this.horaSalidaV = horaSalidaV;
    }

    public Date getFechaVehiculo() {
        return fechaVehiculo;
    }

    public void setFechaVehiculo(Date fechaVehiculo) {
        this.fechaVehiculo = fechaVehiculo;
    }

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

}
