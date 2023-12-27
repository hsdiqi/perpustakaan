package perpus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class nowSesion {
    public static String username;
    public static int userId;
    public static String nama;
    public static void setNama(){
        try (Connection connection = DatabaseConnector.connect()){
            String query = "SELECT nama, id_user FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                nowSesion.nama = resultSet.getString("nama");
                setUserId(resultSet.getInt("id_user"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setUsername(String username) {
        nowSesion.username = username;
    }
    public static int getUserId() {
        return userId;
    }
    public static void setUserId(int userId) {
        nowSesion.userId = userId;
    }
}
