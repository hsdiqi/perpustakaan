package perpus;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class forgotPWController {
    private TextField username;
    private VBox vbNewPas;
    private String usernameUSER;
//    private TextField tfNewPass;

    private void initialize() {
//        tfusername();
    }

    private void tfusername(ActionEvent actionEvent) {
        String usernameInput = username.getText();
        getUsername(usernameInput);
        if (usernameUSER != null && usernameInput.equals(usernameUSER)) {
            showNewPassField();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("username tidak ditemukan!");
            alert.show();
        }
    }

    private void getUsername(String username){
        try (Connection connection = DatabaseConnector.connect()){
            String querySearch = "SELECT username FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(querySearch)){
                statement.setString(1, username);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    usernameUSER = result.getString("username");
                } else {
                    usernameUSER = null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showNewPassField(){
        TextField tfNewPass = new TextField();
//        String newPass = tfNewPass.getText();
        Label lbNewPass = new Label("Masukkan password baru:");
        Button btnNewPass = new Button("Rubah password");
        VBox.setMargin(btnNewPass, new Insets(10,0,0,0));
        btnNewPass.setOnAction(event -> {updateNewPass(usernameUSER, tfNewPass.getText());});

        vbNewPas.getChildren().addAll(lbNewPass, tfNewPass, btnNewPass);
    }
    private void updateNewPass(String username, String password){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try (Connection connection = DatabaseConnector.connect()){
            String queryupdate = "UPDATE users SET password = ?  WHERE users.username = ?";
            if (!password.isEmpty()){
                if (password.length() >= 6 && password.length() <= 50){
                    try (PreparedStatement statement = connection.prepareStatement(queryupdate)){
                        statement.setString(1, password);
                        statement.setString(2, username);
                        statement.executeUpdate();
                        alert.setContentText("password berhasil dirubah!");
                        alert.showAndWait();
                        moveToLogin();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    alert.setContentText("Password minimal 6 karakter dan maksimal 50 karakter");
                    alert.showAndWait();
                }
            }else {
                alert.setContentText("Field tidak boleh kosong!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void moveToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) vbNewPas.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

}
