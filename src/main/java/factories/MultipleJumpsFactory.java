package factories;

import factories.GameFactory;
import game.board.StandardBoard.StandardBoard;
import org.springframework.stereotype.Component;
import rules.MultipleJumpsRuleSet;

/**
 * Factory for creating MultipleJumps game components.
 */
@Component
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