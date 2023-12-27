package perpus.adminn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import perpus.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class updateController {
    public Button updateBook;
    public VBox vbListUpdate;
    public TextField tfSearchBookUpdate;
    //label untuk menampilkan data lama
    public Label lbCode;
    public Label lbJudul;
    public Label lbGenre;
    public Label lbTahun;
    public Label lbStok;

    //textfield untuk memperbarui data
    public TextField tfNewStok;
    public TextField tfNewYear;
    public TextField tfNewGenre;
    public TextField tfNewTitle;
    public TextField tfNewCode;
    public GridPane gpNew;
    public GridPane gplate;
    public ComboBox<String> tfSearchWithCOmbox;
    int bookId;

    public void initialized(){
        displayBook(bookId);
    }

    public void displayBook(int idBook){
        try (Connection connection = DatabaseConnector.connect()){
            String queryShowlateData = "SELECT * FROM buku WHERE id_buku = ?";
            try (PreparedStatement statementShowLate = connection.prepareStatement(queryShowlateData)){
                statementShowLate.setInt(1, idBook);
                ResultSet resultSetDataLate = statementShowLate.executeQuery();
                lbCode.setText(String.valueOf(resultSetDataLate.getInt("kode_buku")));
                lbJudul.setText(resultSetDataLate.getString("judul"));
                lbGenre.setText(resultSetDataLate.getString("genre"));
                lbTahun.setText(String.valueOf(resultSetDataLate.getInt("tahun_rilis")));
                lbStok.setText(String.valueOf(resultSetDataLate.getInt("stok")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateBook(){
    }
    public void showTitleBook(){
        ObservableList<String> listTitle = FXCollections.observableArrayList();
        String title = tfSearchBookUpdate.getText();
        try(Connection connection = DatabaseConnector.connect()){
            String queryShowTitle = "SELECT judul, id_buku FROM buku WHERE LIKE ?";
            try (PreparedStatement statmentShowTitle = connection.prepareStatement(queryShowTitle)){
                statmentShowTitle.setString(1,"%" + title + "%");
                ResultSet resultSearch = statmentShowTitle.executeQuery();
                while (resultSearch.next()){
                    listTitle.add(resultSearch.getString("judul"));
                    bookId = resultSearch.getInt("id_buku");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tfSearchWithCOmbox.setItems(listTitle);
    }

    public void getIdbook(String judul){
        try (Connection connection = DatabaseConnector.connect()) {
            String qurygetIdbook = "SELECT id_buku FROM buku WHERE judul = ?";
            try (PreparedStatement statementgetId = connection.prepareStatement(qurygetIdbook)){
                statementgetId.setString(1, judul);
                ResultSet resultGetId = statementgetId.executeQuery();
                bookId = resultGetId.getInt("id_buku");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //button handler
    public void btnAddBook(ActionEvent actionEvent) {
    }

    public void btnDeleteBook(ActionEvent actionEvent) {
    }

    public void btnUpdateBook(ActionEvent actionEvent) {
    }

    public void clikSearchUpdate(ActionEvent actionEvent) {
    }

    public void comboxClick(ActionEvent actionEvent) {
        String selectValue = tfSearchWithCOmbox.getValue();
        getIdbook(selectValue);
    }
}
