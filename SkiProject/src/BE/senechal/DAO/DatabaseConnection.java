package BE.senechal.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:oracle:thin:@193.190.64.10:1522/xepdb1";
    private static final String USER = "STUDENT03_12";
    private static final String PASSWORD = "changeme";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
