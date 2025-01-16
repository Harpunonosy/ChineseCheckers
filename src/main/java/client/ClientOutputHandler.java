package client;

import game.board.CellVertex;
import game.board.Pawn;
import game.board.StandardBoard.StandardBoard;

import java.io.IOException;
import utils.message.Message;

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
                if (message.getType().equals("BOARD_STATE")) {
                    StandardBoard board = connection.deserializeBoard(message.getContent());
                    updateBoardUI(board);
                } else if (message.getType().equals("MESSAGE")) {
                    System.out.println("Server: " + message.getContent());
                    if (message.getContent().equals("It's your turn!")) {
                        inputHandler.promptForMove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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