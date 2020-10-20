
package Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;


public class Color {
    private String codColor;
    private String color;
    ConexionDB conectar = new ConexionDB();

    public Color() {
    }

    public Color(String codColor, String color) {
        this.codColor = codColor;
        this.color = color;
    }

    public String getCodColor() {
        return codColor;
    }

    public void setCodColor(String codColor) {
        this.codColor = codColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    //sentencias SQL
    
    public void CargarComboColor(JComboBox comboColor){
        try {
            String sql = "select * from colorauto";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {
                comboColor.addItem(rs.getString("color"));
            }
        } catch (SQLException ex) {
            System.out.println("error combo");
        }
    }
}
