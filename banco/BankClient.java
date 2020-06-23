package banco;

import static java.lang.Integer.parseInt;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
 
/**
 * ProvinceClient: client application
 * 
 */
public class BankClient {
    private static boolean EXIT = false;
    private static String userRFC = "";
    private static Scanner entradaEscaner = new Scanner (System.in); 
 
    public static void main(String[] args) {
        try{            
            //Get reference to rmi registry server
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            IRemoteProvince rp = (IRemoteProvince) registry.lookup("Bank");
                     
            while(!EXIT){
                if(user_is_logged(rp)){
                    menu(rp);
                } else {  
                    System.out.println("RFC incorrecto");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
      }
    }

    public static void menu(IRemoteProvince rp) {
      String selected_option = "";
      try {
        while(!EXIT){
            System.out.print("**********Menu**********\n"+
                             "1.-Comprar\n"+
                             "2.-Vender\n"+
                             "3.-Notificaciónes\n"+
                             "4.-Salir\n"+
                             "\n introduzca un numero\n->");
            selected_option = entradaEscaner.nextLine();

            switch(selected_option){
                case "1":
                    execute_compra(rp);
                    break;
                case "2":
                    execute_venta(rp);
                    break;
                case "3":
                    execute_notificaciones(rp);
                    break;
                case "4":
                    EXIT=true;
                    break;
                default:
                    System.out.println("¡¡¡Opcion no valida!!!");
                    menu(rp);
                    break;
            }
        }
      } catch (Exception e) {
            System.out.println(e);
      }
    }

    private static boolean user_is_logged(IRemoteProvince rp) throws RemoteException {
        boolean is_logged=false;
        String RFC = "";        
        String Contraseña = "";

        System.out.print ("Por favor introduzca su RFC:\n->");
        RFC = entradaEscaner.nextLine();
        
        is_logged = rp.check_user(RFC);
        
        if (is_logged) userRFC = RFC;
        
       return is_logged;
    }

    private static void execute_compra(IRemoteProvince rp) throws RemoteException {
        try{
            ArrayList<String> response = new ArrayList<String>();
            ArrayList<String> infoTransaction = new ArrayList<String>();
            infoTransaction.add(userRFC);
            
            response = rp.showAllTransactions();
            Iterator it = response.iterator();
            int i = 1;
            
            System.out.println("Las opciones de compra son:");
            System.out.println("\n------RFC de empresas-----");
            while(it.hasNext()){
                System.out.println(i+".-"+it.next());
                i++;
            }
            System.out.println("------\n");
            System.out.print("Introduzca el numero de la opcion\n->");
            String option_selected = entradaEscaner.nextLine();        
            System.out.print("\nusted seleccionó ->"+ response.get(parseInt(option_selected)-1) +"\nestas seguro? s/n \n->");
            String confirmation = entradaEscaner.nextLine();        
            
            if(confirmation.toLowerCase().equals("s")){
               infoTransaction.add(response.get(parseInt(option_selected)-1));
               System.out.print("cuantas acciones desea comprar?\n->");
               String numAcciones = entradaEscaner.nextLine();
               System.out.print("cuantos deseas ofrecer por accion?\n->");
               String ofertaPorAccion = entradaEscaner.nextLine();
               
               
               //Cambiar
                infoTransaction.add(numAcciones);
                infoTransaction.add(ofertaPorAccion); 
               
               
               save_transaction(rp, infoTransaction);
               System.out.print("\n_____________________________________\n");
               System.out.print("\tOFERTA REALIZADA\n");
               System.out.print("_____________________________________\n\n");
            }else{
               System.out.println("\n!!!!La opción elegida no existe!!!!!\n");
               execute_compra(rp);
            }
                        
        }catch(Exception e) {
            System.out.println(e);
        }
    }

    private static void execute_venta(IRemoteProvince rp) throws RemoteException {        
        System.out.println("\nWIP venta\n");
    }

    private static void execute_notificaciones(IRemoteProvince rp) throws RemoteException {        
        System.out.println("\nWIP notificaciones\n");
    }

    private static void save_transaction(IRemoteProvince rp, ArrayList<String> infoTransaction) throws RemoteException {
        rp.startTransaction(infoTransaction);   
    }
}
