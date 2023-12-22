package perpus.nyobaktok;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class nyobakController {

    @FXML
    public Button btnSearch;
    public AnchorPane mainpane;
    @FXML
    private VBox vbdata;

    public void initialize() {
        // Panggil loadDataFromDatabase saat inisialisasi controller
        loadDataFromDatabase();
    }

    public void loadDataFromDatabase() {
        try (Connection connection = dataBaseConnector2.connect()) {
            String query = "SELECT judul, genre, tahun_rilis, stok FROM buku WHERE stok > 0";
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

        Label lbjudul = new Label(judul);
        Label lbgenre = new Label(genre);
        Label lbtahun = new Label(Integer.toString(tahunRilis));
        Label lbStock = new Label(Integer.toString(stok));

        gridPane.addColumn(0, new Label("Judul:"), new Label("Genre:"), new Label("Tahun Rilis:"), new Label("Stok"), new Label(""));
        gridPane.addColumn(1, lbjudul, lbgenre, lbtahun, lbStock);

        return gridPane;
    }

    StackPane overlaySearch = new StackPane();

    public void btnSearch(ActionEvent actionEvent) {
        if (!mainpane.getChildren().contains(overlaySearch)) {
            mainpane.getChildren().add(overlaySearch); // Menambahkan overlay ke mainpane
            overlaySearch.setVisible(true);
            
            overlay();
        }else {
            overlaySearch.setVisible(false);
            overlay();
        }
    }

    public void overlay() {
        overlaySearch.setStyle("-fx-background-color: rgba(255,0,0,0.5);");
        overlaySearch.setPrefSize(300, 200);
        overlaySearch.setAlignment(Pos.CENTER);

        TextField tfSearch = new TextField();
        tfSearch.setPromptText("search");
        tfSearch.setPrefSize(300, 20);
        tfSearch.setStyle("-fx-background-radius: 25");

        overlaySearch.getChildren().add(tfSearch);

        overlaySearch.setOnMouseClicked(event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();

            double minX = overlaySearch.getBoundsInParent().getMinX();
            double minY = overlaySearch.getBoundsInParent().getMinY();
            double maxX = overlaySearch.getBoundsInParent().getMaxX();
            double maxY = overlaySearch.getBoundsInParent().getMaxY();

            boolean isClickedOutside = (x < minX || x > maxX || y < minY || y > maxY);

            if (isClickedOutside) {
                overlaySearch.setVisible(false);
            }
        });
    }
}