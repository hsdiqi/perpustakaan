package perpus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MenuRakController implements Initializable {

    public Button btnRak;
    @FXML
    private VBox vbRakList;  // VBox to contain GridPanes
    ImageView likeImage = new ImageView(new Image(getClass().getResource("/perpus/assets/heart.png").toExternalForm()));



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "SELECT id_buku, judul, genre, tahun_rilis, stok  FROM buku"; // Replace with your actual table name
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Create a new GridPane for each row of data
                    GridPane gridPane = createGridPane(resultSet);
                    vbRakList.getChildren().add(gridPane);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private GridPane createGridPane(ResultSet resultSet) throws SQLException {
        int idBuku = resultSet.getInt("id_buku");
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("GPList");

        // Set up column constraints
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

        // Set up row constraints
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

        Button btnPinjam = new Button("Pinjam");
        btnPinjam.setPrefWidth(55);
        btnPinjam.setPrefHeight(5);
        btnPinjam.getStyleClass().add("btn_pinjam");
        VBox.setMargin(btnPinjam, new Insets(0,0,10,0));
        btnPinjam.setOnAction(actionEvent -> {
            btnPinjamClicked(idBuku);
        });

        ImageView likeImage = new ImageView(new Image(getClass().getResource("/perpus/assets/heart.png").toExternalForm()));
        likeImage.getStyleClass().add("image_heart");
        likeImage.setFitHeight(17.0);
        likeImage.setFitWidth(16.0);
        likeImage.setOnMouseClicked(event -> {
            heartClicked(idBuku);
        });

        // Add nodes to the gridPane
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
        gridPane.add(btnPinjam, 3, 3);
        gridPane.add(likeImage, 3, 0);

        return gridPane;
    }

    private void btnPinjamClicked(int bookId) {
        LocalDate tanggalPinjam = LocalDate.now();

        try (Connection connection = DatabaseConnector.connect()) {
            String cekStok = "SELECT stok FROM buku WHERE id_buku = ?";
            String cekDipinjam = "SELECT id_buku FROM dipinjam WHERE peminjamId = ?";
            try (PreparedStatement getStokStatement = connection.prepareStatement(cekStok);
                 PreparedStatement cekBukuDipinjam = connection.prepareStatement(cekDipinjam)) {
                cekBukuDipinjam.setInt(1, DataSesi2.getUserId());
                getStokStatement.setInt(1, bookId);
                try (ResultSet checkStok = getStokStatement.executeQuery();
                     ResultSet checkDipinjam = cekBukuDipinjam.executeQuery()) {
                    if (checkStok.next()) {
                        int stok = checkStok.getInt("stok");
                        if (stok == 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Maaf Stok buku kosong!");
                        } else {
                            boolean siapDipinjam = false;
                            while (checkDipinjam.next()) {
                                int idBoookk = checkDipinjam.getInt("id_buku");
                                if (idBoookk == bookId) {
                                    siapDipinjam = true;
                                    break;
                                }
                            }
                            if (siapDipinjam) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText(null);
                                alert.setContentText("Buku sudah dipinjam!");
                            } else {
                                String insertQuery = "INSERT INTO dipinjam (id_buku, genre, judul,tahun_rilis, tanggal_pinjam, peminjamId) SELECT id_buku, genre, judul,tahun_rilis,? , ? FROM buku WHERE id_buku = ?";
                                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                                    insertStatement.setDate(1, java.sql.Date.valueOf(tanggalPinjam));
                                    insertStatement.setInt(2, DataSesi.userId);
                                    insertStatement.setInt(3, bookId);
                                    insertStatement.executeUpdate();
                                }
                                String updateQuery = "UPDATE buku SET stok = stok - 1 WHERE id_buku = ?";
                                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                    updateStatement.setInt(1, bookId);
                                    updateStatement.executeUpdate();
                                }
                                loadDataFromDatabase();
                            }
                            System.out.println(DataSesi.getUserId());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnSearch(ActionEvent actionEvent) {
    }

    public void btnDipinjam(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-dipinjam.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) btnRak.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void heartClicked(int bookId) {
        try (Connection connection = DatabaseConnector.connect()) {
            if (isBookInWishlist(bookId)) {
                removeFromWishlist(bookId);

                likeImage.setImage(new Image(getClass().getResource("/perpus/assets/heart.png").toExternalForm()));
            } else {
                addToWishlist(bookId);

                likeImage.setImage(new Image(getClass().getResource("/perpus/assets/heartFillRed.png").toExternalForm()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addToWishlist(int bookId) throws SQLException {
        String insertQuery = "INSERT INTO wishlist (id_buku) VALUES (?)";
        try (Connection connection = DatabaseConnector.connect()){
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, bookId);
                insertStatement.executeUpdate();
            }
        }
    }

    private void removeFromWishlist(int bookId) throws SQLException {
        String deleteQuery = "DELETE FROM wishlist WHERE id_buku = ?";
        try (Connection connection = DatabaseConnector.connect()){
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setInt(1, bookId);
                deleteStatement.executeUpdate();
            }
        }

    }

    private boolean isBookInWishlist(int bookId) throws SQLException {
        String query = "SELECT COUNT(*) FROM wishlist WHERE id_buku = ?";
        try (Connection connection = DatabaseConnector.connect()){
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, bookId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        }
        return false;
    }


    public void btnwishlist(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-wishlist.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) btnRak.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
}
