 package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionDB {

    private static Connection conexion;
    private static Statement stm;

    private final String user = "PARKINGISTA";
    private final String clave = "1234";
    private final String url = "jdbc:oracle:thin:@localhost:1521:xe";

    public ConexionDB() {
        //cargar Driver Base
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Error de driver");
        }

        try {
            // conexion
            conexion = DriverManager.getConnection(url, user, clave);
        } catch (SQLException e) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Error de conexion");
        }
    }

    public ResultSet query(String sql) {
        try {
            stm = conexion.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            return resultado;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public SQLException noQuery(String nqry) {

        try {
            stm = conexion.createStatement();
            stm.execute(nqry);
            stm.close();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
            return ex;
        }
    }

    public static Connection getConexion() {
        return conexion;
    }

    public static void setConexion(Connection conexion) {
        ConexionDB.conexion = conexion;
    }

    public void CerrarConexion() throws SQLException {
        conexion.close();
    }
}
