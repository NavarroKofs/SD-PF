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
 * @author lupy1
 */
public class Notificaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    private String RFCUsuario;
    private Date fecha;
    private float precioOperacion;
    private String estado;

    public Notificaciones() {
        
    }
    
    public Notificaciones(String RFCUsuario, Date fecha, float precioOperacion, String estado) {
        this.RFCUsuario = RFCUsuario;
        this.fecha = fecha;
        this.precioOperacion = precioOperacion;
        this.estado = estado;
    }

    public String getRFCUsuario() {
        return RFCUsuario;
    }

    public void setRFCUsuario(String RFCUsuario) {
        this.RFCUsuario = RFCUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getPrecioOperacion() {
        return precioOperacion;
    }

    public void setPrecioOperacion(float precioOperacion) {
        this.precioOperacion = precioOperacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Haz " + getEstado() + " por $" + getPrecioOperacion() + " una(s) accion(es) el " + getFecha() + ". Para más detalles revisar en la sección transacciones.";
    }
    
}
