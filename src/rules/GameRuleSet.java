import board.Board;

public interface GameRuleSet {
  boolean isValidMove(Move move, int playerid, Board board);
  boolean isGameOver(Board board);
}
