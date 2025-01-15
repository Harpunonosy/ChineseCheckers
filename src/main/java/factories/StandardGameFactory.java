package factories;

import game.board.StandardBoard.StandardBoard;
import rules.StandardRuleSet;

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