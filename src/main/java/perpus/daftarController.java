package perpus;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class daftarController {
    @FXML
    private TextField tfNama;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPassword;
    @FXML
    private Hyperlink btnMasuk;
    @FXML
    protected void btnDaftar(){
        String nama = tfNama.getText();
        String username = tfUsername.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        Alert alertText = new Alert(Alert.AlertType.INFORMATION);

        if (!nama.isEmpty() && !username.isEmpty() && !password.isEmpty() && !email.isEmpty()){
            if (cekEmail(email)){
                if (password.length() >= 6 && password.length() <= 50 ) {
                    if (!isUsernameExists(username)) {
                        if (username.length() >= 6 && username.length() <= 50){
                            user = addUserToDatabase(nama, username, email, password);
                            if (user != null){
                                System.out.println("Berhasil mendaftarkan " + DataSesi.nama);
                                alertText.setContentText("Pendaftaran Berhasil!!!");
                                alertText.showAndWait();
                                moveToLogin();
                            } else {
                                alertText.setContentText("Gagal melakukan pendaftaran. Silakan coba lagi.");
                                alertText.showAndWait();
                            }
                        }else {
                            alertText.setContentText("Username minimal 6 Karakter dan maksimals 50 karakter");
                            alertText.showAndWait();
                        }
                    } else {
                        alertText.setContentText("Username sudah digunakan. Silakan gunakan username lain.");
                        alertText.show();
                    }
                } else {
                    alertText.setContentText("Password minimal 6 karakter dan maksimal 50 karakter");
                    alertText.show();
                }
            } else{
                alertText.setContentText("Email invalid");
                alertText.show();
            }
        } else {
            alertText.setContentText("Kolom tidak boleh kosong!!");
            alertText.show();
        }
    }

    private boolean isUsernameExists(String username) {
        boolean exists = false;
        try {
            Connection connection = DatabaseConnector.connect();
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    @FXML
    protected void ChangeScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnMasuk.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DataSesi user;
    private DataSesi addUserToDatabase(String nama, String username, String email, String password){
        DataSesi user = null;
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (nama, username, email, password) VALUES (?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0){
                user = new DataSesi();
                DataSesi.nama = nama;
                DataSesi.email = email;
                DataSesi.username = username;
                DataSesi.password = password;

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    DataSesi.userId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to get user ID, no rows affected.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    private boolean cekEmail(String email) {
        Pattern regexPattern = Pattern.compile("^[a-zA-Z0-9_.]+@[a-zA-Z0-9.-]+$");
        Matcher regexMatcher = regexPattern.matcher(email);
        boolean isTrue = regexMatcher.find();
        return isTrue;
    }
    private void moveToLogin(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        try {
            Parent root =  loader.load();
            Scene newScene = new Scene(root);
            Stage currentStage = (Stage) btnMasuk.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
