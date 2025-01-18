package client;

import GUI.ClientStartController;
import GUI.GameOverController;
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
import java.util.logging.Logger;
import java.util.logging.Level;

public class ClientOutputHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientOutputHandler.class.getName());
    private ClientConnection connection;
    private ClientInputHandler inputHandler;
    private ClientStartController startController;
    private InGameClientController gameController;
    private StandardBoard pendingBoard;
    private Stage primaryStage;

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
            logger.log(Level.SEVERE, "Error receiving message", e);
        }
    }

    private void handleMessage(Message message) {
        switch (message.getType()) {
            case BOARD_STATE:
                StandardBoard board = connection.deserializeBoard(message.getContent());
                if (gameController == null) {
                    pendingBoard = board; // Store the board until the gameController is set
                } else {
                    updateBoardGUI(board);
                }
                break;
            case INFO:
                Platform.runLater(() -> {
                    if (startController != null) {
                        startController.setInfo(message.getContent());
                        logger.info("Server: " + message.getContent());
                    }
                });
                break;
            case GAME_STARTED:
                Platform.runLater(() -> {
                    if (startController != null) {
                        startController.setInfo("Game started");
                        switchToGameScene();
                    }
                });
                break;
            case YOUR_ID:
                Platform.runLater(() -> {
                    if (startController != null) {
                        int clientId = Integer.parseInt(message.getContent());
                        startController.setClientId(clientId);
                    }
                });
                break;
            case YOUR_TURN:
                Platform.runLater(() -> {
                    if (gameController != null) {
                        //TODO
                        //gameController.showYourTurnMessage();
                    }
                });
                break;
            case GAME_OVER:
                Platform.runLater(() -> {
                    switchToGameOverScene(message.getContent());
                });
                break;
            default:
                logger.warning("Unknown message type: " + message.getType());
        }
    }

    private void updateBoardGUI(StandardBoard board) {
        Platform.runLater(() -> {
            if (gameController == null) {
                logger.warning("gameController is null in updateBoardGUI");
                return;
            }
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

                if (pendingBoard != null) {
                    updateBoardGUI(pendingBoard); // Update the board if there was a pending board
                    pendingBoard = null;
                }

                primaryStage.setScene(new Scene(root, 1920, 1080));
                primaryStage.show();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error switching to game scene", e);
            }
        });
    }

    private void switchToGameOverScene(String winner) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/GUI/GameOver.fxml")));
                Parent root = loader.load();
                GameOverController controller = loader.getController();
                logger.info("Switching to game over scene with winner: " + winner);
                if (winner.equals("YOU WON!")) {
                    controller.setWinner("YOU WON!");
                } else {
                    controller.setWinner(winner);
                }

                primaryStage.setScene(new Scene(root, 400, 200));
                primaryStage.show();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error switching to game over scene", e);
            }
        });
    }

    public void setStartController(ClientStartController startController) {
        this.startController = startController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}