package perpus;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class logincontroller {

    @FXML
    private TextField pwText;
    @FXML
    private TextField usNameText;
    private Alert alertText = new Alert(Alert.AlertType.INFORMATION);
    @FXML
    private Hyperlink btnDaftar;
    @FXML
    private Button btnLogin;

    public  static perpus.nowSesion nowSesion;
    @FXML
    protected void btnLogin() {
        String username = usNameText.getText();
        String password = pwText.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            try (Connection connection = DatabaseConnector.connect()) {
                String query = "SELECT id_user FROM users WHERE username = ?";
                try (PreparedStatement statementGetId = connection.prepareStatement(query)) {
                    statementGetId.setString(1, username);
                    ResultSet resultSet = statementGetId.executeQuery();
                    if (resultSet.next()) {
                        perpus.nowSesion.setUserId(resultSet.getInt("id_user"));
                    }
                    boolean cekLoginUser = validasi.validatedLogin(username, password);
                    boolean cekLoginAdmin = validasi.validateAdmin(username, password);
                    if (cekLoginUser) {
                        alertText.setContentText("Login Sukses" );
                        System.out.println(perpus.nowSesion.getUserId());
                        changeSceneIfSuccess();
                    } else if (cekLoginAdmin) {
                        alertText.setContentText("Salamat datang" + perpus.nowSesion.username);
                        changeSceneAdmin();

                    } else {
                        alertText.setContentText("Login gagal. Coba cek kembali username dan password anda!");
                    }
                }
            } catch (SQLException e) {
                alertText.setContentText("Kesalahan saat login: " + e.getMessage());
            }
        } else {
            alertText.setContentText("Kolom tidak boleh kosong!!");
        }
    }

    @FXML
    protected void ChangeScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("daftar.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnDaftar.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void changeSceneIfSuccess(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-dipinjam.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected void changeSceneAdmin(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-addBook.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
