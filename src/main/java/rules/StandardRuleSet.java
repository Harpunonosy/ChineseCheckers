package rules;

import game.board.Board;
import game.board.CCEdge;
import game.board.CellVertex;
import game.move.Move;

/**
 * Implements the standard rule set for the game.
 */
public class StandardRuleSet extends AbstractRuleSet {

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

        // Check if the move is a valid jump over another pawn
        int midX = (move.getStartX() + move.getEndX()) / 2;
        int midY = (move.getStartY() + move.getEndY()) / 2;
        CellVertex midVertex = board.getVertexAt(midX, midY);

        if (midVertex != null && midVertex.getPawn() != null) {
            for (CCEdge edge : midVertex.getEdges()) {
                if (edge.getDestVertex().equals(end)) {
                    return true;
                }
            }
        }

        return false;
    }
}