package server;

import game.state.*;
public class Bot extends PlayerHandler {
    private BotStrategy strategy;
    private GameServer server;
    private int playerId;

    public Bot(GameServer server, int playerId, BotStrategy strategy) {
        super(null, server, playerId);
        this.strategy = strategy;
        this.server = server;
        this.playerId = playerId;
    }

    public GameServer getServer() {
        return this.server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (server.getState() instanceof GameInProgressState) {
                  if (server.getCurrentPlayer().getPlayerId() == playerId) {
                    strategy.makeMove(this);
                  }
                }
                Thread.sleep(100); // Czekaj 1 sekundę przed sprawdzeniem ponownie
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}