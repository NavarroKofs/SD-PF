import java.sql.*;
 
/**
 * DBManager: Singleton pattern
 *
 *
 **/
public final class DBManager {
 
  private static DBManager _instance = null;
  private Connection _con = null;
 
  public DBManager() {
  
 	_con = getMySQLConnection();
		
  }
 
  //Thread safe instatiate method
  public static synchronized DBManager getInstance() {
    if (_instance == null) {
      _instance = new DBManager();
    }
    return _instance;
  }
 
  public Connection getConnection() {
    return _con;
  }
 
  /**
   * Connection to MySQL Database
   */
  private static Connection getMySQLConnection() {
    Connection con = null;
 
    try {
 
      String strCon = "jdbc:mysql://127.0.0.1/test?user=rtuser&password=123";
      con = DriverManager.getConnection(strCon);
    } catch (SQLException se) {
      System.out.println(se);
    }
    return con;
  }
  
}
