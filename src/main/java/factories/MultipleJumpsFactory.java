package factories;

import game.board.StandardBoard.StandardBoard;
import rules.MultipleJumpsRuleSet;

/**
 * Factory for creating MultipleJumps game components.
 */
public class MultipleJumpsFactory implements GameFactory {
    @Override
    public StandardBoard createBoard() {
        return new StandardBoard();
    }

    @Override
    public MultipleJumpsRuleSet createRuleSet() {
        return new MultipleJumpsRuleSet();
    }
}