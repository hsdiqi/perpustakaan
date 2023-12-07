package perpus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectTry {
    private static final String URL_SQL = "jdbc:mysql://localhost:3306/percobaan";
    private static final String USERNAME_SQL = "hasbi";
    private static final String PASSWORD_SQL = "Hasbi593";

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(URL_SQL, USERNAME_SQL, PASSWORD_SQL);
    }
}
