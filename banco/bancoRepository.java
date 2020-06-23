package banco;
import java.sql.*;
import java.util.*;
 
/**
 * ProvinceRepository: data accessor
 * @author http://lycog.com
 */

public class bancoRepository {
    private static ArrayList subastas = null;
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
                transaccion.setRFCComp(rs.getString("RFCComp"));
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
            String QRY = "INSERT INTO transacciones (RFCUsuario, RFCComp, fecha,"
            + " numAccionesOperadas, precioOperacion) values('" + t.getRFCUsuario() + "', '" +
            t.getRFCComp() + "', '" + t.getFecha() +  "', '" + t.getAccionesOperadas() +
            "', '" + t.getPrecioOperacion() + "'";
            Connection con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QRY);
        } catch(SQLException se){
            System.out.println(se);
        }
        System.out.println("Acción completada");
    }
    
    public static boolean check_user(String RFC){
        boolean user_exist = true;
                
        
                
        
        return user_exist;
    }
        
    //Código actualizar número de acciones disponibles de una compañía
    
    //Aquí deberá ir el código de hacer una puja
    
    //Código para iniciar temporizador
    
    //Código para detener temporizador
    
    //Código para decidir al ganador de la acción
    
    //Código enviar notificación de ganador de compra
    
    //Código enviar notificación de rechazo de oferta
    
    //código enviar notificación de venta
    
    //Actualizar precio por acción
    
    //Login
    
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
    
    public static void generarGanadorVenta(ArrayList propuestasCompras){
        //Aquí se ordena el array de Transacciones
        for(int i=0; i<propuestasCompras.size(); i++){
            
        }
        //Aquí agarro el primer elemento (el que tiene el MENOR precio)
        generarTransaccion((Transaccion) propuestasCompras.get(0));
    }
    
    public static void generarGanadorCompra(ArrayList publicaciones){
        //Aquí se ordena el array de Transacciones
        for(int i=0; i<publicaciones.size(); i++){
            
        }
        //Aquí agarro el primer elemento (el que tiene el MAYOR precio) <-----Es diferente weee
        generarTransaccion((Transaccion) publicaciones.get(0));
    }
}
