package perpus.nyobaktok;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class nyobakController {

    @FXML
    private VBox vbdata;

    public void initialize() {
        // Panggil loadDataFromDatabase saat inisialisasi controller
        loadDataFromDatabase();
    }

    public void loadDataFromDatabase() {
        try (Connection connection = dataBaseConnector2.connect()) {
            String query = "SELECT judul, genre, tahun_rilis, stok FROM buku WHERE tahun_rilis > 1000";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Ambil data dari hasil query
                        String judul = resultSet.getString("judul");
                        String genre = resultSet.getString("genre");
                        int tahunRilis = resultSet.getInt("tahun_rilis");
                        int stok = resultSet.getInt("stok");

                        // Set label dengan data dari database
                        GridPane gridPane = createGridPane(judul, genre, tahunRilis, stok);
                        vbdata.getChildren().add(gridPane);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle eksepsi koneksi atau query
        }
    }

    private GridPane createGridPane(String judul, String genre, int tahunRilis, int stok) {
        GridPane gridPane = new GridPane();

        Label lblJ = new Label(judul);
        Label lblG = new Label(genre);
        Label lblThnR = new Label(Integer.toString(tahunRilis));
        Label lblS = new Label(Integer.toString(stok));

        gridPane.addColumn(0, new Label("Judul:"), new Label("Genre:"), new Label("Tahun Rilis:"), new  Label("Stok"), new Label(""));
        gridPane.addColumn(1, lblJ, lblG, lblThnR, lblS);

        return gridPane;
    }
}
