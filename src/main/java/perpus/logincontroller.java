package perpus;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import perpus.validasi;

public class logincontroller {

    @FXML
    public TextField pwText;
    @FXML
    public TextField usNameText;
    @FXML
    public Label alertText;

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
    public void linkSignClick() {
    }
}
