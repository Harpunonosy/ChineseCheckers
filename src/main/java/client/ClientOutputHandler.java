package client;

import game.board.CellVertex;
import game.board.Pawn;
import game.board.StandardBoard.StandardBoard;

import java.io.IOException;
import utils.message.Message;
import utils.message.MessageType;

public class ClientOutputHandler implements Runnable {
    private ClientConnection connection;
    private ClientInputHandler inputHandler;

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
                System.out.println("Server: " + message.getContent());
                if (message.getContent().equals("It's your turn!")) {
                    inputHandler.promptForMove();
                }
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
}