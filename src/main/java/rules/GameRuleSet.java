package rules;

import game.board.Board;
import game.move.Move;

public interface GameRuleSet {
  boolean isValidMove(Move move, int playerid, Board board);
  boolean isGameOver(Board board);
}
