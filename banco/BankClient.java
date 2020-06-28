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
    private static Scanner entradaEscaner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            //Get reference to rmi registry server
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            IRemoteProvince rp = (IRemoteProvince) registry.lookup("Bank");

            while (!EXIT) {
                if (user_is_logged(rp)) {
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
            while (!EXIT) {
                imprimirPortafolio(rp);
                System.out.print("**********Menu**********\n"
                        + "1.-Comprar\n"
                        + "2.-Vender\n"
                        + "3.-Notificaciones\n"
                        + "4.-Ver transacciones\n"
                        + "5.-Salir\n"
                        + "\n introduzca un numero\n->");
                selected_option = entradaEscaner.nextLine();

                switch (selected_option) {
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
                        execute_ver_transacciones(rp);
                        break;
                    case "5":
                        EXIT = true;
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
        boolean is_logged = false;
        String usuario = "";
        String contrasena = "";
        boolean salida = false;

        do {
            System.out.print("Por favor introduzca su RFC:\n->");
            usuario = entradaEscaner.nextLine();
            if (usuario.length() == 10) {
                salida = true;
            } else {
                System.out.println("El tamanio del RFC debe ser igual a 10");
            }

        } while (salida == false);

        System.out.print("\nPor favor introduzca su contraseña:\n->");
        contrasena = entradaEscaner.nextLine();

        is_logged = rp.check_user(usuario, contrasena);

        if (is_logged) {
            userRFC = usuario;
        }

        return is_logged;
    }

    private static void execute_compra(IRemoteProvince rp) throws RemoteException {
        try {
            ArrayList response = new ArrayList();
            ArrayList<String> infoTransaction = new ArrayList<String>();
            infoTransaction.add(userRFC);

            response = rp.showAll();

            System.out.println("Las opciones de compra son:");
            System.out.println("\n------RFC de empresas-----");

            for (int i = 0; i < response.size(); i++) {
                Compania comp = (Compania) response.get(i);
                System.out.println((i + 1) + ".-" + comp.toString());
            }

            System.out.println("------\n");
            System.out.print("Introduzca el numero de la opcion\n->");
            String option_selected = entradaEscaner.nextLine();
            System.out.print("\nusted seleccionó ->" + response.get(parseInt(option_selected) - 1) + "\nestas seguro? s/n \n->");
            String confirmation = entradaEscaner.nextLine();

            if (confirmation.toLowerCase().equals("s")) {
                Compania comp2 = (Compania) response.get(parseInt(option_selected) - 1);
                infoTransaction.add(comp2.getRFC());
                boolean salida = false;
                String numAcciones = null;
                String ofertaPorAccion = null;
                do {

                    System.out.print("cuantas acciones desea comprar?\n->");
                    numAcciones = entradaEscaner.nextLine();
                    int compra = Integer.parseInt(numAcciones);

                    int valor = (-1) * comp2.getNumAccionesDisp();

                    if ((compra > 0) && (compra < valor)) {
                        salida = true;
                    } else {
                        if (compra > valor) {
                            System.out.println("La cantidad maxima a comprar es: " + valor + ", por favor introduzca un valor menor");
                        } else {
                            System.out.println("Por favor ingrese un valor positivo");
                        }

                    }

                } while (salida == false);
                boolean accion = false;
                do {
                    System.out.print("cuanto desea ofrecer por accion?\n->");
                    ofertaPorAccion = entradaEscaner.nextLine();
                    float oferta = Float.parseFloat(ofertaPorAccion);
                    if (oferta > comp2.getValorActualAccion()) {
                        accion = true;

                    } else {
                        System.out.println("Por favor, haz una oferta superior o igual a " + comp2.getValorActualAccion());
                    }

                } while (accion == false);

                //Cambiar
                infoTransaction.add(numAcciones);
                infoTransaction.add(ofertaPorAccion);

                save_transaction(rp, infoTransaction);
                System.out.print("\n_____________________________________\n");
                System.out.print("\t HAZ ENTRADO A LA SUBASTA\n");
                System.out.print("_____________________________________\n\n");
            } else {
                System.out.println("\n!!!!La opción elegida no existe!!!!!\n");
                execute_compra(rp);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void execute_venta(IRemoteProvince rp) throws RemoteException {
        try {
            ArrayList response = new ArrayList();
            ArrayList<String> infoTransaction = new ArrayList<String>();
            infoTransaction.add(userRFC);
            boolean salida = false;
            response = rp.getPortafolio(userRFC);
            String numAcciones = null;
            int valor;
            System.out.println("De que compañia desea vender:");
            imprimirPortafolio(rp);

            System.out.print("Introduzca el numero de la opcion\n->");
            String option_selected = entradaEscaner.nextLine();
            System.out.print("\nusted seleccionó ->" + response.get(parseInt(option_selected) - 1) + "\nestas seguro? s/n \n->");
            String confirmation = entradaEscaner.nextLine();

            if (confirmation.toLowerCase().equals("s")) {
                Usuario info = (Usuario) response.get(parseInt(option_selected) - 1);
                infoTransaction.add(info.getRFCComp());
                do {
                    
                    System.out.print("cuantas acciones desea vender?\n->");
                    valor = Integer.parseInt(entradaEscaner.nextLine());
                    numAcciones = "-" + valor;
                    if(valor > info.getNumAcciones()){
                        System.out.println("Por favor, ingresa un valor menor o igual a " + info.getNumAcciones());
                    }else{
                        salida = true;
                    }
                } while (salida == false);

                System.out.print("cuantos desea pedir por accion?\n->");
                String ofertaPorAccion = entradaEscaner.nextLine();

                //Cambiar
                infoTransaction.add(numAcciones);
                infoTransaction.add(ofertaPorAccion);

                save_transaction(rp, infoTransaction);
                System.out.print("\n_____________________________________\n");
                System.out.print("\tSubasta publicada\n");
                System.out.print("_____________________________________\n\n");
            } else {
                System.out.println("\n!!!!La opción elegida no existe!!!!!\n");
                execute_compra(rp);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void execute_notificaciones(IRemoteProvince rp) throws RemoteException {
        ArrayList response = new ArrayList();
        response = rp.obtenerNotificaciones(userRFC, "comprado");

        System.out.println("\n°°°°°°°°Subastas ganadas°°°°°°°°");
        for (int i = 0; i < response.size(); i++) {
            System.out.println("->" + response.get(i).toString());
        }

        response = rp.obtenerNotificaciones(userRFC, "perdido");
        System.out.println("\n°°°°°°°°Subastas perdidas°°°°°°°°");
        for (int i = 0; i < response.size(); i++) {
            System.out.println("->" + response.get(i).toString());
        }
        System.out.println("\n°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°\n");

        response = rp.obtenerNotificaciones(userRFC, "vendido");
        System.out.println("\n°°°°°°°°Acciones vendidas°°°°°°°°");
        for (int i = 0; i < response.size(); i++) {
            System.out.println("->" + response.get(i).toString());
        }
        System.out.println("\n°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°\n");
    }

    private static void save_transaction(IRemoteProvince rp, ArrayList<String> infoTransaction) throws RemoteException {
        int cantidad = parseInt(infoTransaction.get(2));
        float oferta = parseFloat(infoTransaction.get(3));
        String RFCUsuario = infoTransaction.get(0);
        String RFCCompania = infoTransaction.get(1);

        Transaccion t = new Transaccion(RFCUsuario, RFCCompania, new java.sql.Timestamp(new java.util.Date().getTime()), cantidad, oferta);

        rp.enviarPropuesta(t);
    }

    private static void imprimirPortafolio(IRemoteProvince rp) throws RemoteException {
        ArrayList resp = rp.getPortafolio(userRFC);
        System.out.print("|||||||Portafolio|||||||\n");
        for (int i = 0; i < resp.size(); i++) {
            System.out.println((i + 1) + ".-" + resp.get(i).toString());
        }
        System.out.print("||||||||||||||||||||||||\n");
    }

    private static void execute_ver_transacciones(IRemoteProvince rp) throws RemoteException {
        ArrayList resp = rp.getTransacciones(userRFC);
        System.out.print("////////Transancciones////////\n");
        for (int i = 0; i < resp.size(); i++) {
            System.out.println((i + 1) + ".-" + resp.get(i).toString());
        }
        System.out.print("/////////////////////////////\n");
    }
}
