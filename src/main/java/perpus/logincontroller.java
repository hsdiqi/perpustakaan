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
    public Label allertText;

    @FXML
    protected void btnLogin() {
        String username = usNameText.getText();
        String password = pwText.getText();

        if (!username.isEmpty() && !password.isEmpty()){
            boolean loginSucses = validasi.validatedLogin(username, password);
            if (loginSucses){
                allertText.setText("Login Sukses");
            }else {
                allertText.setText("Login gagal. Coba cek kembali username dan password anda!");
            }
        }else {
            allertText.setText("Kolom tidak boleh kosong!!");
        }

    }

    @FXML
    public void linkSignClick() {
    }
}
