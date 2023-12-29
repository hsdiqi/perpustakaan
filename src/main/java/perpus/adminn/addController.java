package perpus.adminn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import perpus.DatabaseConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addController {
    @FXML
    private Button addBook;
    @FXML
    private TextField tfKodeBook;
    @FXML
    private TextField tfJudul;
    @FXML
    private TextField tfGenre;
    @FXML
    private TextField tfTahun;
    @FXML
    private TextField tfStok;

    @FXML
    private void btnTambahBuku(ActionEvent actionEvent){
        try {
            String kodeBookText = tfKodeBook.getText();
            String genreText = tfGenre.getText();
            String judulText = tfJudul.getText();
            String tahunText = tfTahun.getText();
            String stokText = tfStok.getText();

            if (kodeBookText.isEmpty() || genreText.isEmpty() || judulText.isEmpty() || tahunText.isEmpty() || stokText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Semua field harus diisi!");
                alert.showAndWait();
                return;
            }

            int bookCode = Integer.parseInt(kodeBookText);
            String genre = genreText;
            String judul = judulText;
            int tahunRilis = Integer.parseInt(tahunText);
            int stok = Integer.parseInt(stokText);

            try (Connection connection = DatabaseConnector.connect()) {
                String addBookQuery = "INSERT INTO buku (kode_buku, genre, judul, tahun_rilis, stok) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement addBookStatement = connection.prepareStatement(addBookQuery)) {
                    addBookStatement.setInt(1, bookCode);
                    addBookStatement.setString(2, genre);
                    addBookStatement.setString(3, judul);
                    addBookStatement.setInt(4, tahunRilis);
                    addBookStatement.setInt(5, stok);
                    addBookStatement.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Penambahan buku sukses!");
            alert.showAndWait();

            // Reset semua field ke kosong setelah penambahan buku berhasil
            tfKodeBook.setText("");
            tfGenre.setText("");
            tfJudul.setText("");
            tfTahun.setText("");
            tfStok.setText("");
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Pastikan input kode buku, tahun, dan stok adalah angka!");
            alert.showAndWait();
        }
    }


    //Handler btn in header
    @FXML
    private void btnDeleteBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-deleteBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) addBook.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void btnUpdateBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-updateBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) addBook.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void btnPengembalian(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-pengembalian.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) addBook.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
}
