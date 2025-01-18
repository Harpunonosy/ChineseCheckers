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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
                updateBoardGUI(board);
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

   private void updateBoardGUI(StandardBoard board) {
    Platform.runLater(() -> {
        gameController.clearBoard(); // Dodaj metodę do czyszczenia planszy
        CellVertex[][] matrix = board.getMatrix();
        for (int y = 0; y < matrix[0].length; y++) {
            for (int x = 0; x < matrix.length; x++) {
                if (matrix[x][y] != null) {
                    Pawn pawn = matrix[x][y].getPawn();
                    if (pawn != null) {
                        Circle circle = gameController.getCircleAtPosition(x, y);
                        if (circle != null) {
                            gameController.highlightCircle(circle, getColorForPlayer(pawn.getPlayerId()));
                        }
                    }
                }
            }
        }
    });
}

    private Color getColorForPlayer(int playerId) {
        switch (playerId) {
            case 1: return Color.RED;
            case 2: return Color.BLUE;
            case 3: return Color.GREEN;
            case 4: return Color.YELLOW;
            case 5: return Color.ORANGE;
            case 6: return Color.PURPLE;
            default: return Color.BLACK;
        }
    }

    private void switchToGameScene() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/GUI/InGameClient.fxml")));
                Parent root = loader.load();
                gameController = loader.getController();
                gameController.setInputHandler(inputHandler); // Przekazanie instancji ClientInputHandler

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