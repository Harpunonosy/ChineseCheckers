package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import utils.message.Message;
import utils.message.MessageType;
import utils.message.MessageUtils;

/**
 * Handles communication with a single player.
 */
public class PlayerHandler implements Runnable {
    private Socket socket;
    private GameServer server;
    private PrintWriter out;
    private BufferedReader in;
    private int playerId;

    /**
     * Initializes a new PlayerHandler.
     * 
     * @param socket The socket for communication with the player.
     * @param server The game server.
     * @param playerId The ID of the player.
     */
    public PlayerHandler(Socket socket, GameServer server, int playerId) {
        this.socket = socket;
        this.server = server;
        this.playerId = playerId;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Send player ID to client
            sendMessage(new Message(MessageType.YOUR_ID, String.valueOf(playerId))); //GIVE CLIENT HIS ID

            String json;
            while ((json = in.readLine()) != null) {
                Message message = MessageUtils.deserializeMessage(json);
                System.out.println("Received message from player " + playerId + ": " + message.getContent());
                handleMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Message message) {
        switch (message.getType()) {
            case MOVE:
                server.processMove(message.getContent(), playerId);
                break;
            default:
                System.out.println("Unknown message type: " + message.getType());
        }
    }

    /**
     * Sends a message to the player.
     * 
     * @param message The message to send.
     */
    public void sendMessage(Message message) {
        String json = MessageUtils.serializeMessage(message);
        out.println(json);
    }

    /**
     * Gets the ID of the player.
     * 
     * @return The ID of the player.
     */
    public int getPlayerId() {
        return playerId;
    }
}