package rules;

import game.board.Board;
import game.board.CCEdge;
import game.board.CellVertex;
import game.move.Move;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Implements the BananaJump rule set for the game.
 */
public class BananaJump extends AbstractRuleSet {
    private static final Logger logger = Logger.getLogger(BananaJump.class.getName());

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
            if (midVertex.getPawn() != null && !visited.contains(midVertex)) {
                for (CCEdge jumpEdge : midVertex.getEdges()) {
                    CellVertex jumpDest = jumpEdge.getDestVertex();
                    if (!visited.contains(jumpDest) && jumpDest.getPawn() == null) {
                        logger.info("Jumping from " + midVertex.getLocation() + " to " + jumpDest.getLocation());
                        if (jumpDest.equals(end)) {
                            logger.info("Reached end via jump: " + jumpDest.getLocation());
                            return true;
                        }
                        if (canReachWithMultiJump(jumpDest, end, board, visited)) {
                            return true;
                        }
                    }
                }
            }
        }

        visited.remove(start);
        return false;
    }
}