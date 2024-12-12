package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private List<PlayerHandler> players = new ArrayList<>();
    private int playerCount;

    public GameServer(int playerCount) {
        this.playerCount = playerCount;
    }

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer(2); // Example with 2 players
        //server.startServer(); //TODO
    }

    
}
