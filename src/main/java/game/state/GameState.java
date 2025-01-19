package game.state;

import server.GameServer;

/**
 * Interface representing a game state.
 */
public interface GameState {
    /**
     * Handles a player joining the game.
     * 
     * @param server The game server.
     */
    void handlePlayerJoin(GameServer server);

    /**
     * Handles a move made by a player.
     * 
     * @param server The game server.
     * @param move The move made by the player.
     * @param playerId The ID of the player making the move.
     */
    void handleMove(GameServer server, String move, int playerId);

    /**
     * Broadcasts the current state of the game.
     * 
     * @param server The game server.
     */
    void broadcastState(GameServer server);
}