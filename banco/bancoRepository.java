package banco;
import java.sql.*;
import java.util.*;
 
/**
 * 
 * @author Roberto Navarro
 */

public class bancoRepository {
    private static ArrayList subastas = new ArrayList();
    
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
          pstmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
          pstmt.setInt(4, t.getAccionesOperadas());
          pstmt.setFloat(5, t.getPrecioOperacion());

          pstmt.executeUpdate();

          pstmt.close();
        } catch (SQLException se) {
          System.out.println(se);
        }
    }
    
    public static boolean check_user(String RFC){
        boolean user_exist = false;
        ArrayList<String> users = new ArrayList<String>();      
        
        try {
            String QRY = "SELECT * FROM usuarios where RFCUsuario='"+RFC+"' limit 1";
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
    
    public static void generarGanadorVenta(ArrayList propuestasCompras) throws SQLException{
        String estado = "vendido";
        //Aquí se ordena el array de Transacciones

        Comparator<Integer> comparador = Collections.reverseOrder();
        Collections.sort(propuestasCompras, comparador);
        
        //Aquí agarro el primer elemento (el que tiene el MAYOR precio)
        generarTransaccion((Transaccion) propuestasCompras.get(0));
        actualizarPortafolio((Transaccion) propuestasCompras.get(0));
        actualizarCompanias((Transaccion) propuestasCompras.get(0));
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
            pstmt.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
            pstmt.setFloat(3, t.getPrecioOperacion());
            pstmt.setString(4, estado);

            pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException se){
            System.out.println(se);
        }
        System.out.println("Acción completada");
    }
    
    public static void generarGanadorCompra(ArrayList publicaciones) throws SQLException{
        String estado = "comprado";
         ArrayList rechazados;
        //Aquí se ordena el array de Transacciones
     
        Collections.sort(publicaciones);

        //Aquí agarro el primer elemento (el que tiene el MENOR precio) <-----Es diferente weee
        generarTransaccion((Transaccion) publicaciones.get(0));
        actualizarPortafolio((Transaccion) publicaciones.get(0));
        actualizarCompanias((Transaccion) publicaciones.get(0));
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
    
    public static void generarGanadores(ArrayList propuestasCompras, String id, boolean isCompra) throws SQLException {
        actualizarListas(id);
        if(isCompra){
            generarGanadorCompra(propuestasCompras);
        } else {
            generarGanadorVenta(propuestasCompras);
        }
    }

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
    
    public static void actualizarPortafolio(Transaccion t) throws SQLException {
      ArrayList portafolio = new ArrayList();
      try {
        String QRY = "SELECT * from usuarios WHERE RFCUsuario ='" + t.getRFCUsuario() + "' AND RFCCompania = '" + t.getRFCComp() + "' LIMIT 1";
        Connection con = DBManager.getInstance().getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(QRY);
        while (rs.next()) {
            Usuario item = new Usuario();
            item.setRFCUsuario(rs.getString("RFCUsuario"));
            item.setRFCComp(rs.getString("RFCCompania"));
            item.setNumAcciones(rs.getInt("numAcciones"));
            item.setUltPrecioCompra(rs.getFloat("ultPrecioCompra"));
            portafolio.add(item);
        }
        PreparedStatement pstmt;
        if(portafolio.size() == 1) {
            Usuario item = new Usuario();
            item = (Usuario) portafolio.get(0);
            int numAcciones = t.getAccionesOperadas() + item.getNumAcciones();
            if(numAcciones == 0) {
                String SQL = "DELETE FROM usuarios WHERE RFCUsuario=? AND RFCCompania=?";
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1, t.getRFCUsuario());
                pstmt.setString(2, t.getRFCComp());
            } else {
                String SQL = "UPDATE usuarios SET numAcciones=?, ultPrecioCompra=? WHERE RFCUsuario=? AND RFCCompania=?";
                pstmt = con.prepareStatement(SQL);
                pstmt.setInt(1, numAcciones);
                pstmt.setFloat(2, t.getPrecioOperacion());
                pstmt.setString(3, t.getRFCUsuario());
                pstmt.setString(4, t.getRFCComp());
            }
                /*String SQL = "UPDATE usuarios SET numAcciones=?, ultPrecioCompra=? WHERE RFCUsuario=? AND RFCCompania=?";
                pstmt = con.prepareStatement(SQL);
                pstmt.setInt(1, numAcciones);
                pstmt.setFloat(2, t.getPrecioOperacion());
                pstmt.setString(3, t.getRFCUsuario());
                pstmt.setString(4, t.getRFCComp());*/
        } else {
            String SQL = "INSERT INTO usuarios (RFCUsuario, RFCCompania, numAcciones, ultPrecioCompra) values(?,?,?,?)";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, t.getRFCUsuario());
            pstmt.setString(2, t.getRFCComp());
            pstmt.setInt(3, t.getAccionesOperadas());
            pstmt.setFloat(4, t.getPrecioOperacion());
        }
        pstmt.executeUpdate();
        pstmt.close();
      } catch (SQLException se) {
        System.out.println(se);
      }
    }
    
    public static void actualizarCompanias(Transaccion t) {
      ArrayList companias = new ArrayList();
      try {
        String QRY = "SELECT * from companias WHERE RFC = '" + t.getRFCComp() + "' LIMIT 1";
        Connection con = DBManager.getInstance().getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(QRY);
        while (rs.next()) {
            Compania compania = new Compania();
            compania.setRFC(rs.getString("RFC"));
            compania.setNumAccionesTot(rs.getInt("numAccionesTot"));
            compania.setNumAccionesDisp(rs.getInt("numAccionesDisp"));
            compania.setValorActualAccion(rs.getFloat("valorActualAccion"));
            companias.add(compania);
        }
        PreparedStatement pstmt;
        Compania item = new Compania();
        item = (Compania) companias.get(0);
        int numAcciones = t.getAccionesOperadas() - item.getNumAccionesDisp();
        String SQL = "UPDATE companias SET numAccionesDisp=? WHERE RFC=?";
        pstmt = con.prepareStatement(SQL);
        pstmt.setInt(1, numAcciones);
        pstmt.setString(2, t.getRFCComp());
        pstmt.executeUpdate();
        pstmt.close();
      } catch (SQLException se) {
        System.out.println(se);
      }
    }
}
