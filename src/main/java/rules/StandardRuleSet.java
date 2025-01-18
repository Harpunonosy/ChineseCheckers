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

    private boolean isMoveWithinAllowedRegions(Move move, int playerId, Board board) {
        int[][] playerRegion = board.getRegion(board.getPlayersTargetRegions().get(playerId));
        int[][] startRegion = board.getRegion(getStartRegionForPlayer(playerId));

        // Check if the move is within the player's allowed regions
        if (isPositionInRegion(move.getEndX(), move.getEndY(), playerRegion) ||
            isPositionInRegion(move.getEndX(), move.getEndY(), startRegion)) {
            return true;
        }

        // Check if the move ends in another player's target region
        for (int otherPlayerId : board.getPlayersTargetRegions().keySet()) {
            if (otherPlayerId != playerId) {
                int[][] otherPlayerRegion = board.getRegion(board.getPlayersTargetRegions().get(otherPlayerId));
                if (isPositionInRegion(move.getEndX(), move.getEndY(), otherPlayerRegion)) {
                    return false;
                }
            }
        }

        // Check if the move ends in any other player's target region
        for (int region = 1; region <= 6; region++) {
            if (region != board.getPlayersTargetRegions().get(playerId) &&
                !board.getPlayersTargetRegions().containsValue(region)) {
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

    private int getStartRegionForPlayer(int playerId) {
        switch (playerId) {
            case 1: return 11;
            case 2: return 22;
            case 3: return 3;
            case 4: return 4;
            case 5: return 5;
            case 6: return 6;
            default: return -1;
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