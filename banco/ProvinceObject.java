package banco;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import java.rmi.server.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.Date;
 
/**
 * Server object
 * 
 */
public class ProvinceObject extends UnicastRemoteObject implements IRemoteProvince {
private static ArrayList<Temporizador> ongoingTransactions = new ArrayList<Temporizador>();
private static final long serialVersionUID = 11L;

public ProvinceObject() throws RemoteException {
    super();
  }

    @Override
    public ArrayList showAll() throws RemoteException {
      try {
        System.out.println("Invoke show all from " + getClientHost());
      } catch (ServerNotActiveException snae) {
        snae.printStackTrace();
      }
      return bancoRepository.showAll();
    }

    @Override
    public ArrayList getPortafolio(String RFC) throws RemoteException {
      try {
        System.out.println("Invoke get portafolio from " + getClientHost());
      } catch (ServerNotActiveException snae) {
        snae.printStackTrace();
      }
      return bancoRepository.getPortafolio(RFC);
    }

    @Override
    public void generarTransaccion(Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean check_user(String RFC) throws RemoteException {
      try {
        System.out.println("Invoke check_user from " + getClientHost());
      } catch (ServerNotActiveException snae) {
        snae.printStackTrace();
      }
      return bancoRepository.check_user(RFC);
    }
        
    @Override
    public void startTransaction(ArrayList<String> infoTransaction) throws RemoteException {
        Transaccion t = new Transaccion(infoTransaction.get(0),
                                        infoTransaction.get(1),
                                        new Date(),
                                        parseInt(infoTransaction.get(2)),
                                        parseFloat(infoTransaction.get(3)));
        
       startTimer(t);
    }
    
    public void startTimer (Transaccion t){
        ongoingTransactions.add(new Temporizador(t));
    }
    
    public void stopTimer (int index){
        ongoingTransactions.get(index).stop_timer();
        ongoingTransactions.remove(index);
    } 

    @Override
    public void enviarPropuesta(Transaccion t) throws RemoteException {
    try {
      System.out.println("Invoke enviarPropuesta from " + getClientHost());
    } catch (ServerNotActiveException snae) {
      snae.printStackTrace();
    }
      bancoRepository.enviarPropuesta(t);
    }

    @Override
    public ArrayList obtenerNotificaciones(String userRFC, String estado) throws RemoteException {
      try {
        System.out.println("Invoke obtener notificaciones from " + getClientHost());
      } catch (ServerNotActiveException snae) {
        snae.printStackTrace();
      }
      return bancoRepository.obtenerNotificaciones(userRFC, estado);
    }
}

