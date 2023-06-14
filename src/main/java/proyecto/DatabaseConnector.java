package proyecto;
import java.sql.*;

public class DatabaseConnector {
    private static final String url = "jdbc:sqlite:src/main/resources/basededatos.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}

