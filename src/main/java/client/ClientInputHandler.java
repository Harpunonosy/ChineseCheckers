package client;

import java.util.Scanner;
import exceptions.InvalidMoveFormatException;
import utils.message.Message;
import utils.message.MessageType;

public class ClientInputHandler {
    private ClientConnection connection;
    private Scanner scanner;

    public ClientInputHandler(ClientConnection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void handleInput() {
        // For other input handling if needed
    }

    // Communication Methods
    /*
     * Communication Methods
     */

    public void sendMove(String move) {
        try {
            if (isValidMoveFormat(move)) {
                Message message = new Message(MessageType.MOVE, move);
                connection.sendMessage(message);
            } else {
                throw new InvalidMoveFormatException("Invalid move format. Please enter in the format x1-y1-x2-y2.");
            }
        } catch (InvalidMoveFormatException e) {
            System.out.println(e.getMessage());
        }
    }
    public void promptForMove() {
        String move;
        while (true) {
            System.out.print("Enter move (startX-startY-endX-endY): ");
            move = scanner.nextLine();
            try {
                if (isValidMoveFormat(move)) {
                    Message message = new Message(MessageType.MOVE, move);
                    connection.sendMessage(message);
                    break;
                } else {
                    throw new InvalidMoveFormatException("Invalid move format. Please enter in the format x1-y1-x2-y2.");
                }
            } catch (InvalidMoveFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isValidMoveFormat(String move) {
        return move.matches("\\d+-\\d+-\\d+-\\d+");
    }
}