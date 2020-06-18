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
    
    public static void
    
  public static int save(Province p) {
    int iRet = -1;
    try {
      Connection con = DBManager.getInstance().getConnection();
      String SQL = "INSERT INTO Province (Id, ShortName, Name) values(?,?,?)";
      
      PreparedStatement pstmt = con.prepareStatement(SQL);
      pstmt.setInt(1, p.getId());
      pstmt.setString(2, p.getShortName());
      pstmt.setString(3, p.getName());
 
      iRet = pstmt.executeUpdate();
 
      pstmt.close();
    } catch (SQLException se) {
      System.out.println(se);
    }
 
    return iRet;
  }
 
  public static int update(Province p) {
    int iRet = -1;
    try {
      Connection con = DBManager.getInstance().getConnection();
      String SQL = "UPDATE Province SET ShortName=?, Name=? WHERE Id=?";
      PreparedStatement pstmt = con.prepareStatement(SQL);
      pstmt.setString(1, p.getShortName());
      pstmt.setString(2, p.getName());
      pstmt.setInt(3, p.getId());
 
      iRet = pstmt.executeUpdate();
 
      pstmt.close();
    } catch (SQLException se) {
      System.out.println(se);
    }
 
    return iRet;
  }
 
  public static int delete(Province p) {
    int iRet = -1;
    try {
      Connection con = DBManager.getInstance().getConnection();
      String SQL = "DELETE FROM Province WHERE Id=?";
      PreparedStatement pstmt = con.prepareStatement(SQL);
      pstmt.setInt(1, p.getId());
 
      iRet = pstmt.executeUpdate();
 
      pstmt.close();
    } catch (SQLException se) {
      System.out.println(se);
    }
    return iRet;
  }
 
  public static void deleteAll() {
    Connection con = DBManager.getInstance().getConnection();
    try {
      con.setAutoCommit(false);
      String SQL = "DELETE FROM Province";
      PreparedStatement pstmt = con.prepareStatement(SQL);
 
      pstmt.executeUpdate();
      con.commit();
    } catch (SQLException se) {
      try {
        con.rollback();
      } catch (SQLException ise) {
      }
    } finally {
      try {
        con.setAutoCommit(true);
      } catch (SQLException fse) {
      }
    }
  }
 

 
  public static ArrayList findByName(String name) {
    ArrayList arr = new ArrayList();
 
    try {
      String QRY = "SELECT * FROM Province WHERE name LIKE(?) ORDER BY id";
      Connection con = DBManager.getInstance().getConnection();
      PreparedStatement pstmt = con.prepareStatement(QRY);
      pstmt.setString(1, "%" + name + "%");
      ResultSet rs = pstmt.executeQuery();
 
      while (rs.next()) {
        Province p = new Province();
        p.setId(rs.getInt("Id"));
        p.setShortName(rs.getString("ShortName"));
        p.setName(rs.getString("Name"));
        arr.add(p);
      }
 
      pstmt.close();
    } catch (SQLException se) {
      System.out.println(se);
    }
    return arr;
  }
}
