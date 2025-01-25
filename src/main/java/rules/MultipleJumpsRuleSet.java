package rules;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import game.board.Board;
import game.board.CCEdge;
import game.board.CellVertex;
import game.move.Move;
public class MultipleJumpsRuleSet implements GameRuleSet {
    private static final Logger logger = Logger.getLogger(MultipleJumpsRuleSet.class.getName());
    @Override
    public boolean isValidMove(Move move, int playerId, Board board) {
        CellVertex start = board.getVertexAt(move.getStartX(), move.getStartY());
        CellVertex end = board.getVertexAt(move.getEndX(), move.getEndY());
        // Check if the start position contains the player's pawn
        if (start == null || start.getPawn() == null || start.getPawn().getPlayerId() != playerId || end.getPawn() != null) {
            return false;
        }
        // Check if the move is within the allowed regions
        if (!isMoveWithinAllowedRegions(move, playerId, board)) {
            System.out.println("WYJEBKA 2");
            return false;
        }
        // Check if there is a direct edge between start and end positions
        for (CCEdge edge : start.getEdges()) {
            if (edge.getDestVertex().equals(end)) {
                return true;
            }
        }
        // Use DFS to check if there is a valid multi-jump path from start to end
        return canReachWithMultiJump(start, end, board, new HashSet<>());
    }
    private boolean canReachWithMultiJump(CellVertex start, CellVertex end, Board board, Set<CellVertex> visited) {
      logger.info("Visiting: " + start.getLocation());
      if (start.equals(end)) {
          logger.info("Reached end: " + end.getLocation());
          return true;
      }
  
      visited.add(start);
  
      for (CCEdge edge : start.getEdges()) {
          CellVertex midVertex = edge.getDestVertex();
  
          // Sprawdzamy, czy jest pionek do przeskoczenia
          if (midVertex.getPawn() != null && !visited.contains(midVertex)) {
              for (CCEdge jumpEdge : midVertex.getEdges()) {
                  CellVertex jumpDest = jumpEdge.getDestVertex();
  
                  if (!visited.contains(jumpDest) && jumpDest.getPawn() == null) {
                      // Pobieramy współrzędne
                      int xStart = start.getLocation().x;
                      int yStart = start.getLocation().y;
                      int xEnd = jumpDest.getLocation().x;
                      int yEnd = jumpDest.getLocation().y;
  
                      // Obliczamy różnice w położeniu
                      int dx = xEnd - xStart;
                      int dy = yEnd - yStart;
  
                      // Sprawdzamy legalność skoku
                      if (isValidJump(dx, dy)) {
                          logger.info("Valid jump from " + start.getLocation() + " to " + jumpDest.getLocation());
  
                          if (jumpDest.equals(end)) {
                              logger.info("Reached end via jump: " + jumpDest.getLocation());
                              return true;
                          }
  
                          // Rekurencja
                          if (canReachWithMultiJump(jumpDest, end, board, visited)) {
                              return true;
                          }
                      } else {
                          logger.info("Illegal jump detected: dx = " + dx + ", dy = " + dy);
                      }
                  }
              }
          }
      }
  
      visited.remove(start);
      return false;
  }
  
  // Funkcja sprawdzająca, czy skok jest legalny
  private boolean isValidJump(int dx, int dy) {
      // Przypadki diagonalne
      if (dx == 2 && dy == 2) {
          return true;
      }
      if (dx == 2 && dy == -2) {
          return true;
      }
      if (dx == -2 && dy == 2) {
          return true;
      }
      if (dx == -2 && dy == -2) {
          return true;
      }
  
      // Przypadki horyzontalne
      if (dx == 4 && dy == 0) {
          return true;
      }
      if (dx == -4 && dy == 0) {
          return true;
      }
  
      // W innych przypadkach skok jest nielegalny
      return false;
  }
  
  private boolean isMoveWithinAllowedRegions(Move move, int playerId, Board board) {
    int[][] playerRegion = board.getRegion(board.getPlayersTargetRegions().get(playerId));
    int[][] startRegion = board.getRegion(getStartRegionForPlayer(playerId,board));
    // Check if the move is within the player's allowed regions
    if (isPositionInRegion(move.getEndX(), move.getEndY(), playerRegion) ||
        isPositionInRegion(move.getEndX(), move.getEndY(), startRegion)) {
        return true;
    }
    // Check if the move ends in any other region except the player's start and target regions
    for (int region = 1; region <= 6; region++) {
        if (region != board.getPlayersTargetRegions().get(playerId) &&
            region != getStartRegionForPlayer(playerId, board)) {
            int[][] otherRegion = board.getRegion(region);
            if (isPositionInRegion(move.getEndX(), move.getEndY(), otherRegion)) {
                return false;
            }
        }
    }
    return true;
}

    private boolean isPositionInRegion(int x, int y, int[][] region) {
        for (int[] pos : region) {
            if (pos[0] == x && pos[1] == y) {
                return true;
            }
        }
        return false;
    }
    private int getStartRegionForPlayer(int playerId, Board board) {
        int numberOfPlayers = board.getNumberOfPlayers();
        switch (numberOfPlayers) {
            case 2:
                switch (playerId) {
                    case 1: return 11;
                    case 2: return 22;
                    default: return -1;
                }
            case 3:
                switch (playerId) {
                    case 1: return 1;
                    case 2: return 3;
                    case 3: return 5;
                    default: return -1;
                }
            case 4:
                switch (playerId) {
                    case 1: return 2;
                    case 2: return 3;
                    case 3: return 5;
                    case 4: return 6;
                    default: return -1;
                }
            case 6:
                switch (playerId) {
                    case 1: return 1;
                    case 2: return 2;
                    case 3: return 3;
                    case 4: return 4;
                    case 5: return 5;
                    case 6: return 6;
                    default: return -1;
                }
            default:
                return -1;
        }
    }
    @Override
    public boolean isGameOver(Board board, int playerId) {
        int targetRegion = board.getPlayersTargetRegions().get(playerId);
        return areAllPawnsInTargetRegion(board, playerId, targetRegion);
    }
    private boolean areAllPawnsInTargetRegion(Board board, int playerId, int targetRegion) {
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