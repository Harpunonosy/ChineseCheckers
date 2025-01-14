package client;

import java.util.Scanner;

public class ClientInputHandler {
    private ClientConnection connection;

    public ClientInputHandler(ClientConnection connection) {
        this.connection = connection;
    }

    public void handleInput() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter move (start-end): ");
            String move = scanner.nextLine();
            connection.sendMessage(move);
        }
    }
}