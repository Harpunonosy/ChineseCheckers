package rules;

import game.board.Board;
import game.move.Move;

import java.util.List;

public interface GameRuleSet {
  boolean isValidMove(Move move, int playerid, Board board);
  public boolean isGameOver(Board board, int playerId);

  List<Move> getAvailableMoves(Move move, int playerId, Board board);
}
