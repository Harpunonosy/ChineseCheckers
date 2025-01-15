package client;

import java.util.Scanner;

public class ClientInputHandler {
    private ClientConnection connection;
    private Scanner scanner;

    public ClientInputHandler(ClientConnection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void handleInput() {
        //Dla innego inputu jeśli będzie potrzebny
    }

    public void promptForMove() {
        String move;
        while (true) {
            System.out.print("Enter move (startX-startY-endX-endY): ");
            move = scanner.nextLine();
            if (isValidMoveFormat(move)) {
                break;
            } else {
                System.out.println("Invalid move format. Please enter in the format x1-y1-x2-y2.");
            }
        }
        connection.sendMessage(move);
    }

    private boolean isValidMoveFormat(String move) {
        return move.matches("\\d+-\\d+-\\d+-\\d+");
    }
}