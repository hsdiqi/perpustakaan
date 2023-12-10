package perpus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;

public class libraryAplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(libraryAplication.class.getResource("login.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 360, 640);
        stage.setTitle("Anjay Mabar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
