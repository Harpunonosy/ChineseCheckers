package factories;

import game.board.Board;
import rules.GameRuleSet;

public interface GameFactory {
  Board createBoard();
  GameRuleSet createRuleSet();
} 
