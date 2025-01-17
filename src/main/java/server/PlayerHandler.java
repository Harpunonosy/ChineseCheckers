package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import utils.message.Message;
import utils.message.MessageType;
import utils.message.MessageUtils;

public class PlayerHandler implements Runnable {
    private Socket socket;
    private GameServer server;
    private PrintWriter out;
    private BufferedReader in;
    private int playerId;

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
            sendMessage(new Message(MessageType.INFO, "Your ID: " + playerId)); //GIVE CLIENT HIS ID

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

    public void sendMessage(Message message) {
        String json = MessageUtils.serializeMessage(message);
        out.println(json);
    }

    public int getPlayerId() {
        return playerId;
    }
}