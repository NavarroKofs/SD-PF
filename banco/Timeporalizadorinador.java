/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Roberto Navarro
 */
public class Timeporalizadorinador extends Thread {
    public Timeporalizadorinador() {
        
    }
    public void run() 
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { 
            @Override 
            public void run() { 
                System.out.println("Hola");
            } 
        }, 1*60*100);
    }
    
}
