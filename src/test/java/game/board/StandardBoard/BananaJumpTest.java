package game.board.StandardBoard;

import game.board.Board;
import game.board.Pawn;
import game.move.Move;
import rules.BananaJump;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BananaJumpTest {
    private Board board;
    private BananaJump ruleSet;

    @BeforeEach
    public void setUp() {
        board = new StandardBoard();
        board.initialize();
        ((StandardBoard) board).setPlayersToRegions(2); // Inicjalizacja playerTargetRegions dla 2 graczy
        ruleSet = new BananaJump();
    }

    @Test
    public void testValidMultiJump() {
        // Ustaw pionki na planszy
        board.getVertexAt(12, 12).setPawn(new Pawn(1));
        board.getVertexAt(13, 11).setPawn(new Pawn(2));
        board.getVertexAt(15, 9).setPawn(new Pawn(2));

        Move move = new Move(12, 12, 16, 8);
        assertTrue(ruleSet.isValidMove(move, 1, board));
    }

    @Test
    public void testInvalidMultiJump() {
        // Ustaw pionki na planszy
        board.getVertexAt(12, 12).setPawn(new Pawn(1));
        board.getVertexAt(14, 12).setPawn(new Pawn(2));
        board.getVertexAt(16, 12).setPawn(new Pawn(2));

        Move move = new Move(12, 12, 20, 12);
        assertFalse(ruleSet.isValidMove(move, 1, board));
    }
}