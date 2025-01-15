package game.state;

import server.GameServer;

public interface GameState {
    void handlePlayerJoin(GameServer server);
    void handleMove(GameServer server, String move, int playerId);
    void broadcastState(GameServer server);
}