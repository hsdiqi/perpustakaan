package perpus.adminn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import perpus.DatabaseConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class pengembalianController {
    @FXML
    private Button btnPengembalian;
    @FXML
    private Label lbNamaUser;
    @FXML
    private VBox vbListBukuUsers;
    @FXML
    private TextField searchUsername;
    private int idPEminjamm;


    private GridPane creatGridPane(ResultSet resultSet) throws SQLException {
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: rgba(204,204,204,0.4)");

        // Set columnConstraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.SOMETIMES);
        col1.setHalignment(HPos.LEFT);
        col1.setMaxWidth(100);
        col1.setMinWidth(10.0);
        col1.setPrefWidth(85.0);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.SOMETIMES);
        col2.setMaxWidth(30);
        col2.setMinWidth(10.0);
        col2.setPrefWidth(10);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.SOMETIMES);
        col3.setMaxWidth(190.0);
        col3.setMinWidth(10.0);
        col3.setPrefWidth(150.0);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.SOMETIMES);
        col4.setHalignment(HPos.CENTER);
        col4.setMaxWidth(120.0);
        col4.setMinWidth(50.0);
        col4.setPrefWidth(80.0);

        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);

        // Set rowConstraints
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(10.0);
        row1.setPrefHeight(20.0);
        row1.setVgrow(Priority.SOMETIMES);
        row1.setValignment(VPos.BOTTOM);

        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(10.0);
        row2.setPrefHeight(20.0);
        row2.setVgrow(Priority.SOMETIMES);

        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(10.0);
        row3.setPrefHeight(20.0);
        row3.setVgrow(Priority.SOMETIMES);

        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(10.0);
        row4.setPrefHeight(30.0);
        row4.setVgrow(Priority.SOMETIMES);
        row4.setValignment(VPos.TOP);

        gridPane.getRowConstraints().addAll(row1, row2, row3, row4);

        // label nama
        Label lbNJudul = new Label("Judul: ");
        Label lbNGenre = new Label("Genre: ");
        Label lbNTahun = new Label("Tahun Rilis: ");
        Label lbNborrowDate = new Label("tanggal pinjam: ");
        // label (:)
        Label lb1 = new Label(":");
        Label lb2 = new Label(":");
        Label lb3 = new Label(":");
        Label lb4 = new Label(":");
        // label isi
        Label lbIJudul = new Label(resultSet.getString("judul"));
        Label lbIGenre = new Label(resultSet.getString("genre"));
        Label lbITahun = new Label(resultSet.getString("tahun_rilis"));
        Label lbIDateBorrow = new Label(resultSet.getString("tanggal_pinjam"));
        int bokkID = resultSet.getInt("id_buku");

        Button btnBackk = new Button("kembalikan");
        btnBackk.setPrefWidth(80);
        btnBackk.setPrefHeight(7);
        btnBackk.getStyleClass().add("btnBack");
        VBox.setMargin(btnBackk, new Insets(0,3,10,0));
        btnBackk.setOnAction(actionEvent -> {
            backBook(bokkID);
        });

        // Add nodes to gridPane
        gridPane.add(lbNJudul, 0, 0);
        gridPane.add(lbNGenre, 0, 1);
        gridPane.add(lbNTahun, 0, 2);
        gridPane.add(lbNborrowDate, 0, 3);
        gridPane.add(lb1, 1, 0);
        gridPane.add(lb2, 1, 1);
        gridPane.add(lb3, 1, 2);
        gridPane.add(lb4, 1, 3);
        gridPane.add(lbIJudul, 2, 0);
        gridPane.add(lbIGenre, 2, 1);
        gridPane.add(lbITahun, 2, 2);
        gridPane.add(lbIDateBorrow, 2, 3);
        gridPane.add(btnBackk, 3, 3);

        return gridPane;
    }

    private void searchUsername(String username){
        String querygetIdUser = "SELECT id_user FROM users WHERE username = ?";
        String querySetData = "SELECT * FROM dipinjam WHERE peminjamId = ?";
        clearBook();
        try (Connection connection = DatabaseConnector.connect()){
            try (PreparedStatement statementSearch = connection.prepareStatement(querygetIdUser);
            PreparedStatement statementOut = connection.prepareStatement(querySetData)) {
                statementSearch.setString(1, username);
                ResultSet resultSetgetID = statementSearch.executeQuery();
                if (resultSetgetID.next()){
                    idPEminjamm = resultSetgetID.getInt("id_user");
                    statementOut.setInt(1, idPEminjamm);
                    ResultSet resultSetOutput = statementOut.executeQuery();
                    while (resultSetOutput.next()){
                        GridPane gridPane = creatGridPane(resultSetOutput);
                        vbListBukuUsers.getChildren().add(gridPane);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void clearBook(){
        vbListBukuUsers.getChildren().clear();
    }

    private void backBook(int idBook){
        try (Connection connection = DatabaseConnector.connect()){
            String deleteDataInDipinjam = "DELETE FROM dipinjam WHERE id_buku = ? AND peminjamId = ?";
            String tambahStok = "UPDATE buku SET stok = stok + 1 WHERE id_buku = ?";

            try (PreparedStatement statementBack = connection.prepareStatement(deleteDataInDipinjam)){
                statementBack.setInt(1, idBook);
                statementBack.setInt(2, idPEminjamm);
                statementBack.executeUpdate();
            }
            try (PreparedStatement statementplusStock = connection.prepareStatement(tambahStok)){
                statementplusStock.setInt(1, idBook);
                statementplusStock.executeUpdate();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Buku telah dikembalikan");
            alert.showAndWait();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void searchKlick(ActionEvent actionEvent) {
        String searcText = searchUsername.getText();
        if (!searcText.isEmpty()){
            clearBook();
            searchUsername(searcText);
        }else {
            Text txt = new Text("...");
            txt.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 25));
            vbListBukuUsers.getChildren().add(txt);
            vbListBukuUsers.setAlignment(Pos.CENTER);
        }
    }
    
    private void clear(){
        vbListBukuUsers.getChildren().clear();
    }



    //Handler btn in header
    @FXML
    private void addBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-addBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) btnPengembalian.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void btnDeleteBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-deleteBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) btnPengembalian.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void btnUpdateBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-updateBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) btnPengembalian.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
}
