package factories;

import game.board.StandardBoard.StandardBoard;
import rules.MultiJumpRuleSet;

public class MultiJumpGameFactory implements GameFactory {
    @Override
    public StandardBoard createBoard() {
        return new StandardBoard();
    }

    @Override
    public MultiJumpRuleSet createRuleSet() {
        return new MultiJumpRuleSet();
    }
}