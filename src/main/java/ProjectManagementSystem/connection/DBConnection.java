package ProjectManagementSystem.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String URL = "jdbc:mysql://localhost:3306/homework?useSSL=false";
    private final String user = "root";
    private final String password = "root";
    private Connection connection;

    public DBConnection( ) {
    }

    public Connection getConnection( ) {
        try {
            connection = DriverManager.getConnection(URL, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}

