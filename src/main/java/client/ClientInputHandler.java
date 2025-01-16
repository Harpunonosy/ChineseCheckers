package client;

import java.util.Scanner;
import exceptions.*;

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

    public void promptForMove() {
        String move;
        while (true) {
            System.out.print("Enter move (startX-startY-endX-endY): ");
            move = scanner.nextLine();
            try { //TODO DELETE SECON CONDITION
                if (isValidMoveFormat(move) || isValidTeleportFormat(move)) {
                    connection.sendMessage(move);
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
    //TODO DELETE LATER
    private boolean isValidTeleportFormat(String move) {
        return move.matches("teleport\\d+-\\d+-\\d+-\\d+");
    }
}