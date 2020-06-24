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
    private ArrayList propuestasCompras = new ArrayList();
    private boolean compra;
    private String id;
    private boolean flag = false;

    public Subasta(int id) {
        this.id = String.valueOf(id) + UUID.randomUUID();
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

    public void setCompra(boolean compra) {
        this.compra = compra;
    }
    
    public void switchFlag(){
        this.flag = !this.flag;
    }
    
    public void startTimer(){
        if(!this.flag){
            Timer timer = new Timer();
            switchFlag();
            timer.schedule(new TimerTask() {
                @Override 
                public void run() {
                    timer.cancel();
                    switchFlag();
                    bancoRepository.generarGanadores(propuestasCompras, getId(), isCompra());
                }
            }, 600);
        }
    }
}
