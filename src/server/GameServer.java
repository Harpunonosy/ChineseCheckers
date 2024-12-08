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

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started, waiting for players...");

        while (players.size() < playerCount) {
            Socket clientSocket = serverSocket.accept();
            PlayerHandler playerHandler = new PlayerHandler(clientSocket, this, players.size() + 1);
            players.add(playerHandler);
            new Thread(playerHandler).start();
            System.out.println("Player connected: " + players.size() + "/" + playerCount);
        }

        System.out.println("All players connected. Game starting...");
        // Start the game logic here
    }

    public void broadcastMove(String move, int playerId) {
        for (PlayerHandler player : players) {
            player.sendMessage("Player " + playerId + ": " + move);
        }
    }

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer(2); // Example with 2 players
        server.startServer();
    }
}