package banco;

import Province;
import java.sql.*;
import java.util.*;
 
/**
 * ProvinceRepository: data accessor
 * @author http://lycog.com
 */

public class bancoRepository {
    
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
            String QRY = "SELECT * FROM notificaciones WHERE RFCUsuario = '" + RFC + "AND estado =" + estado + "'";
            Connection con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QRY);
            while (rs.next()) {
                Notificaciones notificacion = new Notificaciones();
                notificacion.setRFCUsuario(rs.getString("RFCUsuario"));
                notificacion.setPrecioOperacion(rs.getFloat("ultPrecioCompra"));
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
              
            String QRY = "INSERT INTO notificaciones (RFCUsuario, fecha,"
            + "precioOperacion, estado) values('" + t.getRFCUsuario() + "', '" + t.getFecha() +  "', '" +
            "', '" + t.getPrecioOperacion() + "', '" + estado + "'";
            Connection con = DBManager.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QRY);
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
    
    
}
