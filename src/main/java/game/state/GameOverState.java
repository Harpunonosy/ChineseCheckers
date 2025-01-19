package game.state;

import server.GameServer;
import utils.message.MessageType;

/**
 * Represents the state of the game when it is over.
 */
public class GameOverState implements GameState {

    /**
     * Initializes the game over state.
     * 
     * @param server The game server.
     */
    public GameOverState(GameServer server){
        // No need to broadcast "Game Over!" message here
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
        // No need to broadcast "Game over." message here
    }
}