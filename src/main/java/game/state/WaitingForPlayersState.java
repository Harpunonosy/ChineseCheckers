package game.state;

import server.GameServer;

public class WaitingForPlayersState implements GameState {
    @Override
    public void handlePlayerJoin(GameServer server) {
        server.incrementPlayerCount();
        server.broadcastPlayerInfo();
        if (server.getPlayerCount() >= server.getMaxPlayers()) {
            server.startGame();
        }
    }

    @Override
    public void handleMove(GameServer server, String move, int playerId) {
        // No moves should be processed in this state
    }

    @Override
    public void broadcastState(GameServer server) {
        server.broadcastPlayerInfo();
    }
}