package perpus;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.regex.*;
public class daftarController {
    @FXML
    public TextField tfNama;
    @FXML
    public TextField tfUsername;
    @FXML
    public TextField tfEmail;
    @FXML
    public TextField tfPassword;
    @FXML
    public Label alertText;
    @FXML
    protected void btnDaftar(){
        String nama = tfNama.getText();
        String username = tfUsername.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();

        if (!nama.isEmpty() && !username.isEmpty() && !password.isEmpty() && !email.isEmpty()){
            if (cekEmail(email)){
                alertText.setText("Pendaftaran Berhasil!!!");
            }else{
                alertText.setText("Email invalid");
            }
        }else{
            alertText.setText("Kolom tidak boleh kosong!!");
        }
    }

    private boolean cekEmail(String email) {
        Pattern regexPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher regexMatcher = regexPattern.matcher(email);
        boolean isTrue = regexMatcher.find();
        if (isTrue){
            return true;
        }else{
            return false;
        }
    }


}
