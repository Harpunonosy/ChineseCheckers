package GUI;

import game.board.Board;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

public class GUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SceneSample.fxml")));
        primaryStage.setScene(new javafx.scene.Scene(root, 300, 275));
        primaryStage.show();
    }
}

