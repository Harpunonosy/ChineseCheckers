package server;

import factories.StandardGameFactory;
import game.Game;
import game.board.StandardBoard.StandardBoard;
import game.move.Move;
import game.state.GameInProgressState;
import game.state.GameState;
import game.state.WaitingForPlayersState;
import utils.message.Message;
import utils.message.MessageType;
import utils.SerializationUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private List<PlayerHandler> players = new ArrayList<>();
    private int playerCount;
    private int maxPlayers;
    private Game game;
    private GameState currentState;
    private PlayerHandler currentPlayer;

    public GameServer(int maxPlayers) {
        if (maxPlayers < 2 || maxPlayers > 6 || maxPlayers == 5) {
            throw new IllegalArgumentException("Invalid number of players. The game supports between 2 3 4 or 6 players");
        }
        this.maxPlayers = maxPlayers;
        this.playerCount = 0;
        this.currentState = new WaitingForPlayersState();
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started, waiting for players...");

        while (currentState instanceof WaitingForPlayersState) {
            Socket clientSocket = serverSocket.accept();
            PlayerHandler playerHandler = new PlayerHandler(clientSocket, this, players.size() + 1);
            players.add(playerHandler);
            new Thread(playerHandler).start();
            currentState.handlePlayerJoin(this);
            System.out.println("Player connected: " + players.size() + "/" + maxPlayers);
        }
    }

    public void startGame() {
        game = new Game(new StandardGameFactory(), maxPlayers);
        setState(new GameInProgressState(this));
        broadcastMessage("Game started");
        broadcastBoardState();
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public void broadcastPlayerInfo() {
        Message message = new Message(MessageType.INFO, "Player " + playerCount + "/" + maxPlayers + " players connected.");
        for (PlayerHandler playerHandler : players) {
            playerHandler.sendMessage(message);
        }
    }

    public void broadcastBoardState() {
        StandardBoard board = (StandardBoard) game.getBoard();
        String serializedBoard = SerializationUtils.serializeBoard(board);
        if (serializedBoard != null) {
            Message message = new Message(MessageType.BOARD_STATE, serializedBoard);
            for (PlayerHandler playerHandler : players) {
                playerHandler.sendMessage(message);
            }
        }
    }

    public void sendMessageToPlayer(int playerId, String messageContent) {
        Message message = new Message(MessageType.INFO, messageContent);
        players.get(playerId - 1).sendMessage(message);
    }

    public void broadcastMessage(String messageContent) {
        Message message = new Message(MessageType.INFO, messageContent);
        for (PlayerHandler player : players) {
            player.sendMessage(message);
        }
    }

    public void setCurrentPlayer(PlayerHandler currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public PlayerHandler getCurrentPlayer() {
        return currentPlayer;
    }

    public void incrementPlayerCount() {
        playerCount++;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<PlayerHandler> getPlayers() {
        return players;
    }

    public void nextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        currentPlayer = players.get((currentIndex + 1) % players.size());
    }

    public GameState getState(){
        return currentState;
    }

    public Game getGame() {
        return game;
    }

    public Move parseMove(String move) {
        String[] parts = move.split("-");
        int startX = Integer.parseInt(parts[0]);
        int startY = Integer.parseInt(parts[1]);
        int endX = Integer.parseInt(parts[2]);
        int endY = Integer.parseInt(parts[3]);
        return new Move(startX, startY, endX, endY);
    }

    public void processMove(String move, int playerId) {
        if (move.startsWith("teleport")) {
            String[] parts = move.substring("teleport".length()).split("-");
            int startX = Integer.parseInt(parts[0]);
            int startY = Integer.parseInt(parts[1]);
            int endX = Integer.parseInt(parts[2]);
            int endY = Integer.parseInt(parts[3]);
            boolean success = teleportPawn(startX, startY, endX, endY);
            if (success) {
                sendMessageToPlayer(playerId, "Teleport successful.");
                broadcastBoardState();
            } else {
                sendMessageToPlayer(playerId, "Teleport failed. Either the start position does not contain your pawn or the end position is occupied.");
            }
        } else {
            currentState.handleMove(this, move, playerId);
            broadcastBoardState();
        }
    }

    public boolean teleportPawn(int startX, int startY, int endX, int endY) {
        return ((StandardBoard) game.getBoard()).teleportPawn(startX, startY, endX, endY);
    }

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer(6);
        server.startServer();
    }
}