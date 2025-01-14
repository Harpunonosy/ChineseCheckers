package rules;
import game.board.Board;
import game.board.CCEdge;
import game.board.CellVertex;
import game.move.Move;

public class StandardRuleSet implements GameRuleSet{

  @Override
  public boolean isValidMove(Move move, int playerid, Board board){
   CellVertex start = board.getVertexAt(move.getStartX(), move.getStartY());
    CellVertex end = board.getVertexAt(move.getEndX(), move.getEndY());

    // Check if the start position contains the player's pawn
    if (start == null || start.getPawn() == null || start.getPawn().getPlayerId() != playerid || end.getPawn() != null) {
        return false;
    }

    // Check if there is a direct edge between start and end positions
    for (CCEdge edge : start.getEdges()) {
        if (edge.getDestVertex().equals(end)) {
            return true;
        }
    }

    return false;
  }

  @Override
  public boolean isGameOver(Board board){
    //TODO
    return true;
  }
}
