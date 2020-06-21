package banco;
import java.sql.*;
import java.util.*;
 
/**
 * ProvinceRepository: data accessor
 * @author http://lycog.com
 */

public class bancoRepository {
    private static ArrayList subastas = null;
    private static ArrayList publicaciones = null;
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
    
    public static void enviarPropuesta(Transaccion t) { 
        if(t.isCompra()) {
            //es compra
            añadirPropuestaCompra(t);
        }
        if (!t.isCompra()) {
            //es venta
            añadirPropuestaVenta(t);
        }
    }
    
    public static void añadirPropuestaCompra(Transaccion t){
        if(subastas.isEmpty()){
            Subasta subasta = new Subasta(id);
            id++;
            subasta.setPropuestasCompras(t);
            subastas.add(subasta);
        } else {
            for (int i=0; i<subastas.size(); i++) {
                Subasta subastaActiva = (Subasta) subastas.get(i);
                ArrayList listaSubastasActivas = subastaActiva.getPropuestasCompras();
                for(int k=0; k<listaSubastasActivas.size(); k++) {
                    Transaccion transaccion = (Transaccion) listaSubastasActivas.get(k);
                    if(transaccion.getRFCComp() == t.getRFCComp()) {
                        subastaActiva.setPropuestasCompras(t);
                        subastas.set(i, subastaActiva);
                    }
                }
            } 
        }
    }
    
    public static void añadirPropuestaVenta(Transaccion t) {
        if(publicaciones.isEmpty()){
            Subasta publicacion = new Subasta(id);
            id++;
            publicacion.setPropuestasCompras(t);
            publicaciones.add(publicacion);
        } else {
            for (int i=0; i<publicaciones.size(); i++) {
                Subasta publicacionesPropuestas = (Subasta) publicaciones.get(i);
                ArrayList listaPublicacionesPropuestas = publicacionesPropuestas.getPropuestasCompras();
                for(int k=0; k<listaPublicacionesPropuestas.size(); k++) {
                    Transaccion transaccion = (Transaccion) listaPublicacionesPropuestas.get(k);
                    if(transaccion.getRFCComp() == t.getRFCComp()) {
                        publicacionesPropuestas.setPropuestasCompras(t);
                        publicaciones.set(i, publicacionesPropuestas);
                    }
                }
            }
        }
    }
    
    public static void generarGanadores(ArrayList propuestasCompras, String id, boolean isCompra) {
        actualizarListas(id, isCompra);
        if(isCompra){
            generarGanadorCompra(propuestasCompras);
        } else {
            generarGanadorVenta(propuestasCompras);
        }
    }
    
    public static void actualizarListas(String id, boolean isCompra){
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
    }
    
    public static void generarGanadorVenta(ArrayList propuestasCompras){
        //Aquí se ordena el array de Transacciones
        for(int i=0; i<propuestasCompras.size(); i++){
            
        }
        //Aquí agarro el primer elemento (el que tiene el MAYOR precio)
        generarTransaccion((Transaccion) propuestasCompras.get(0));
    }
    
    public static void generarGanadorCompra(ArrayList publicaciones){
        //Aquí se ordena el array de Transacciones
        for(int i=0; i<publicaciones.size(); i++){
            
        }
        //Aquí agarro el primer elemento (el que tiene el MENOR precio) <-----Es diferente weee
        generarTransaccion((Transaccion) publicaciones.get(0));
    }
}
