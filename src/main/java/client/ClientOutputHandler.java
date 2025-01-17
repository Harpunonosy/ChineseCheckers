package client;

import GUI.ClientStartController;
import GUI.InGameClientController;
import game.board.CellVertex;
import game.board.Pawn;
import game.board.StandardBoard.StandardBoard;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.message.Message;
import utils.message.MessageType;

import java.io.IOException;
import java.util.Objects;

public class ClientOutputHandler implements Runnable {
    private ClientConnection connection;
    private ClientInputHandler inputHandler;
    private ClientStartController startController;
    private InGameClientController gameController;

    public ClientOutputHandler(ClientConnection connection, ClientInputHandler inputHandler) {
        this.connection = connection;
        this.inputHandler = inputHandler;
    }

    @Override
    public void run() {
        try {
            Message message;
            while ((message = connection.receiveMessage()) != null) {
                handleMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Message message) {
        switch (message.getType()) {
            case BOARD_STATE:
                StandardBoard board = connection.deserializeBoard(message.getContent());
                updateBoardUI(board);
                break;
            case INFO:
                Platform.runLater(() -> {
                    if (startController != null) {
                        startController.setInfo(message.getContent());
                        if (message.getContent().startsWith("Your ID: ")) {
                            int clientId = Integer.parseInt(message.getContent().substring(9));
                            startController.setClientId(clientId);
                        } else if (message.getContent().equals("Game started")) {
                            switchToGameScene();
                        }
                    }
                });
                break;
            default:
                System.out.println("Unknown message type: " + message.getType());
        }
    }

    private void updateBoardUI(StandardBoard board) {
        CellVertex[][] matrix = board.getMatrix();
        for (int y = 0; y < matrix[0].length; y++) {
            for (int x = 0; x < matrix.length; x++) {
                if (matrix[x][y] != null) {
                    Pawn pawn = matrix[x][y].getPawn();
                    if (pawn != null) {
                        System.out.print(pawn.getPlayerId() + " ");
                    } else {
                        System.out.print("0 ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    private void switchToGameScene() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/GUI/InGameClient.fxml")));
                Parent root = loader.load();
                gameController = loader.getController();

                Stage stage = (Stage) startController.getClientIdLabel().getScene().getWindow();
                stage.setScene(new Scene(root, 1920, 1080));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setStartController(ClientStartController startController) {
        this.startController = startController;
    }
}