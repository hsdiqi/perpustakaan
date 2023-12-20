package perpus;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;
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
                user = addUserToDatabase(nama,username,email,password);
                if (user != null){
                    System.out.println("Berhasil mendaftarkan " + user.nama);
                }else{
                    System.out.println("Gagal melakukan pendaftaran");
                }
                alertText.setText("Pendaftaran Berhasil!!!");
            }else{
                alertText.setText("Email invalid");
            }
        }else{
            alertText.setText("Kolom tidak boleh kosong!!");
        }
    }

    public static User user;
    private User addUserToDatabase(String nama, String username, String email, String password){
        User user = null;
        try {
            Connection connection = DatabaseConnector.connect();
            Statement statement = connection.createStatement();
            String sqlQuery = "INSERT INTO users (nama, username, email, password)"+"VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, nama);
            preparedStatement.setString(2,username);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);

            //tambah row ke table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0){
                user = new User();
                user.nama = nama;
                user.email = email;
                user.username = username;
                user.password = password;
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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
