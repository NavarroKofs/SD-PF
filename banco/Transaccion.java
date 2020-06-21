/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Roberto Navarro
 */
public class Transaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String RFCUsuario;
    private String RFCComp;
    private Date fecha;
    private int accionesOperadas;
    private float precioOperacion;
    
    public Transaccion() {
        
    }
    
    public Transaccion(String RFCUsuario, String RFCComp,
    Date fecha, int accionesOperadas, float precioOperacion) {
        this.RFCUsuario = RFCUsuario;
        this.RFCComp = RFCComp;
        this.fecha = fecha;
        this.accionesOperadas = accionesOperadas;
        this.precioOperacion = precioOperacion;
    }

    public String getRFCUsuario() {
        return RFCUsuario;
    }

    public void setRFCUsuario(String RFCUsuario) {
        this.RFCUsuario = RFCUsuario;
    }

    public String getRFCComp() {
        return RFCComp;
    }

    public void setRFCComp(String RFCComp) {
        this.RFCComp = RFCComp;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getAccionesOperadas() {
        return accionesOperadas;
    }

    public void setAccionesOperadas(int numAccionesOperadas) {
        this.accionesOperadas = numAccionesOperadas;
    }

    public float getPrecioOperacion() {
        return precioOperacion;
    }

    public void setPrecioOperacion(float precioOperacion) {
        this.precioOperacion = precioOperacion;
    }
    
    public boolean isCompra(){
        if(getAccionesOperadas() > 0){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Transacciones{" + "RFCUsuario=" + RFCUsuario + ", RFCComp=" + RFCComp + ", fecha=" + fecha + ", accionesOperadas=" + accionesOperadas + ", precioOperacion=" + precioOperacion + '}';
    }
}
