package server;

import game.board.CellVertex;
import game.board.StandardBoard.StandardBoard;
import game.move.Move;
import rules.GameRuleSet;

import java.awt.Point;
import java.util.*;

public class TargetRegionBotStrategy implements BotStrategy {

    private GameRuleSet ruleSet;

    public void setRuleSet(GameRuleSet ruleset){
       this.ruleSet = ruleset;
    }

    @Override
    public void makeMove(Bot bot) {
        StandardBoard board = (StandardBoard) bot.getServer().getGame().getBoard();
        setRuleSet(bot.getServer().getGame().getGameRuleSet());
        int playerId = bot.getPlayerId();
        int targetRegion = board.getPlayersTargetRegions().get(playerId);
        List<CellVertex> targetVertices = getTargetVertices(board, targetRegion);

        // Find the highest empty cell in the target region
        CellVertex highestEmptyCell = findHighestEmptyCell(targetVertices, board, playerId);
        if (highestEmptyCell != null) {
            Set<CellVertex> rejectedVertices = new HashSet<>();
            Move bestMove;
            do {
                bestMove = findBestMove(board, playerId, highestEmptyCell, rejectedVertices);
                if (bestMove == null) {
                    break;
                }
                CellVertex startVertex = board.getVertexAt(bestMove.getStartX(), bestMove.getStartY());
                int currentLayer = getLayerValue(startVertex, targetRegion);
                int newLayer = getLayerValue(board.getVertexAt(bestMove.getEndX(), bestMove.getEndY()), targetRegion);
                if ((newLayer > currentLayer || !isInTargetVertices(bestMove.getStartX(), bestMove.getStartY(), targetVertices)) &&
                    ruleSet.isValidMove(bestMove, playerId, board)) {
                    bot.getServer().processMove(formatMove(bestMove), playerId);
                    return;
                } else {
                    rejectedVertices.add(startVertex);
                }
            } while (true);
        }

        // Evaluate moves and prioritize them
        Move bestMove;
        bestMove = evaluateMoves(board, playerId, targetVertices);
        if (bestMove != null && ruleSet.isValidMove(bestMove, playerId, board)) {
            bot.getServer().processMove(formatMove(bestMove), playerId);
        }
    }

    private boolean isInTargetVertices(int x, int y, List<CellVertex> targetVertices) {
        for (CellVertex vertex : targetVertices) {
            if (vertex.getLocation().x == x && vertex.getLocation().y == y) {
                return true;
            }
        }
        return false;
    }

    private List<CellVertex> getTargetVertices(StandardBoard board, int targetRegion) {
        List<CellVertex> targetVertices = new ArrayList<>();
        int[][] region = board.getRegion(targetRegion);
        for (int[] pos : region) {
            targetVertices.add(board.getVertexAt(pos[0], pos[1]));
        }
        return targetVertices;
    }

    private CellVertex findHighestEmptyCell(List<CellVertex> targetVertices, StandardBoard board, int playerId) {
        CellVertex highestEmptyCell = null;
        int highestLayer = -1;
        for (CellVertex vertex : targetVertices) {
            if (vertex.getPawn() == null) {
                int layer = getLayerValue(vertex, board.getPlayersTargetRegions().get(playerId));
                if (layer > highestLayer) {
                    highestLayer = layer;
                    highestEmptyCell = vertex;
                }
            }
        }
        return highestEmptyCell;
    }

    private Move findBestMove(StandardBoard board, int playerId, CellVertex target, Set<CellVertex> rejectedVertices) {
        CellVertex[][] matrix = board.getMatrix();
        Move bestMove = null;
        double shortestDistance = Double.MAX_VALUE;

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                CellVertex start = matrix[x][y];
                if (start != null && start.getPawn() != null && start.getPawn().getPlayerId() == playerId && !rejectedVertices.contains(start)) {
                    for (CellVertex neighbor : start.getNeighbors()) {
                        if (neighbor.getPawn() == null) {
                            double distance = neighbor.getLocation().distance(target.getLocation());
                            if (distance < shortestDistance) {
                                shortestDistance = distance;
                                bestMove = new Move(start.getLocation().x, start.getLocation().y, neighbor.getLocation().x, neighbor.getLocation().y);
                            }
                        }
                    }
                }
            }
        }
        return bestMove;
    }

    private Move evaluateMoves(StandardBoard board, int playerId, List<CellVertex> targetVertices) {
        CellVertex[][] matrix = board.getMatrix();
        List<Move> potentialMoves = new ArrayList<>();
        Map<Move, Double> moveScores = new HashMap<>();

        // Find the farthest empty cell in the target region
        CellVertex farthestEmptyCell = findFarthestEmptyCell(targetVertices);

        // Collect all pawns and their distances to the farthest empty cell
        List<CellVertex> pawns = new ArrayList<>();
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                CellVertex start = matrix[x][y];
                if (start != null && start.getPawn() != null && start.getPawn().getPlayerId() == playerId) {
                    pawns.add(start);
                }
            }
        }

        // Evaluate potential moves and assign scores
        for (CellVertex start : pawns) {
            for (CellVertex neighbor : start.getNeighbors()) {
                if (neighbor.getPawn() == null) {
                    Move move = new Move(start.getLocation().x, start.getLocation().y, neighbor.getLocation().x, neighbor.getLocation().y);
                    double score = evaluateMove(start, neighbor, farthestEmptyCell, targetVertices);
                    potentialMoves.add(move);
                    moveScores.put(move, score);
                }
            }
        }

        // Find the best move based on the highest score
        return potentialMoves.stream().max(Comparator.comparingDouble(moveScores::get)).orElse(null);
    }

    private double evaluateMove(CellVertex start, CellVertex neighbor, CellVertex farthestEmptyCell, List<CellVertex> targetVertices) {
        double score = 0.0;

        if(farthestEmptyCell != null){
        double currentDistanceToFarthest = start.getLocation().distance(farthestEmptyCell.getLocation());
        double newDistanceToFarthest = neighbor.getLocation().distance(farthestEmptyCell.getLocation());
        if (newDistanceToFarthest < currentDistanceToFarthest) {
            score += 150;
        }
    }

        // Calculate distance to the target region
        double currentDistance = calculateDistanceToTarget(start, targetVertices);
        double newDistance = calculateDistanceToTarget(neighbor, targetVertices);
        if (newDistance < currentDistance) {
            score += (currentDistance - newDistance) * 10;
        }

        return score;
    }

    private CellVertex findFarthestEmptyCell(List<CellVertex> targetVertices) {
        return targetVertices.stream()
                .filter(vertex -> vertex.getPawn() == null)
                .max(Comparator.comparingDouble(vertex -> calculateDistanceToTarget(vertex, targetVertices)))
                .orElse(null);
    }

    private int getLayerValue(CellVertex vertex, int targetRegion) {
        Point location = vertex.getLocation();
        switch (targetRegion) {
            case 1:
                if (location.y == 16) return 4;
                if (location.y == 15) return 3;
                if (location.y == 14) return 2;
                if (location.y == 13) return 1;
                break;
            case 2:
            case 3:
                if (location.x == 0) return 4;
                if (location.x == 1) return 3;
                if (location.x == 2) return 2;
                if (location.x == 3) return 1;
                break;
            case 4:
                if (location.y == 0) return 4;
                if (location.y == 1) return 3;
                if (location.y == 2) return 2;
                if (location.y == 3) return 1;
                break;
            case 5:
            case 6:
                if (location.x == 24) return 8;
                if (location.x == 23) return 7;
                if (location.x == 22) return 6;
                if (location.x == 21) return 5;
                if (location.x == 20) return 4;
                if (location.x == 19) return 3;
                if (location.x == 18) return 2;
                break;
            default:
                return 0;
        }
        return 0;
    }

    private double calculateDistanceToTarget(CellVertex vertex, List<CellVertex> targetVertices) {
        return targetVertices.stream()
                .mapToDouble(target -> vertex.getLocation().distance(target.getLocation()))
                .min()
                .orElse(Double.MAX_VALUE);
    }

    private String formatMove(Move move) {
        return move.getStartX() + "-" + move.getStartY() + "-" + move.getEndX() + "-" + move.getEndY();
    }
}