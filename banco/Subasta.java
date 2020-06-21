/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 *
 * @author Roberto Navarro
 */
public class Subasta {
    private Temporizador timer;
    private ArrayList propuestasCompras = null;
    private boolean compra;
    private String id;

    public Subasta(int id) {
        this.id = String.valueOf(id) + UUID.randomUUID();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { 
            @Override 
            public void run() {
                timer.cancel();
                bancoRepository.generarGanadores(propuestasCompras, getId(), isCompra());
            }
        }, 1*60*100);
    }
    
    public String getId() {
        return this.id;
    }

    public ArrayList getPropuestasCompras() {
        return propuestasCompras;
    }

    public void setPropuestasCompras(Transaccion t) {
        this.propuestasCompras.add(t);
    }

    public boolean isCompra() {
        return this.compra;
    }
    
    
}
