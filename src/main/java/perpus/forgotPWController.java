package perpus;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class forgotPWController {
    public TextField username;
    public VBox vbNewPas;
    private String usernameUSER;
    private TextField tfNewPass;

    public void tfusername(ActionEvent actionEvent) {
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

    public void getUsername(String username){
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

    public void showNewPassField(){
        Label lbNewPass = new Label("Masukkan password baru:");
        tfNewPass = new TextField();
        Button btnNewPass = new Button("Rubah password");
        String newPass = tfNewPass.getText();
        btnNewPass.setOnAction(event -> {updateNewPass(usernameUSER, newPass);});

        vbNewPas.getChildren().addAll(lbNewPass, tfNewPass, btnNewPass);
    }
    public void updateNewPass(String username, String password){
        try (Connection connection = DatabaseConnector.connect()){
            String queryupdate = "UPDATE users SET password = ?  WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(queryupdate)){
                statement.setString(1, password);
                statement.setString(2, username);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
