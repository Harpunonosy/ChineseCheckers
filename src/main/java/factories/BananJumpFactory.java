package factories;

import game.board.StandardBoard.StandardBoard;
import rules.BananaJump;

public class BananJumpFactory implements GameFactory {
    @Override
    public StandardBoard createBoard() {
        return new StandardBoard();
    }

    @Override
    public BananaJump createRuleSet() {
        return new BananaJump();
    }
}