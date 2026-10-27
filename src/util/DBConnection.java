
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/student_db";

    private static final String USER = "root";

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("STUDENT_DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new SQLException(
                    "Database password not set. "
                    + "Set the STUDENT_DB_PASSWORD environment variable.");
        }

        return DriverManager.getConnection(URL, USER, password);
    }
}
