package perpus.nyobaktok;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OverlayExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        AnchorPane anchorPane = new AnchorPane();

        // Konten pada AnchorPane
        Button button = new Button("Tampilkan Overlay");
        AnchorPane.setTopAnchor(button, 20.0);
        AnchorPane.setLeftAnchor(button, 20.0);
        anchorPane.getChildren().add(button);

        // Membuat StackPane untuk overlay
        StackPane overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Warna hitam transparan
        overlayPane.setVisible(false); // Memulai dengan overlay tersembunyi
        overlayPane.setPrefSize(400, 300); // Sesuaikan dengan ukuran yang diinginkan

        // Isi overlay
        Button closeButton = new Button("Tutup Overlay");
        closeButton.setTextFill(Color.WHITE);
        overlayPane.getChildren().add(closeButton);

        // Menampilkan overlay saat tombol ditekan
        button.setOnAction(event -> overlayPane.setVisible(true));

        // Menyembunyikan overlay saat tombol di overlay ditekan
        closeButton.setOnAction(event -> overlayPane.setVisible(false));

        // Menambahkan StackPane (overlay) di atas AnchorPane
        anchorPane.getChildren().add(overlayPane);

        Scene scene = new Scene(anchorPane, 600, 400);
        primaryStage.setTitle("Overlay Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

