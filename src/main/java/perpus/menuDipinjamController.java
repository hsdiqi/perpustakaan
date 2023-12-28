package perpus;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class menuDipinjamController {
    public VBox vbListDipinjam;
    public Button btnDipinjam;


    public void initialize() {
        loadDataFromDatabase();
    }

    public void loadDataFromDatabase() {
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "SELECT judul, genre, tahun_rilis, tanggal_pinjam FROM dipinjam WHERE peminjamId > 0";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {

                        String judul = resultSet.getString("judul");
                        String genre = resultSet.getString("genre");
                        int tahunRilis = resultSet.getInt("tahun_rilis");
                        String tglPinjam = resultSet.getString("tanggal_pinjam");

                        GridPane gridPane = createGridPane(judul, genre, tahunRilis, tglPinjam);
                        vbListDipinjam.getChildren().add(gridPane);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private GridPane createGridPane(String judul, String genre, int tahunRilis, String tanggalPinjam ) {
        GridPane gridPane = new GridPane();

        Label lbjudul = new Label(judul);
        Label lbgenre = new Label(genre);
        Label lbtahun = new Label(Integer.toString(tahunRilis));
        Label lbTglPinjam = new Label(tanggalPinjam);

        gridPane.addColumn(0, new Label("Judul:"), new Label("Genre:"), new Label("Tahun Rilis:"), new Label("Tanggal Pinjam:"), new Label(""));
        gridPane.addColumn(1, lbjudul, lbgenre, lbtahun, lbTglPinjam);

        return gridPane;
    }

    // Action btn in header
    public void btnRak(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-rak.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) btnDipinjam.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    public void btnSearch(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-search.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) btnDipinjam.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    //Handler logout
    public void btnLogout(ActionEvent actionEvent) {
        clearUserData();
        pindahKeLogin();
    }

    private void clearUserData() {
        nowSesion.username = null;
        nowSesion.setUserId(0);
        nowSesion.nama = null;
    }

    private void pindahKeLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Stage currentStage = (Stage) btnDipinjam.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
