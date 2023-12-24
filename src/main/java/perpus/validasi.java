package perpus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class validasi {
    private static final String logValidationQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String getIdUserQuery = "SELECT id_user FROM users WHERE username =?";

    public static boolean validatedLogin(String username, String password){
        try (Connection connection = DatabaseConnector.connect();
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
    public static void getIdUser(String username){
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(getIdUserQuery)) {
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("id_user");
                dataSementara ds = new dataSementara();
                ds.setUserId(userId);
            } else {
                System.out.println("Username tidak ditemukan");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
