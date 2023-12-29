package perpus.adminn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import perpus.DatabaseConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class updateController {

    @FXML
    private Label lbCode;
    @FXML
    private Label lbGenre;
    @FXML
    private Label lbJudul;
    @FXML
    private Label lbStok;
    @FXML
    private Label lbTahun;
    @FXML
    private TextField tfNewCode;
    @FXML
    private TextField tfNewGenre;
    @FXML
    private TextField tfNewStok;
    @FXML
    private TextField tfNewTitle;
    @FXML
    private TextField tfNewYear;
    @FXML
    private TextField tfSearchBookUpdate;
    @FXML
    private Button updateBook;
    @FXML
    private ComboBox<String> valueBook;
    private Connection connection;

    private void initialize() throws SQLException {
        connection = DatabaseConnector.connect();
        showTitle(tfSearchBookUpdate.getText());

        valueBook.setOnAction(event -> {
            String selectedTitle = valueBook.getValue();
            if (selectedTitle != null && !selectedTitle.isEmpty()){
                selectBook(selectedTitle);
            }
        });
    }
    private void showTitle(String searchTitle){
        searchTitle = tfSearchBookUpdate.getText();
        ObservableList<String> listTitle = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnector.connect()){
            String queryShowTitle = "SELECT judul FROM buku WHERE judul LIKE ?";
            try (PreparedStatement statementShowTitle = connection.prepareStatement(queryShowTitle)){
                statementShowTitle.setString(1, "%" + searchTitle + "%");
                ResultSet resultSet = statementShowTitle.executeQuery();
                while (resultSet.next()){
                    listTitle.add(resultSet.getString("judul"));
                }
                valueBook.setItems(listTitle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    void selectBook(String inputTitle) {
        try {
            String query = "SELECT * FROM buku WHERE judul = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, inputTitle);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lbCode.setText(resultSet.getString("kode_buku"));
                lbJudul.setText(resultSet.getString("judul"));
                lbGenre.setText(resultSet.getString("genre"));
                lbStok.setText(resultSet.getString("stok"));
                lbTahun.setText(resultSet.getString("tahun_rilis"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateBook(ActionEvent event) {
        try {
            int newCode = Integer.parseInt(tfNewCode.getText());
            String newGenre = tfNewGenre.getText();
            int newStok = Integer.parseInt(tfNewStok.getText());
            String newTitle = tfNewTitle.getText();
            int newYear = Integer.parseInt(tfNewYear.getText());

            if (newCode == 0 || newGenre.isEmpty() || newStok == 0 || newTitle.isEmpty() || newYear == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Semua field harus diisi!");
                alert.showAndWait();
            } else {
                String query = "UPDATE buku SET kode_buku=?, genre=?, stok=?, judul=?, tahun_rilis=? WHERE judul=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, newCode);
                statement.setString(2, newGenre);
                statement.setInt(3, newStok);
                statement.setString(4, newTitle);
                statement.setInt(5, newYear);
                statement.setString(6, lbJudul.getText());

//                testing
                System.out.println("Judul: "+newTitle + "\n" +
                        "kode buku: " + newCode + "\n" +
                        "Genre: " +  newGenre+
                        "\n tahun: " + newYear +
                        "\n stok: " + newStok +
                        "\npatokan judu: " + lbJudul.getText());
//                Testing

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Buku berhasil diupdate!");
                    alert.showAndWait();

                    // Mengosongkan label dan field input
                    lbCode.setText("");
                    lbGenre.setText("");
                    lbJudul.setText("");
                    lbStok.setText("");
                    lbTahun.setText("");
                    tfNewCode.setText("");
                    tfNewGenre.setText("");
                    tfNewStok.setText("");
                    tfNewTitle.setText("");
                    tfNewYear.setText("");
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void clikSearchUpdate(ActionEvent actionEvent) {
        String titleBookSearch = tfSearchBookUpdate.getText();
        showTitle(titleBookSearch);
        String judulBOOK = valueBook.getValue();
        if (judulBOOK != null && !judulBOOK.isEmpty()){
            selectBook(judulBOOK);
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("field kosong atau null");
        }
    }

    //handler btn in header
    @FXML
    private void btnDeleteBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-deleteBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) updateBook.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
    @FXML
    private void btnAddBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-addBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) updateBook.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void btnPengembalian(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-pengembalian.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) updateBook.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
}
