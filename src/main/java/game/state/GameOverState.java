package game.state;

import server.GameServer;

public class GameOverState implements GameState {

    public GameOverState(GameServer server){
      server.broadcastMessage("Game Over !");
    }

    @Override
    public void handlePlayerJoin(GameServer server) {
        // No new players should join during the game over state
        throw new UnsupportedOperationException("Cannot join game over state.");
    }

    @Override
    public void handleMove(GameServer server, String move, int playerId) {
        // No moves should be processed in the game over state
        throw new UnsupportedOperationException("Cannot make a move in game over state.");
    }

    @Override
    public void broadcastState(GameServer server) {
        server.broadcastMessage("Game over.");
    }
}