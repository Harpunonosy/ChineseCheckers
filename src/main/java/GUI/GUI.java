package GUI;

import game.board.Board;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

public class GUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

   @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SceneSample.fxml")));
        primaryStage.setTitle("Scene Sample");
        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.show();
    }
}

