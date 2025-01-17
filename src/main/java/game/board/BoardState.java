package game.board;

import java.io.Serializable;

/**
 * Represents the state of the board.
 */
public class BoardState implements Serializable {
    private static final long serialVersionUID = 1L;
    private CellVertex[][] matrix;

    public BoardState(CellVertex[][] matrix) {
        this.matrix = matrix;
    }

    public CellVertex[][] getMatrix() {
        return matrix;
    }
}