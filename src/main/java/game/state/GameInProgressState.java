package game.state;

import server.GameServer;
import game.move.Move;

public class GameInProgressState implements GameState {

    public GameInProgressState(GameServer server) {
        // Randomly select the first player
        server.setCurrentPlayer(server.getPlayers().get(0)); // Start with the first player
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

                // Move to the next player
                server.nextPlayer();
                server.sendMessageToPlayer(server.getCurrentPlayer().getPlayerId(), "It's your turn!");
                server.broadcastMessage("Player " + server.getCurrentPlayer().getPlayerId() + " is making a move.");
            } else {
                server.sendMessageToPlayer(playerId, "Invalid move. Please try again.");
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