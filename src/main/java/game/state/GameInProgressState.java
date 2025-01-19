package game.state;

import java.util.Random;
import server.GameServer;
import game.move.Move;
import utils.message.MessageType;

/**
 * Represents the state of the game when it is in progress.
 */
public class GameInProgressState implements GameState {

    /**
     * Initializes the game in progress state.
     * 
     * @param server The game server.
     */
    public GameInProgressState(GameServer server) {
        // Randomly select the first player
        Random random = new Random();
        int randomIndex = random.nextInt(server.getPlayers().size());
        server.setCurrentPlayer(server.getPlayers().get(randomIndex)); // Start with a random player
        
        // Broadcast the board state to the current player
        server.broadcastBoardState();
        
        // Notify the current player that it's their turn
        server.broadcastMessage("Player " + server.getCurrentPlayer().getPlayerId() + " is making a move.", MessageType.INFO);
        server.sendMessageToPlayer(server.getCurrentPlayer().getPlayerId(), "It's your turn!", MessageType.YOUR_TURN);
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
                    server.broadcastMessage("Player " + playerId + " has won the game!", MessageType.GAME_OVER);
                    server.sendMessageToPlayer(playerId, "YOU WON!", MessageType.GAME_OVER);
                    server.setState(new GameOverState(server));
                } else {
                    // Move to the next player

                    server.nextPlayer();

                    server.broadcastMessage("Player " + server.getCurrentPlayer().getPlayerId() + " is making a move.", MessageType.INFO);
                    server.sendMessageToPlayer(server.getCurrentPlayer().getPlayerId(), "It's your turn!", MessageType.YOUR_TURN);
                }
            } else {
                server.sendMessageToPlayer(playerId, "Invalid move: either the start position does not contain your pawn or the end position is occupied.", MessageType.INFO);
            }
        } else {
            server.sendMessageToPlayer(playerId, "Player " + server.getCurrentPlayer().getPlayerId() + " is making a move.", MessageType.INFO);
        }
    }

    @Override
    public void broadcastState(GameServer server) {
        server.broadcastBoardState();
    }
}