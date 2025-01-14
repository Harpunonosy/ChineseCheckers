package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable {
    private Socket socket;
    private GameServer server;
    private PrintWriter out;
    private int playerId;

    public PlayerHandler(Socket socket, GameServer server, int playerId) {
        this.socket = socket;
        this.server = server;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String move;
            while ((move = in.readLine()) != null) {
                System.out.println("Received move from player " + playerId + ": " + move);
                server.broadcastMove(move, playerId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}