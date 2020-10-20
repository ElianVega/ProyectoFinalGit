
package Modelo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;


public class Persona {
    
    // Atributos
    private String Cedula;
    private String Nombre;
    private String Apellido;
    private String Celular;
    private String Correo;
    private String Sexo;
    private Date fechaNacimiento;
    private int edad;
    
    public Persona() {
    }

    public Persona(String Cedula, String Nombre, String Apellido, String Celular, String Correo, String Sexo, Date fechaNacimiento, int edad) {
        this.Cedula = Cedula;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Celular = Celular;
        this.Correo = Correo;
        this.Sexo = Sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        LocalDate fechaEdad = getFechaNacimiento().toLocalDate();
        this.edad = Period.between(fechaEdad, LocalDate.now()).getYears();
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

   
}
