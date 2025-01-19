package factories;

import game.board.Board;
import rules.GameRuleSet;

/**
 * Interface for game factories.
 */
public interface GameFactory {
    Board createBoard();
    GameRuleSet createRuleSet();
}