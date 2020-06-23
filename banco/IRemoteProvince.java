package banco;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRemoteProvince extends Remote {
    /*
  public int save(Province p) throws RemoteException;
  public int update(Province p) throws RemoteException;
  public int delete(Province p) throws RemoteException;
  public void deleteAll() throws RemoteException;
  public ArrayList findAll() throws RemoteException;
  public ArrayList findByName(String criteria) throws RemoteException;
*/
  public ArrayList showAll() throws RemoteException;
  public ArrayList showAllTransactions() throws RemoteException;
  public ArrayList getPortafiolio(String RFC) throws RemoteException;
  public void generarTransaccion(Transaccion t) throws RemoteException;
  public boolean check_user(String RFC) throws RemoteException;
  public void startTransaction(ArrayList<String> infoTransaction)throws RemoteException;
}
