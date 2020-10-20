
package Modelo;


public class TipoCliente {
    private int codTipo;
    private String tipoCliente;

    public TipoCliente() {
    }

    public TipoCliente(int codTipo, String tipoCliente) {
        this.codTipo = codTipo;
        this.tipoCliente = tipoCliente;
    }

    public int getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(int codTipo) {
        this.codTipo = codTipo;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    
    
}
