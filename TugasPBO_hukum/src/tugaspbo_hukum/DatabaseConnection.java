package tugaspbo_hukum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/2210010499_hukum";
    private static final String USER = "root"; // ganti dengan username database Anda
    private static final String PASSWORD = ""; // ganti dengan password database Anda

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}