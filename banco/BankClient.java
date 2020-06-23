package banco;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
 
/**
 * ProvinceClient: client application
 * 
 */
public class BankClient {
 
    public static void main(String[] args) {
        try{            
            //Get reference to rmi registry server
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            IRemoteProvince rp = (IRemoteProvince) registry.lookup("Bank");
            
            if(user_is_logged(rp)){
                System.out.println("123");
                menu(rp);
            }      
            
        } catch (Exception e) {
            System.out.println(e);
      }
    }

    public static void menu(IRemoteProvince rp) {
      try {
          
      } catch (Exception e) {
            System.out.println(e);
      }
    }

    private static boolean user_is_logged(IRemoteProvince rp) throws RemoteException {
        boolean is_logged=false;
        String RFC = "";        
        String Contraseña = "";

        
        Scanner entradaEscaner = new Scanner (System.in); 

        System.out.print ("Por favor introduzca su RFC:\n->");
        RFC = entradaEscaner.nextLine ();
        
        is_logged = rp.check_user(RFC);
                      
       return is_logged;
    }
  
  
  
}
