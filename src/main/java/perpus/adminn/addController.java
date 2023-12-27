package perpus.adminn;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import perpus.DatabaseConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addController {
    public Button addBook;
    public TextField tfKodeBook;
    public TextField tfJudul;
    public TextField tfGenre;
    public TextField tfTahun;
    public TextField tfStok;

    public void btnTambahBuku(ActionEvent actionEvent){
        int bookCode = Integer.parseInt(tfKodeBook.getText());
        String genre = tfGenre.getText();
        String judul = tfJudul.getText();
        int tahunRilis = Integer.parseInt(tfTahun.getText());
        int stok = Integer.parseInt(tfStok.getText());
        try (Connection connection = DatabaseConnector.connect()) {
            String addBookQuery = "INSERT INTO buku (kode_buku, genre, judul, tahun_rilis, stok) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement addBookStatment = connection.prepareStatement(addBookQuery)) {
                addBookStatment.setInt(1, bookCode);
                addBookStatment.setString(2, genre);
                addBookStatment.setString(3, judul);
                addBookStatment.setInt(4, tahunRilis);
                addBookStatment.setInt(5, stok);
                addBookStatment.executeQuery();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void btnAddBook(ActionEvent actionEvent) {
    }

    public void btnDeleteBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-deleteBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) addBook.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    public void btnUpdateBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-updateBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) addBook.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
}
