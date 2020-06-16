import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRemoteProvince extends Remote {
  public int save(Province p) throws RemoteException;
  public int update(Province p) throws RemoteException;
  public int delete(Province p) throws RemoteException;
  public void deleteAll() throws RemoteException;
  public ArrayList findAll() throws RemoteException;
  public ArrayList findByName(String criteria) throws RemoteException;
}
