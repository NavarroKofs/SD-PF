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
  public ArrayList getPortafolio(String RFC) throws RemoteException;
  public void generarTransaccion(Transaccion t) throws RemoteException;
  public boolean check_user(String usuario, String contrasena) throws RemoteException;
  public void startTransaction(ArrayList<String> infoTransaction)throws RemoteException;
  public void enviarPropuesta(Transaccion t)throws RemoteException;
  public ArrayList obtenerNotificaciones(String userRFC, String estado)throws RemoteException;
  public ArrayList getTransacciones(String userRFC)throws RemoteException;
}
