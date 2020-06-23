/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.rmi.RemoteException;
import static java.rmi.server.RemoteServer.getClientHost;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author 3PX68LA_1909
 */
public class TransactionObject  extends UnicastRemoteObject implements IRemoteProvince  {

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
}
