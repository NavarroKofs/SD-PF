package banco;

import java.rmi.server.*;
import java.rmi.*;
import java.util.ArrayList;
 
/**
 * Server object
 * 
 */
public class ProvinceObject extends UnicastRemoteObject implements IRemoteProvince {
 
  /**
	 * 
	 */
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
}

