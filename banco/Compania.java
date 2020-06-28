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
public class Compania implements Serializable {
    private static final long serialVersionUID = 1L;
    private String RFC;
    private int numAccionesTot;
    private int numAccionesDisp;
    private float valorActualAccion;
    
    public Compania() {
        
    }
    
    public Compania(String RFC, int numAccionesTot, int numAccionesDisp, float valorActualAccion) {
        this.RFC = RFC;
        this.numAccionesTot = numAccionesTot;
        this.numAccionesDisp = numAccionesDisp;
        this.valorActualAccion = valorActualAccion;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public int getNumAccionesTot() {
        return numAccionesTot;
    }

    public void setNumAccionesTot(int numAccionesTot) {
        this.numAccionesTot = numAccionesTot;
    }

    public int getNumAccionesDisp() {
        return numAccionesDisp;
    }

    public void setNumAccionesDisp(int numAccionesDisp) {
        this.numAccionesDisp = numAccionesDisp;
    }

    public float getValorActualAccion() {
        return valorActualAccion;
    }

    public void setValorActualAccion(float valorActualAccion) {
        this.valorActualAccion = valorActualAccion;
    }

    @Override
    public String toString() {
        return "RFC=" + RFC + " numAccionesTot=" + numAccionesTot + " numAccionesDisp=" + -numAccionesDisp + " valorActualAccion=" + valorActualAccion;
    }
}
