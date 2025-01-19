package client;

import GUI.ClientStartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The main class for the game client.
 */
public class GameClient extends Application {
    private ClientConnection connection;
    private ClientInputHandler inputHandler;
    private ClientOutputHandler outputHandler;
    private int clientId;

    public GameClient() {
    }

    /**
     * Initializes the client connection and handlers.
     * 
     * @param serverAddress The address of the server.
     * @throws IOException If an I/O error occurs when creating the connection.
     */
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

        outputHandler.setPrimaryStage(primaryStage);
        new Thread(outputHandler).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}