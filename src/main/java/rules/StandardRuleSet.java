package rules;

import game.board.Board;
import game.board.CCEdge;
import game.board.CellVertex;
import game.move.Move;

public class StandardRuleSet implements GameRuleSet {

    @Override
    public boolean isValidMove(Move move, int playerId, Board board) {
        CellVertex start = board.getVertexAt(move.getStartX(), move.getStartY());
        CellVertex end = board.getVertexAt(move.getEndX(), move.getEndY());

        // Check if the start position contains the player's pawn
        if (start == null || start.getPawn() == null || start.getPawn().getPlayerId() != playerId || end.getPawn() != null) {
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