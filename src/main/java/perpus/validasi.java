package perpus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class validasi {
    private static final String logValidationQuery = "SELECT * FORM login WHERE username = ? AND password = ?";

    public static boolean validatedLogin(String username, String password){
        try (Connection connection = dataBaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(logValidationQuery)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
