package client;

import exceptions.InvalidMoveFormatException;
import utils.message.Message;
import utils.message.MessageType;

public class ClientInputHandler {
    private ClientConnection connection;

    public ClientInputHandler(ClientConnection connection) {
        this.connection = connection;
    }

    public void sendMove(String move) {
        try {
            Message message = new Message(MessageType.MOVE, move);
            connection.sendMessage(message);
        } catch (Exception e) {
            System.out.println("Error sending move: " + e.getMessage());
        }
    }

    public void sendAvailableMovesRequest(String move) {
    }
}