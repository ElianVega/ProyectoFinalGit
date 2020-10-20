
package Modelo;


public class Marca {
    private int codMarca;
    private String marca;
    private String modelo;

    public Marca() {
    }

    public Marca(int codMarca, String marca, String modelo) {
        this.codMarca = codMarca;
        this.marca = marca;
        this.modelo = modelo;
    }

    public int getCodMarca() {
        return codMarca;
    }

    public void setCodMarca(int codMarca) {
        this.codMarca = codMarca;
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
    
    
}
