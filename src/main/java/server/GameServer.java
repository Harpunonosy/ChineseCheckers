package server;

import game.Game;
import game.board.StandardBoard.StandardBoard;
import game.move.Move;
import game.state.GameInProgressState;
import game.state.GameOverState;
import game.state.GameState;
import game.state.WaitingForPlayersState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import utils.message.Message;
import utils.message.MessageType;
import utils.SerializationUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import factories.*;;
@Component
public class GameServer {
    private List<PlayerHandler> players = new ArrayList<>();
    private int playerCount;
    private int maxPlayers;
    private Game game;
    private GameState currentState;
    private PlayerHandler currentPlayer;
    private GameFactory gameFactory;


    @Autowired
    public GameServer(@Value("${game.maxPlayers}")int maxPlayers, GameFactory gameFactory) {
        this.maxPlayers = maxPlayers;
        this.gameFactory = gameFactory;
        if (maxPlayers < 2 || maxPlayers > 6 || maxPlayers == 5) {
            throw new IllegalArgumentException("Invalid number of players. The game supports between 2 3 4 or 6 players");
        }
        this.maxPlayers = maxPlayers;
        this.playerCount = 0;
        this.currentState = new WaitingForPlayersState();
        this.game = new Game(gameFactory, maxPlayers);
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started, waiting for players...");

        while (currentState instanceof WaitingForPlayersState) {
            if (players.size() < maxPlayers) {
                Socket clientSocket = serverSocket.accept();
                PlayerHandler playerHandler = new PlayerHandler(clientSocket, this, players.size() + 1);
                players.add(playerHandler);
                new Thread(playerHandler).start();
                currentState.handlePlayerJoin(this);
                System.out.println("Player connected: " + players.size() + "/" + maxPlayers);
            }
        }
    }

    public void addBot(BotStrategy strategy) {
        Bot bot = new Bot(this, players.size() + 1, strategy);
        players.add(bot);
        new Thread(bot).start();
        currentState.handlePlayerJoin(this);
        System.out.println("Bot added: " + players.size() + "/" + maxPlayers);
    }

    public void startGame() {
        setState(new GameInProgressState(this));
        broadcastMessage("Game started", MessageType.GAME_STARTED);
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

    public void sendMessageToPlayer(int playerId, String messageContent, MessageType messageType) {
        Message message = new Message(messageType, messageContent);
        players.get(playerId - 1).sendMessage(message);
    }

    public void broadcastMessage(String messageContent, MessageType messageType) {
        Message message = new Message(messageType, messageContent);
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
        currentState.handleMove(this, move, playerId);
        broadcastBoardState();
    }


    }

