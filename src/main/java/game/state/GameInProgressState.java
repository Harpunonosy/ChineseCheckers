package game.state;

import java.util.Random;
import server.GameServer;
import game.move.Move;

public class GameInProgressState implements GameState {

    public GameInProgressState(GameServer server) {
        // Randomly select the first player
        Random random = new Random();
        int randomIndex = random.nextInt(server.getPlayers().size());
        server.setCurrentPlayer(server.getPlayers().get(randomIndex)); // Start with a random player
        
        // Broadcast the board state to the current player
        server.broadcastBoardState();
        
        // Notify the current player that it's their turn
        server.sendMessageToPlayer(server.getCurrentPlayer().getPlayerId(), "It's your turn!");
        server.broadcastMessage("Player " + server.getCurrentPlayer().getPlayerId() + " is making a move.");
    }

    @Override
    public void handlePlayerJoin(GameServer server) {
        // No new players should join during the game
        throw new UnsupportedOperationException("Cannot join game in progress.");
    }

    @Override
    public void handleMove(GameServer server, String move, int playerId) {
        if (server.getCurrentPlayer().getPlayerId() == playerId) {
            Move parsedMove = server.parseMove(move);
            if (server.getGame().getGameRuleSet().isValidMove(parsedMove, playerId, server.getGame().getBoard())) {
                // Process the move
                server.getGame().makeMove(parsedMove, playerId);
                server.broadcastBoardState();

                // Check if the game is over for the current player
                if (server.getGame().getGameRuleSet().isGameOver(server.getGame().getBoard(), playerId)) {
                    server.sendMessageToPlayer(playerId, "You won!");
                    server.broadcastMessage("Player " + playerId + " has won the game!");
                    server.setState(new GameOverState(server));
                    server.broadcastMessage("Game Over! Player " + playerId + " has won the game!"); // Rozsyłanie wiadomości o wygranej
                } else {
                    // Move to the next player
                    server.nextPlayer();
                    server.sendMessageToPlayer(server.getCurrentPlayer().getPlayerId(), "It's your turn!");
                    server.broadcastMessage("Player " + server.getCurrentPlayer().getPlayerId() + " is making a move.");
                }
            } else {
                server.sendMessageToPlayer(playerId, "Invalid move: either the start position does not contain your pawn or the end position is occupied.");
            }
        } else {
            server.sendMessageToPlayer(playerId, "It's not your turn.");
        }
    }

    @Override
    public void broadcastState(GameServer server) {
        server.broadcastBoardState();
    }
}