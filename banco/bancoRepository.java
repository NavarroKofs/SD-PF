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
 
    //------------------------------poiupio
 
    //Login
    
    //Aquí deberá ir el código de hacer una puja
    
    //Código para iniciar temporizador
    
    //Código para detener temporizador
    
    //Código para decidir al ganador de la acción
 
    //------------------------------poiupio
 
     //Código actualizar número de acciones disponibles de una compañía
        
    //Código enviar notificación de ganador de compra
    
    //Código enviar notificación de rechazo de oferta
    
    //código enviar notificación de venta
    
    //Actualizar precio por acción

    
    
}
