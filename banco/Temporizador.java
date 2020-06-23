/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.Timer;
import java.util.TimerTask;

public class Temporizador extends Thread {
    private Transaccion ongoing_transaction = null;
    private TimerTask transaction_timer = new Temporizador.MyTask();

    Temporizador(Transaccion t) {
        this.ongoing_transaction = t;
    }
    
    class MyTask extends TimerTask {
        public void run() {
           System.out.println("Task is running");
        }
    }    
    
    public void run() 
    {
        Timer timer = new Timer();
        
        timer.schedule(transaction_timer, 1*60*100);
    }
    
    public void stop_timer() 
    {
        transaction_timer.cancel();
    }
        
    public void finish_Transaction() {
        System.out.println("El ganador de la transaccion es"+this.ongoing_transaction.getRFCUsuario());
    }

    public Transaccion getOngoing_transaction() {
        return ongoing_transaction;
    }

    public void setOngoing_transaction(Transaccion ongoing_transaction) {
        this.ongoing_transaction = ongoing_transaction;
    }
    
    public String ongoing_buyer(){
        return ongoing_transaction.getRFCUsuario();
    }
    
    public float ongoing_offer(){
        return ongoing_transaction.getPrecioOperacion();
    }
}
