package perpus;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.IOException;

public class logincontroller {

    @FXML
    public TextField pwText;
    @FXML
    public TextField usNameText;
    @FXML
    public Label alertText;
    @FXML
    private Hyperlink btnDaftar;

    @FXML
    protected void btnLogin() {
        String username = usNameText.getText();
        String password = pwText.getText();

        if (!username.isEmpty() && !password.isEmpty()){
            boolean cekLogin = validasi.validatedLogin(username, password);
            if (cekLogin){
                alertText.setText("Login Sukses");
            }else {
                alertText.setText("Login gagal. Coba cek kembali username dan password anda!");
            }
        }else {
            alertText.setText("Kolom tidak boleh kosong!!");
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
}
