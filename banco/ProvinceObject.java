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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList getPortafiolio(String RFC) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public ArrayList showAllTransactions() throws RemoteException {
      try {
        System.out.println("Invoke GetAllTransactions from " + getClientHost());
      } catch (ServerNotActiveException snae) {
        snae.printStackTrace();
      }
      return bancoRepository.getAllTransactions();
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
}

