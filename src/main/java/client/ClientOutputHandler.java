package client;

import java.io.IOException;

public class ClientOutputHandler implements Runnable {
    private ClientConnection connection;

    public ClientOutputHandler(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = connection.receiveMessage()) != null) {
                System.out.println("Move received: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}