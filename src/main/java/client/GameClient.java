package client;

import GUI.ClientStartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameClient extends Application {
    private ClientConnection connection;
    private ClientInputHandler inputHandler;
    private ClientOutputHandler outputHandler;
    private int clientId;

    public GameClient() {
    }

    public void initialize(String serverAddress) throws IOException {
        connection = new ClientConnection(serverAddress);
        inputHandler = new ClientInputHandler(connection);
        outputHandler = new ClientOutputHandler(connection, inputHandler);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialize("localhost");

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/GUI/ClientStart.fxml")));
        Parent root = loader.load();
        ClientStartController controller = loader.getController();
        controller.setClientId(clientId);
        outputHandler.setStartController(controller);

        primaryStage.setTitle("Client Start");
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();

        new Thread(outputHandler).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}