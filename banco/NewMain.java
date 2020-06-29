/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.Date;
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
        Transaccion t = new Transaccion("123456789a", "123456789l", new Date(), 2, (float) 200.00);

        System.out.println(bancoRepository.enviarPropuesta(t)); 
    }
}
