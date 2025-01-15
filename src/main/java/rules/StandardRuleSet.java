package rules;
import java.util.Map;

import game.board.Board;
import game.board.CCEdge;
import game.board.CellVertex;
import game.board.StandardBoard.StandardBoard;
import game.move.Move;

public class StandardRuleSet implements GameRuleSet{

  @Override
  public boolean isValidMove(Move move, int playerid, Board board){
   CellVertex start = board.getVertexAt(move.getStartX(), move.getStartY());
    CellVertex end = board.getVertexAt(move.getEndX(), move.getEndY());

    // Check if the start position contains the player's pawn
    if (start == null || start.getPawn() == null || start.getPawn().getPlayerId() != playerid) {
        return false;
    }

    if(end.getPawn() != null){
      //TODO CHECK COMBO OR STH
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
    public boolean isGameOver(Board board, int playerId) {
        if (board instanceof StandardBoard) {
            StandardBoard standardBoard = (StandardBoard) board;
            int targetRegion = standardBoard.getPlayerTargetRegions().get(playerId);
            return areAllPawnsInTargetRegion(standardBoard, playerId, targetRegion);
        }
        return false;
    }

    private boolean areAllPawnsInTargetRegion(StandardBoard board, int playerId, int targetRegion) {
        int[][] targetPositions = board.getRegion(targetRegion);
        for (int[] pos : targetPositions) {
            CellVertex vertex = board.getVertexAt(pos[0], pos[1]);
            if (vertex == null || vertex.getPawn() == null || vertex.getPawn().getPlayerId() != playerId) {
                return false;
            }
        }
        return true;
    }
}
