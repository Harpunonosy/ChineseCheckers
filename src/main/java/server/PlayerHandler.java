package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import utils.message.Message;
import utils.message.MessageUtils;

public class PlayerHandler implements Runnable {
    private Socket socket;
    private GameServer server;
    private PrintWriter out;
    private int playerId;

    public PlayerHandler(Socket socket, GameServer server, int playerId) {
        this.socket = socket;
        this.server = server;
        this.playerId = playerId;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String json;
            while ((json = in.readLine()) != null) {
                Message message = MessageUtils.deserializeMessage(json);
                System.out.println("Received message from player " + playerId + ": " + message.getContent());
                server.processMove(message.getContent(), playerId);
            }
        } catch (IOException e) {
            e.printStackTrace();
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