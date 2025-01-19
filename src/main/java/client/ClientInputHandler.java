package client;

import exceptions.InvalidMoveFormatException;
import utils.message.Message;
import utils.message.MessageType;

/**
 * Handles input from the client.
 */
public class ClientInputHandler {
    private ClientConnection connection;

    /**
     * Initializes a new ClientInputHandler.
     * 
     * @param connection The client connection.
     */
    public ClientInputHandler(ClientConnection connection) {
        this.connection = connection;
    }

    /**
     * Sends a move to the server.
     * 
     * @param move The move to send.
     */
    public void sendMove(String move) {
        try {
            Message message = new Message(MessageType.MOVE, move);
            connection.sendMessage(message);
        } catch (Exception e) {
            System.out.println("Error sending move: " + e.getMessage());
        }
    }
}