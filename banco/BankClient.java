package banco;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
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
                    System.out.println("Usuario o contraseña incorrecto");
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
            imprimirPortafolio(rp);
            System.out.print("**********Menu**********\n"+
                             "1.-Comprar\n"+
                             "2.-Vender\n"+
                             "3.-Notificaciones\n"+
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
        String usuario = "";        
        String contrasena = "";

        System.out.print ("Por favor introduzca su usuario:\n->");
        usuario = entradaEscaner.nextLine();
        
        System.out.print ("\nPor favor introduzca su contraseña:\n->");
        contrasena = entradaEscaner.nextLine();
                
        is_logged = rp.check_user(usuario, contrasena);
        
        if (is_logged) userRFC = usuario;
        
       return is_logged;
    }

    private static void execute_compra(IRemoteProvince rp) throws RemoteException {
        try{
            ArrayList response = new ArrayList();
            ArrayList<String> infoTransaction = new ArrayList<String>();
            infoTransaction.add(userRFC);

            response = rp.showAll();
                        
            System.out.println("Las opciones de compra son:");
            System.out.println("\n------RFC de empresas-----");
         
            for(int i=0;i<response.size();i++){
                Compania comp = (Compania) response.get(i);
                System.out.println((i+1)+".-"+comp.toString());
            }
    
            System.out.println("------\n");
            System.out.print("Introduzca el numero de la opcion\n->");
            String option_selected = entradaEscaner.nextLine();        
            System.out.print("\nusted seleccionó ->"+ response.get(parseInt(option_selected)-1) +"\nestas seguro? s/n \n->");
            String confirmation = entradaEscaner.nextLine();        
            
            if(confirmation.toLowerCase().equals("s")){
               Compania comp2 = (Compania) response.get(parseInt(option_selected)-1);
               infoTransaction.add(comp2.getRFC());
               System.out.print("cuantas acciones desea comprar?\n->");
               String numAcciones = entradaEscaner.nextLine();
               System.out.print("cuantos desea ofrecer por accion?\n->");
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
        try{
            ArrayList response = new ArrayList();
            ArrayList<String> infoTransaction = new ArrayList<String>();
            infoTransaction.add(userRFC);

            response = rp.getPortafolio(userRFC);
                        
            System.out.println("De que compañia desea vender:");
            imprimirPortafolio(rp);
            
            System.out.print("Introduzca el numero de la opcion\n->");
            String option_selected = entradaEscaner.nextLine();        
            System.out.print("\nusted seleccionó ->"+ response.get(parseInt(option_selected)-1) +"\nestas seguro? s/n \n->");
            String confirmation = entradaEscaner.nextLine();        
            
            if(confirmation.toLowerCase().equals("s")){
               Transaccion comp2 = (Transaccion) response.get(parseInt(option_selected)-1);
               infoTransaction.add(comp2.getRFCComp());
               System.out.print("cuantas acciones desea vender?\n->");
               String numAcciones = "-"+entradaEscaner.nextLine();
               System.out.print("cuantos desea pedir por accion?\n->");
               String ofertaPorAccion = entradaEscaner.nextLine();
               
               
               //Cambiar
                infoTransaction.add(numAcciones);
                infoTransaction.add(ofertaPorAccion); 
               
               
               save_transaction(rp, infoTransaction);
               System.out.print("\n_____________________________________\n");
               System.out.print("\tVenta publicada\n");
               System.out.print("_____________________________________\n\n");
            }else{
               System.out.println("\n!!!!La opción elegida no existe!!!!!\n");
               execute_compra(rp);
            }
                        
        }catch(Exception e) {
            System.out.println(e);
        }
    }

    private static void execute_notificaciones(IRemoteProvince rp) throws RemoteException {        
        ArrayList response = new ArrayList();
        response = rp.obtenerNotificaciones(userRFC, "comprado");
        
        System.out.println("\n°°°°°°°°Acciones compradas°°°°°°°°");
        for (int i = 0; i < response.size(); i++) {
            System.out.println("->"+response.get(i).toString());
        }
        
        response = rp.obtenerNotificaciones(userRFC, "vendido");
        System.out.println("\n°°°°°°°°Acciones vendidas°°°°°°°°");
        for (int i = 0; i < response.size(); i++) {
            System.out.println("->"+response.get(i).toString());
        }
        System.out.println("\n°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°\n");
        
    }

    private static void save_transaction(IRemoteProvince rp, ArrayList<String> infoTransaction) throws RemoteException {
        int cantidad = parseInt(infoTransaction.get(2));
        float oferta = parseFloat(infoTransaction.get(3));
        String RFCUsuario = infoTransaction.get(0);
        String RFCCompania = infoTransaction.get(1);
        
        Transaccion t = new Transaccion(RFCUsuario, RFCCompania, new Date(), cantidad, oferta);

        rp.enviarPropuesta(t);   
    }

    private static void imprimirPortafolio(IRemoteProvince rp) throws RemoteException {
            ArrayList resp = rp.getPortafolio(userRFC);
            System.out.print("|||||||Portafolio|||||||\n");
            for (int i = 0; i < resp.size(); i++) {
                System.out.println((i+1)+".-"+ resp.get(i).toString());
            }
            System.out.print("||||||||||||||||||||||||\n");
    }
}
