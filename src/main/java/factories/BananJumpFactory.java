package factories;

import game.board.StandardBoard.StandardBoard;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import rules.BananaJump;

/**
 * Factory for creating BananaJump game components.
 */
@Component
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