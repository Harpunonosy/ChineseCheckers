package rules;

import game.board.Board;
import game.move.Move;

public interface GameRuleSet {
  boolean isValidMove(Move move, int playerid, Board board);
  public boolean isGameOver(Board board, int playerId);
}
