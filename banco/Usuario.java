/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.io.Serializable;

/**
 *
 * @author Roberto Navarro
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String RFCUsuario;
    private String RFCComp;
    private int numAcciones;
    private float ultPrecioCompra;
    
    public Usuario() {
        
    }
    
    public Usuario(String RFCUsuario, String RFCComp,
    int numAcciones, float ultPrecioCompra) {
        this.RFCUsuario = RFCUsuario;
        this.RFCComp = RFCComp;
        this.numAcciones = numAcciones;
        this.ultPrecioCompra = ultPrecioCompra;
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

    public int getNumAcciones() {
        return numAcciones;
    }

    public void setNumAcciones(int numAcciones) {
        this.numAcciones = numAcciones;
    }

    public float getUltPrecioCompra() {
        return ultPrecioCompra;
    }

    public void setUltPrecioCompra(float ultPrecioCompra) {
        this.ultPrecioCompra = ultPrecioCompra;
    }

    @Override
    public String toString() {
        return "RFCUsuario=" + RFCUsuario + ", RFCComp=" + RFCComp + ", numAcciones=" + numAcciones + ", ultPrecioCompra=" + ultPrecioCompra;
    }
}
