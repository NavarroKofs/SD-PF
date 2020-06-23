package banco;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
 
/**
 * Server
 * 
 */
public class BankServer {
 
  public static void main(String[] args) {
    try {
      //Create and get reference to rmi registry
      Registry registry = LocateRegistry.createRegistry(1099);
 
      //Instantiate server object
      ProvinceObject po = new ProvinceObject();
 
      //Register server object
      registry.rebind("Bank", po);
      System.out.println("BankServer is created!!!");
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}

