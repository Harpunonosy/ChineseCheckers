package factories;

import game.board.Board;
import org.springframework.context.annotation.Bean;
import rules.GameRuleSet;

/**
 * Interface for game factories.
 */
public interface GameFactory {
    Board createBoard();
    GameRuleSet createRuleSet();
}