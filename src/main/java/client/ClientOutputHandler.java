package client;

import java.io.IOException;

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
            String message;
            while ((message = connection.receiveMessage()) != null) {
                System.out.println("Server: " + message);
                if (message.equals("It's your turn!")) {
                    inputHandler.promptForMove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}