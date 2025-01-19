package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import game.board.StandardBoard.StandardBoard;
import utils.message.Message;
import utils.message.MessageUtils;
import utils.SerializationUtils;

/**
 * Handles the connection between the client and the server.
 */
public class ClientConnection {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Initializes a new ClientConnection.
     * 
     * @param serverAddress The address of the server.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public ClientConnection(String serverAddress) throws IOException {
        socket = new Socket(serverAddress, 12345);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Sends a message to the server.
     * 
     * @param message The message to send.
     */
    public void sendMessage(Message message) {
        String json = MessageUtils.serializeMessage(message);
        out.println(json);
    }

    /**
     * Receives a message from the server.
     * 
     * @return The received message.
     * @throws IOException If an I/O error occurs when reading the message.
     */
    public Message receiveMessage() throws IOException {
        String json = in.readLine();
        return MessageUtils.deserializeMessage(json);
    }

    /**
     * Deserializes a board from a string.
     * 
     * @param serializedBoard The serialized board.
     * @return The deserialized board.
     */
    public StandardBoard deserializeBoard(String serializedBoard) {
        return SerializationUtils.deserializeBoard(serializedBoard);
    }

    /**
     * Closes the connection.
     * 
     * @throws IOException If an I/O error occurs when closing the connection.
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}