package Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  private static final String url = "jdbc:sqlserver://localhost:1433;database=CK_Java;encrypt=true;trustServerCertificate=true;useUnicode=true;characterEncoding=UTF-8;";
  private static final String user = "sa";
  private static final String password = "18052006";

  // phương thức kết nối
  public static Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, user, password);
      System.out.print("Kết nối thành công! ");
    } catch (SQLException e) {
      System.err.printf("Lỗi kết nối! " + e.getMessage());
    }
    return connection;
  }

}