package banco;
import java.sql.*;
import java.util.*;
 
/**
 * ProvinceRepository: data accessor
 * @author http://lycog.com
 */

public class bancoRepository {
    private static ArrayList subastas = new ArrayList();
    //private static ArrayList publicaciones = null;
    private static int id = 0;
    
    public static ArrayList showAll() {
        ArrayList arr = new ArrayList();
        try {
            String QRY = "SELECT * FROM companias ORDER BY RFC";
            Connection con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QRY);
            while (rs.next()) {
                Compania compania = new Compania();
                compania.setRFC(rs.getString("RFC"));
                compania.setNumAccionesTot(rs.getInt("numAccionesTot"));
                compania.setNumAccionesDisp(rs.getInt("numAccionesDisp"));
                compania.setValorActualAccion(rs.getFloat("valorActualAccion"));
                arr.add(compania);
            }
        } catch (SQLException se) {
            System.out.println(se);
        }
        return arr;
    }
    
    public static ArrayList getPortafolio(String RFC) {
        ArrayList arr = new ArrayList();
        try {
            String QRY = "SELECT * FROM usuarios WHERE RFCUsuario = '" + RFC + "'";
            Connection con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QRY);
            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setRFCUsuario(rs.getString("RFCUsuario"));
                transaccion.setRFCComp(rs.getString("RFCCompania"));
                transaccion.setAccionesOperadas(rs.getInt("numAcciones"));
                transaccion.setPrecioOperacion(rs.getFloat("ultPrecioCompra"));
                arr.add(transaccion);
            }
        } catch (SQLException se) {
            System.out.println(se);
        }
        return arr;
    }
    
    public static void generarTransaccion(Transaccion t) {
        try {
          Connection con = DBManager.getInstance().getConnection();
          String SQL = "INSERT INTO transacciones (RFCUsuario, RFCComp, fecha,"
                     + " accionesOperadas, precioOperacion) values(?,?,?,?,?)";

          PreparedStatement pstmt = con.prepareStatement(SQL);
          pstmt.setString(1, t.getRFCUsuario());
          pstmt.setString(2, t.getRFCComp());
          pstmt.setDate(3, null);
          pstmt.setInt(4, t.getAccionesOperadas());
          pstmt.setFloat(5, t.getPrecioOperacion());

          pstmt.executeUpdate();

          pstmt.close();
        } catch (SQLException se) {
          System.out.println(se);
        }
        
        /*try {
            String QRY = "INSERT INTO transacciones (RFCUsuario, RFCComp, fecha,"
            + " numAccionesOperadas, precioOperacion) values('" + t.getRFCUsuario() + "', '" +
            t.getRFCComp() + "', '" + t.getFecha() +  "', " + t.getAccionesOperadas() +
            ", " + t.getPrecioOperacion();
            Connection con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QRY);
        } catch(SQLException se){
            System.out.println(se);
        }
        System.out.println("Acción completada");*/
    }
    
    public static boolean check_user(String usuario, String contrasena){
        boolean user_exist = false;
        ArrayList<String> users = new ArrayList<String>();      
        
        try {
            String QRY = "SELECT * FROM users where usuario='"+usuario+"' AND contrasena='"+contrasena+"' limit 1";
            Connection con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QRY);
            
            while (rs.next()) { 
                users.add(rs.getString(1));
            }  
            
            if(users.size() > 0){
                user_exist=true;
            }
            
        } catch (SQLException se) {
            System.out.println(se);
        }                    
        
        return user_exist;
    }
        
    public static void actualizarAccionesDisponibles(float i, Transaccion t) {
        try {
           
            String QRY = "UPDATE companias SET valorActualAccion =" + t + "WHERE RFC=" + t.getRFCComp();
            Connection con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QRY);
        } catch(SQLException se){
            System.out.println(se);
        }
        System.out.println("Las acciones disponibles se han actualizado");
    }
 
  //Código enviar notificación de ganador de compra
    public static void notificarCompra(String RFC){
        String estado = "comprado";
        ArrayList notificaciones;
        notificaciones = obtenerNotificaciones(RFC, estado);
        
        System.out.println("Usted ha ganado las siguientes compras: ");
        for(int i = 0; i < notificaciones.size(); i++){
            if(notificaciones.isEmpty()){
                System.out.println("Usted no tiene compras ganadas");
            }
            System.out.println(notificaciones.get(i));
        }
    }
    //Código enviar notificación de rechazo de oferta
    public static void notificarRechazo(String RFC){
        String estado = "perdido";
        ArrayList notificaciones;
        notificaciones = obtenerNotificaciones(RFC, estado);
        
        System.out.println("Usted ha perdido las siguientes ventas: ");
        for(int i = 0; i < notificaciones.size(); i++){
            if(notificaciones.isEmpty()){
                System.out.println("Usted no tiene ventas perdidas");
            }
            System.out.println(notificaciones.get(i));
        }
    }
    //código enviar notificación de venta
    public static void notificarVenta(String RFC){
        String estado = "vendido";
        ArrayList notificaciones;
        notificaciones = obtenerNotificaciones(RFC, estado);
        
        System.out.println("Usted ha vendido: ");
        for(int i = 0; i < notificaciones.size(); i++){
            if(notificaciones.isEmpty()){
                System.out.println("Usted no tiene ventas");
            }
            System.out.println(notificaciones.get(i));
        }
    }
    
    public static void generarGanadorVenta(ArrayList propuestasCompras){
        String estado = "vendido";
        //Aquí se ordena el array de Transacciones

        Comparator<Integer> comparador = Collections.reverseOrder();
        Collections.sort(propuestasCompras, comparador);
        
        //Aquí agarro el primer elemento (el que tiene el MAYOR precio)
        generarTransaccion((Transaccion) propuestasCompras.get(0));
        almacenarNotificaciones((Transaccion)propuestasCompras.get(0), estado);

        
    }
    public static ArrayList obtenerNotificaciones(String RFC, String estado){
                ArrayList arr = new ArrayList();
        try {
            String QRY = "SELECT * FROM notificaciones WHERE RFCUsuario = '" + RFC + "' AND estado = '" + estado + "'";
            Connection con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QRY);
            while (rs.next()) {
                Notificaciones notificacion = new Notificaciones();
                notificacion.setRFCUsuario(rs.getString("RFCUsuario"));
                notificacion.setFecha(rs.getDate("fecha"));
                notificacion.setPrecioOperacion(rs.getFloat("precioOperacion"));
                notificacion.setEstado(rs.getString("estado"));
                arr.add(notificacion);
            }
        } catch (SQLException se) {
            System.out.println(se);
        }
        return arr;
    }
    public static void almacenarNotificaciones(Transaccion t, String estado){
          try {
            Connection con = DBManager.getInstance().getConnection();

            String QRY = "INSERT INTO notificaciones (RFCUsuario, fecha,"
            + "precioOperacion, estado) values(?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(QRY);
            pstmt.setString(1, t.getRFCUsuario());
            pstmt.setDate(2, null);
            pstmt.setFloat(3, t.getPrecioOperacion());
            pstmt.setString(4, estado);

            pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException se){
            System.out.println(se);
        }
        System.out.println("Acción completada");
    }
    
    public static void generarGanadorCompra(ArrayList publicaciones){
        String estado = "comprado";
         ArrayList rechazados;
        //Aquí se ordena el array de Transacciones
     
            Collections.sort(publicaciones);
            
        
        //Aquí agarro el primer elemento (el que tiene el MENOR precio) <-----Es diferente weee
        generarTransaccion((Transaccion) publicaciones.get(0));
        
        almacenarNotificaciones((Transaccion)publicaciones.get(0), estado);
                //rechazados 
        publicaciones.remove(0);
        if(publicaciones.size() > 0){
            rechazados = publicaciones;
            estado = "perdido";
            for(int i = 0; i < rechazados.size(); i++){
                almacenarNotificaciones((Transaccion)rechazados.get(i), estado);
            }
        }

    }
    
    /*public static void enviarPropuesta(Transaccion t) { 
        if(t.isCompra()) {
            //es compra
            añadirPropuestaCompra(t);
        }
        if (!t.isCompra()) {
            //es venta
            añadirPropuestaVenta(t);
        }
    }*/
    
    //Ahora recibe cualquier tipo de transacción
    public static int enviarPropuesta(Transaccion t){
        if(!subastas.isEmpty()){
            for (int i=0; i<subastas.size(); i++) {
                Subasta subastaActiva = (Subasta) subastas.get(i);
                ArrayList listaSubastasActivas = subastaActiva.getPropuestasCompras();
                for(int k=0; k<listaSubastasActivas.size(); k++) {
                    Transaccion transaccion = (Transaccion) listaSubastasActivas.get(k);
                    if((transaccion.getRFCComp() == t.getRFCComp()) && (transaccion.isCompra() == t.isCompra())) {
                        subastaActiva.setPropuestasCompras(t);
                        subastaActiva.setCompra(t.isCompra());
                        subastaActiva.startTimer();
                        subastas.set(i, subastaActiva);
                        return 1;
                    }
                }
            }
        }
        Subasta subasta = new Subasta(id);
        id++;
        subasta.setPropuestasCompras(t);
        subasta.setCompra(t.isCompra());
        subasta.startTimer();
        subastas.add(subasta);
        return 0;
    }
    
    /*
        public static void armarPropuesta(Subasta subasta, Transaccion t){
        subasta.setPropuestasCompras(t);
        subasta.setCompra(t.isCompra());
        subasta.startTimer();
    }
    */
    
    /*public static int añadirPropuestaCompra(Transaccion t){
        for (int i=0; i<subastas.size(); i++) {
            Subasta subastaActiva = (Subasta) subastas.get(i);
            ArrayList listaSubastasActivas = subastaActiva.getPropuestasCompras();
            for(int k=0; k<listaSubastasActivas.size(); k++) {
                Transaccion transaccion = (Transaccion) listaSubastasActivas.get(k);
                if(transaccion.getRFCComp() == t.getRFCComp()) {
                    subastaActiva.setPropuestasCompras(t);
                    subastaActiva.startTimer();
                    subastas.set(i, subastaActiva);
                    return 1;
                }
            }
        }
        Subasta subasta = new Subasta(id);
        id++;
        subasta.setPropuestasCompras(t);
        subasta.startTimer();
        subastas.add(subasta);
        return 0;
    }*/
    //Refactorizar ---
    /*
    public static int añadirPropuestaVenta(Transaccion t) {
        for (int i=0; i<publicaciones.size(); i++) {
            Subasta publicacionesPropuestas = (Subasta) publicaciones.get(i);
            ArrayList listaPublicacionesPropuestas = publicacionesPropuestas.getPropuestasCompras();
            for(int k=0; k<listaPublicacionesPropuestas.size(); k++) {
                Transaccion transaccion = (Transaccion) listaPublicacionesPropuestas.get(k);
                if(transaccion.getRFCComp() == t.getRFCComp()) {
                    publicacionesPropuestas.setPropuestasCompras(t);
                    publicacionesPropuestas.startTimer();
                    publicaciones.set(i, publicacionesPropuestas);
                    return 1;
                }
            }
        }
        Subasta publicacion = new Subasta(id);
        id++;
        publicacion.setPropuestasCompras(t);
        publicacion.startTimer();
        publicaciones.add(publicacion);
        return 0;
    }*/
    
    public static void generarGanadores(ArrayList propuestasCompras, String id, boolean isCompra) {
        actualizarListas(id);
        if(isCompra){
            generarGanadorCompra(propuestasCompras);
        } else {
            generarGanadorVenta(propuestasCompras);
        }
    }
    
    /*public static void actualizarListas(String id, boolean isCompra){
        //Refactorizar
        if(isCompra){
            for (int i=0; i<subastas.size(); i++){
                Subasta subastaActiva = (Subasta) subastas.get(i);
                String subastaId = subastaActiva.getId();
                if (subastaId == id){
                    subastas.remove(i);
                }
            }
        } else {
            for (int i=0; i<publicaciones.size(); i++){
                Subasta subastaActiva = (Subasta) publicaciones.get(i);
                String subastaId = subastaActiva.getId();
                if (subastaId == id){
                    publicaciones.remove(i);
                }
            }
        }
    }*/
    
    public static void actualizarListas(String id){
        for (int i=0; i<subastas.size(); i++){
            Subasta subastaActiva = (Subasta) subastas.get(i);
            String subastaId = subastaActiva.getId();
            if (subastaId == id){
                subastas.remove(i);
            }
        }
    }
    
    static ArrayList getAllTransactions() {
        ArrayList<String> transactions = new ArrayList<String>();  
        try {            
            String QRY = "SELECT RFCComp,precioOperacion,accionesOperadas FROM transacciones";
            Connection con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QRY);            
            
            while (rs.next()) { 
                transactions.add(rs.getString(1));
                System.out.println(transactions);
            }  
                        
        } catch (SQLException se) {
            System.out.println(se);
        }  
        return transactions;
    }
}
