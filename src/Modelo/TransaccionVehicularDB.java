
package Modelo;

import Vistas.VCamara;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

public class TransaccionVehicularDB {
    private int codTransaccion;
    private Timestamp horaEntradaV;//hora de entrada del vehiculo
    private Timestamp horaSalidaV;//hora de salida del vehiculo
    private Date fechaVehiculo;//fecha en la que se realizo la entrada y salida del vehiculo
    private Image foto;//foto del vehiculo al momento de la entrada
    private String placa;
    private static Statement stm;
    private static PreparedStatement pst;
    Blob imagenBlob = null;
    byte[] imgBytea = null;
    VCamara Vcam=new VCamara();
    ConexionDB conectar = new ConexionDB();

    public TransaccionVehicularDB() {
    }   
        
    public TransaccionVehicularDB(int codTransaccion, Timestamp horaEntradaV, Timestamp horaSalidaV, Date fechaVehiculo, Image foto, String placa) {
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
    
    
    public String ObtenerPropietario(String placa) {
        String nompro = "";
        try {
//            String sql = "nombre from persona p join cliente c on p.cedula = c.cedula_cliente join automovil a on a.cod_cliente = c.cod_cliente where a.placa = '"+placa+"' ";
            String sql = "SELECT * FROM NOMBREPROPIETARIO where placa = '"+placa+"'";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {
                nompro = (rs.getString("propietario"));
            }
            return nompro;
        } catch (SQLException ex) {
            System.out.println("error aqui");
            System.out.println(ex);
        }
        return nompro;
    }
    
    public boolean RegistrarTransaccion(String placa){
        LocalTime ahora = LocalTime.now();
        int hora = ahora.getHour();
        int minuto = ahora.getMinute();
        int segundo = ahora.getSecond();
        String sql;
//        sql = "Update hora_de_marcaje SET hora_salida_empleado = To_timestamp('" + hora + ":" + minuto + ":" + segundo + "','HH24:MI:SS') where cod_horas = " + Cod_Horas + "";
//          sql ="INSERT INTO TRANSACCIONES_VEHICULARES VALUES (null, To_timestamp('" + hora + ":" + minuto + ":" + segundo + "','HH24:MI:SS'), EMPTY_BLOB(),'"+placa+"',SYSDATE)";
          sql ="INSERT INTO TRANSACCIONES_VEHICULARES VALUES (null, To_timestamp('" + hora + ":" + minuto + ":" + segundo + "','HH24:MI:SS'), SYSDATE, EMPTY_BLOB(), '"+placa+"')";

        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            return false;
        }
    }
    
    //Proceso foto En la base
    public void GuardarFoto(Image fotoicon){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            BufferedImage img = deImangen(fotoicon);
            ImageIO.write(img, "JPG", baos);
            
        }catch(IOException e){
            e.printStackTrace();
        }
            
        try {
            imgBytea = baos.toByteArray();
            imagenBlob = new SerialBlob(imgBytea);
            } catch ( SerialException se ) {
                se.printStackTrace ();
            } catch ( SQLException sqle ) {
                sqle.printStackTrace ();
            }
        try {
            pst = conectar.getConexion().prepareStatement("update transacciones_vehiculares set foto = ? where cod_transaccion = (SELECT max(cod_transaccion) from transacciones_vehiculares)");
            pst.setBlob(1, imagenBlob.getBinaryStream());
            pst.execute();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionVehicular.class.getName()).log(Level.SEVERE, null, ex);
            
            System.out.println("se cago aqui xd");
            System.out.println(ex);
        }    
    }
    
    public BufferedImage deImangen(Image img){
        BufferedImage cambio = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = cambio.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        
        return cambio;
    }  
    //fin proceso foto
    
}
