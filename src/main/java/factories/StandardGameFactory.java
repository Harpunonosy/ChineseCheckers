package factories;

import game.board.StandardBoard.StandardBoard;
import org.springframework.stereotype.Component;
import rules.StandardRuleSet;

/**
 * Factory for creating standard game components.
 */
@Component
public class StandardGameFactory implements GameFactory {
    @Override
    public StandardBoard createBoard() {
        return new StandardBoard();
    }

    @Override
    public StandardRuleSet createRuleSet() {
        return new StandardRuleSet();
    }
}