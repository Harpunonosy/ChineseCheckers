package game.board.StandardBoard;

import game.CompletedGame;
import game.CompletedMove;
import game.board.Board;
import game.board.Pawn;
import game.move.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import repository.CompletedGameRepository;
import repository.CompletedMoveRepository;
import rules.BananaJump;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompletedGameTest {
    private Board board;
    private BananaJump ruleSet;

    @Autowired
    CompletedGameRepository repo;
    @BeforeEach
    public void setUp() {
        board = new StandardBoard();
        board.initialize();
        ((StandardBoard) board).setPlayersToRegions(2); // Inicjalizacja playerTargetRegions dla 2 graczy
        ruleSet = new BananaJump();
    }

//    @Test
    public void testSaveCompletedGame() {
        List<CompletedMove> moves = new ArrayList<>();
        moves.add(new CompletedMove(new Move(1, 2, 3, 4), 1));
        moves.add(new CompletedMove(new Move(1, 2, 3, 4), 2));
        moves.add(new CompletedMove(new Move(1, 2, 3, 4), 3));

        CompletedGame game = new CompletedGame(
                1,
                LocalDateTime.now(),
                LocalDateTime.now(),
                moves
        );
        repo.save(game);

    }

}