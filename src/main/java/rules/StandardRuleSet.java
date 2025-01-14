package rules;
import game.board.Board;
import game.move.Move;

public class StandardRuleSet implements GameRuleSet{

  @Override
  public boolean isValidMove(Move move, int playerid, Board board){
    //TODO
    return true;
  }

  @Override
  public boolean isGameOver(Board board){
    //TODO
    return true;
  }
}
