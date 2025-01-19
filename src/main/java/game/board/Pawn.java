package game.board;

import java.io.Serializable;

/**
 * Represents a pawn on the board.
 */
public class Pawn implements Serializable {
    private static final long serialVersionUID = 1L;
    private int playerId;

    public Pawn(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}