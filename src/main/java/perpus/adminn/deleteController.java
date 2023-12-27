package perpus.adminn;

import javafx.event.ActionEvent;
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

public class deleteController {
    public Button deleteBook;
    public VBox vbDeleteList;
    public TextField tfSearchDeleteBook;

    public GridPane creatGridPane(ResultSet resultSet) throws SQLException {
        GridPane gridPane = new GridPane();

        // Set columnConstraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.SOMETIMES);
        col1.setHalignment(HPos.CENTER);
        col1.setMaxWidth(80);
        col1.setMinWidth(10.0);
        col1.setPrefWidth(69.0);

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
        col4.setMaxWidth(70.0);
        col4.setMinWidth(10.0);
        col4.setPrefWidth(70.0);

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
        Label lbNStok = new Label("Stok: ");
        // label (:)
        Label lb1 = new Label(":");
        Label lb2 = new Label(":");
        Label lb3 = new Label(":");
        Label lb4 = new Label(":");
        // label isi
        Label lbIJudul = new Label(resultSet.getString("judul"));
        Label lbIGenre = new Label(resultSet.getString("genre"));
        Label lbITahun = new Label(resultSet.getString("tahun_rilis"));
        Label lbIStok = new Label(resultSet.getString("stok"));
        int bokkID = resultSet.getInt("id_buku");

        Button btnDelete = new Button("Delete");
        btnDelete.setPrefWidth(55);
        btnDelete.setPrefHeight(5);
        btnDelete.getStyleClass().add("btnDelete");
        VBox.setMargin(btnDelete, new Insets(0,0,10,0));
        btnDelete.setOnAction(actionEvent -> {
            deleted(bokkID);
        });

        // Add nodes to gridPane
        gridPane.add(lbNJudul, 0, 0);
        gridPane.add(lbNGenre, 0, 1);
        gridPane.add(lbNTahun, 0, 2);
        gridPane.add(lbNStok, 0, 3);
        gridPane.add(lb1, 1, 0);
        gridPane.add(lb2, 1, 1);
        gridPane.add(lb3, 1, 2);
        gridPane.add(lb4, 1, 3);
        gridPane.add(lbIJudul, 2, 0);
        gridPane.add(lbIGenre, 2, 1);
        gridPane.add(lbITahun, 2, 2);
        gridPane.add(lbIStok, 2, 3);
        gridPane.add(btnDelete, 3, 3);

        return gridPane;
    }

    public void searchDeleteClick(ActionEvent actionEvent) {
        String searchName = tfSearchDeleteBook.getText().trim();
        if (!searchName.isEmpty()){
            clearBook();
            searchBook(searchName);
        }else {
            Text txt = new Text("...");
            txt.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 20));
            vbDeleteList.getChildren().add(txt);
            vbDeleteList.setAlignment(Pos.CENTER);
        }

    }
    public void searchBook(String name){
        String query = "SELECT * FROM buku WHERE judul LIKE ?";
        clearBook();
        try (Connection connection = DatabaseConnector.connect()){
            try (PreparedStatement statementSearch = connection.prepareStatement(query)) {
                statementSearch.setString(1, "%" +name + "%");
                ResultSet resultSet = statementSearch.executeQuery();

                while (resultSet.next()){
                    GridPane gridPane = creatGridPane(resultSet);
                    vbDeleteList.getChildren().add(gridPane);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void clearBook(){
        vbDeleteList.getChildren().clear();
    }

    private void deleted(int idBuku) {
        String bookTitle = "";
        try (Connection connection = DatabaseConnector.connect()) {
            String getTitleQuery = "SELECT judul FROM buku WHERE id_buku = ?";
            try (PreparedStatement getTitleStatement = connection.prepareStatement(getTitleQuery)) {
                getTitleStatement.setInt(1, idBuku);
                ResultSet resultSet = getTitleStatement.executeQuery();
                if (resultSet.next()) {
                    bookTitle = resultSet.getString("judul");
                }
            }

            String deleteQuery = "DELETE FROM buku WHERE id_buku = ?";
            try (PreparedStatement statementDelete = connection.prepareStatement(deleteQuery)) {
                statementDelete.setInt(1, idBuku);
                statementDelete.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Buku '" + bookTitle + "' telah dihapus!");
        alert.showAndWait();

        clearBook();
    }


    public void btnAddBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-addBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) deleteBook.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }


    public void btnUpdateBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/perpus/admin/admin-updateBook.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) deleteBook.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
}
