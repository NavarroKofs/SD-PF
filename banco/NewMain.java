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
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        Temporizador tmp = new Temporizador();
        tmp.run();
    }
}
