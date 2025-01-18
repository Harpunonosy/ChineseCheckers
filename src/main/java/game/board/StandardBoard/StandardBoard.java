package game.board.StandardBoard;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import game.board.Board;
import game.board.CCEdge;
import game.board.CellVertex;
import game.board.Pawn;
import game.move.Move;
import rules.GameRuleSet;

public class StandardBoard implements Board, Serializable {
    private Map<Point, CellVertex> vertices;
    private CellVertex[][] matrix;
    private Map<Integer, Integer> playerTargetRegions;

    public StandardBoard() {
        vertices = new HashMap<>();
        matrix = new CellVertex[25][17];
    }

    @Override
    public void initialize() {
        initializeVertices();
        initializeEdges();
    }

    private void initializeVertices() {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 17; y++) {
                if (isValidPosition(x, y)) {
                    CellVertex vertex = new CellVertex(new Point(x, y));
                    vertices.put(new Point(x, y), vertex);
                    matrix[x][y] = vertex;
                }
            }
        }
    }

    private void initializeEdges() {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 17; y++) {
                if (matrix[x][y] != null) {
                    CellVertex vertex = matrix[x][y];
                    addEdges(vertex, x, y);
                }
            }
        }
    }

    public boolean isValidPosition(int x, int y) {
        if ((x + y) % 2 != 0) return false;
        if (x < 0 || x >= 25 || y < 0 || y >= 17) return false;
        if ((y == 0 || y == 16) && (x != 12)) return false;
        if ((y == 1 || y == 15) && (x < 11 || x > 13)) return false;
        if ((y == 2 || y == 14) && (x < 10 || x > 14)) return false;
        if ((y == 3 || y == 13) && (x < 9 || x > 15)) return false;
        if ((y == 6 || y == 10) && (x < 2 || x > 22)) return false;
        if ((y == 7 || y == 9) && (x < 3 || x > 21)) return false;
        if (y == 8 && (x < 4 || x > 20)) return false;
        return true;
    }

    private void addEdges(CellVertex vertex, int x, int y) {
        addEdgeIfValid(vertex, x - 1, y - 1);
        addEdgeIfValid(vertex, x - 1, y + 1);
        addEdgeIfValid(vertex, x + 1, y - 1);
        addEdgeIfValid(vertex, x + 1, y + 1);
        addEdgeIfValid(vertex, x - 2, y);
        addEdgeIfValid(vertex, x + 2, y);
    }

    private void addEdgeIfValid(CellVertex vertex, int x, int y) {
        if (isValidPosition(x, y)) {
            CellVertex neighbor = matrix[x][y];
            CCEdge edge = new CCEdge(vertex, neighbor);
            vertex.addEdge(edge);
        }
    }

    @Override
    public CellVertex getVertexAt(int x, int y) {
        return vertices.get(new Point(x, y));
    }

    @Override
    public CellVertex[][] getMatrix() {
        return matrix;
    }

    @Override
    public void makeMove(Move move, int playerId, GameRuleSet ruleSet) {
        if (ruleSet.isValidMove(move, playerId, this)) {
            CellVertex start = matrix[move.getStartX()][move.getStartY()];
            CellVertex end = matrix[move.getEndX()][move.getEndY()];
            end.setPawn(start.getPawn());
            start.setPawn(null);
        } else {
            System.out.println("Ruch jest nielegalny");
        }
    }
    @Override
    public void setPlayersToRegions(int numberOfPlayers) {
        playerTargetRegions = new HashMap<>();
        switch (numberOfPlayers) {
            case 2:
                setPlayerTargetRegion(1, 4, 11); // 15 PEACES
                setPlayerTargetRegion(2, 1, 22); // 15 PEACES
                break;
            case 3:
                setPlayerTargetRegion(1, 4, 1);
                setPlayerTargetRegion(2, 6, 3);
                setPlayerTargetRegion(3, 2, 5);
                break;
            case 4:
                setPlayerTargetRegion(1, 5, 2);
                setPlayerTargetRegion(2, 6, 3);
                setPlayerTargetRegion(3, 2, 5);
                setPlayerTargetRegion(4, 3, 6);
                break;
            case 6:
                setPlayerTargetRegion(1, 4, 1);
                setPlayerTargetRegion(2, 5, 2);
                setPlayerTargetRegion(3, 6, 3);
                setPlayerTargetRegion(4, 1, 4);
                setPlayerTargetRegion(5, 2, 5);
                setPlayerTargetRegion(6, 3, 6);
                break;
        }
    }

    private void setPlayerTargetRegion(int playerId, int targetRegion, int startRegion) {
        playerTargetRegions.put(playerId, targetRegion);
        initializePlayerPawns(playerId, startRegion);
    }

    private void initializePlayerPawns(int playerId, int targetRegion) {
        int[][] positions = getRegion(targetRegion);
        for (int[] pos : positions) {
            if (matrix[pos[0]][pos[1]] != null) {
                matrix[pos[0]][pos[1]].setPawn(new Pawn(playerId));
            }
        }
    }

    @Override
    public int[][] getRegion(int region) {
        switch (region) {
            case 1:
                return new int[][]{{12, 16}, {11, 15}, {13, 15}, {10, 14}, {12, 14}, {14, 14}, {9, 13}, {11, 13}, {13, 13}, {15, 13}};
            case 4:
                return new int[][]{{12, 0}, {11, 1}, {13, 1}, {10, 2}, {12, 2}, {14, 2}, {9, 3}, {11, 3}, {13, 3}, {15, 3}};
            case 3:
                return new int[][]{{0, 4}, {2, 4}, {4, 4}, {6, 4}, {1, 5}, {3, 5}, {5, 5}, {2, 6}, {4, 6}, {3, 7}};
            case 6:
                return new int[][]{{18, 12}, {20, 12}, {22, 12}, {24, 12}, {19, 11}, {21, 11}, {23, 11}, {20, 10}, {22, 10}, {21, 9}};
            case 5:
                return new int[][]{{18, 4}, {20, 4}, {22, 4}, {24, 4}, {19, 5}, {21, 5}, {23, 5}, {20, 6}, {22, 6}, {21, 7}};
            case 2:
                return new int[][]{{0, 12}, {2, 12}, {4, 12}, {6, 12}, {1, 11}, {3, 11}, {5, 11}, {2, 10}, {4, 10}, {3, 9}};
            case 11:
                return new int[][]{{12, 16}, {11, 15}, {13, 15}, {10, 14}, {12, 14}, {14, 14}, {9, 13}, {11, 13}, {13, 13}, {15, 13}, {8, 12}, {10, 12}, {12, 12}, {14, 12}, {16, 12}};
            case 22:
                return new int[][]{{12, 0}, {11, 1}, {13, 1}, {10, 2}, {12, 2}, {14, 2}, {9, 3}, {11, 3}, {13, 3}, {15, 3}, {8, 4}, {10, 4}, {12, 4}, {14, 4}, {16, 4}};
            default:
                return new int[0][0];
        }
    }

    @Override
    public Map<Integer, Integer> getPlayersTargetRegions() {
        return playerTargetRegions;
    }
}