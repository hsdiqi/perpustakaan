package perpus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class logincontroller {

    public TextField pwText;
    public TextField usNameText;
    public Label allertText;
    public Hyperlink signLink;

    @FXML
    protected void btnLogin(){
        if (pwText&&usNameText == true)
        allertText.setText("Login Sukses");
    }

    public void linkSignClick(ActionEvent actionEvent) {
    }
}
