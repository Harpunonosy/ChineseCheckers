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
    private Map<Point, CellVertex> vertices; // Nie wiem czy to będzie przydatne
    private CellVertex[][] matrix; // Plansza
    private Map<Integer, Integer> playerTargetRegions; // Do przypisywania kto ma gdzie iść

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

    //sprawdza czy powinien być vertex na danych współrzędnych
    protected boolean isValidPosition(int x, int y) {
        if((x+y) % 2 != 0) return false;
        // Przykładowa logika sprawdzania, czy pozycja jest prawidłowa
        if (x < 0 || x >= 25 || y < 0 || y >= 17) {
            return false;
        }
        // Logika dla kształtu gwiazdy Dawida
        if ((y == 0 || y == 16) && (x != 12)) return false;
        if ((y == 1 || y == 15) && (x < 11 || x > 13)) return false;
        if ((y == 2 || y == 14) && (x < 10 || x > 14)) return false;
        if ((y == 3 || y == 13) && (x < 9 || x > 15)) return false;
        //pomijamy ten rząd bo tam mogą być wszędzie gdzie parzyście
        //ten również, bo pozostały w nim tylko miejsca o wsp. nieparzystych
        if ((y == 6 || y == 10) && (x < 2 || x > 22)) return false;
        if ((y == 7 || y == 9) && (x < 3 || x > 21)) return false;
        if (y == 8 && (x < 4 || x > 20)) return false;
        return true;
    }

    private void addEdges(CellVertex vertex, int x, int y) {
        // Dodawanie krawędzi do wierzchołka tylko wtedy, gdy istnieją sąsiednie wierzchołki
        if (isValidPosition(x - 1, y - 1)) addEdge(vertex, x - 1, y - 1); // Na skos w lewo-górę
        if (isValidPosition(x - 1, y + 1)) addEdge(vertex, x - 1, y + 1); // Na skos w prawo-górę
        if (isValidPosition(x + 1, y - 1)) addEdge(vertex, x + 1, y - 1); // Na skos w lewo-dół
        if (isValidPosition(x + 1, y + 1)) addEdge(vertex, x + 1, y + 1); // Na skos w prawo-dół
        if (isValidPosition(x - 2, y)) addEdge(vertex, x - 2, y); // Dwa pola w lewo
        if (isValidPosition(x + 2, y)) addEdge(vertex, x + 2, y); // Dwa pola w prawo
    }

    private void addEdge(CellVertex vertex, int x, int y) {
        if (x >= 0 && x < 25 && y >= 0 && y < 17 && matrix[x][y] != null) {
            CellVertex neighbor = matrix[x][y];
            CCEdge edge = new CCEdge(vertex, neighbor);
            vertex.addEdge(edge);
        }
    }

    public CellVertex getVertexAt(int x, int y) {
        return vertices.get(new Point(x, y));
    }

    public CellVertex[][] getMatrix(){
        return matrix;
    }

    @Override
    public void makeMove(Move move, int playerId, GameRuleSet ruleSet) {
        if (ruleSet.isValidMove(move, playerId, this)) {
            // Implementacja wykonania ruchu
            CellVertex start = matrix[move.getStartX()][move.getStartY()];
            CellVertex end = matrix[move.getEndX()][move.getEndY()];
            // Przenieś pionek z wierzchołka start do wierzchołka end
            end.setPawn(start.getPawn());
            start.setPawn(null);
        } else {
            System.out.println("Ruch jest nielegalny");
        }
    }

    //TODO DELETE THAT LATER WHEN GUI IS READY
    public boolean teleportPawn(int startX, int startY, int endX, int endY) {
        CellVertex startVertex = getVertexAt(startX, startY);
        CellVertex endVertex = getVertexAt(endX, endY);

        if (startVertex != null && startVertex.getPawn() != null && endVertex != null && endVertex.getPawn() == null) {
            endVertex.setPawn(startVertex.getPawn());
            startVertex.setPawn(null);
            return true;
        }
        return false;
    }

    @Override
    public void display() {
        // Implementacja wyświetlania planszy
        for (int y = 0; y < 17; y++) {
            for (int x = 0;  x < 25; x++) {
                if (matrix[x][y] != null) {
                    Pawn pawn = matrix[x][y].getPawn();
                    if (pawn != null) {
                        System.out.print(pawn.getPlayerId() + " ");
                    } else {
                        System.out.print("0 ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public void setPlayersToRegions(int numberOfPlayers){
        switch(numberOfPlayers){
            case 2:
                playerTargetRegions = new HashMap<>();
                //player 1
                playerTargetRegions.put(1,2);
                initializePlayerPawns(1,11); //15 pieces for 2 players
                //player 2
                playerTargetRegions.put(2,1);
                initializePlayerPawns(2,22); //15 pieces for 2 players
                break;
            case 3:
                playerTargetRegions = new HashMap<>();
                //player1
                playerTargetRegions.put(1,4);
                initializePlayerPawns(1,1);
                //player2
                playerTargetRegions.put(2,6);
                initializePlayerPawns(2,3);
                //player3
                playerTargetRegions.put(3,2);
                initializePlayerPawns(3,5);
                break;
            case 4:
                playerTargetRegions = new HashMap<>();
                //player1
                playerTargetRegions.put(1,5);
                initializePlayerPawns(1,2);
                //player2
                playerTargetRegions.put(2,6);
                initializePlayerPawns(2,3);
                //player3
                playerTargetRegions.put(3,2);
                initializePlayerPawns(3,5);
                //player4
                playerTargetRegions.put(4,3);
                initializePlayerPawns(4,6);
                break;
            case 6:
                playerTargetRegions = new HashMap<>();
                //player1
                playerTargetRegions.put(1,4);
                initializePlayerPawns(1,1);
                //player2
                playerTargetRegions.put(2,5);
                initializePlayerPawns(2,2);
                //player3
                playerTargetRegions.put(3,6);
                initializePlayerPawns(3,3);
                //player4
                 playerTargetRegions.put(4,1);
                initializePlayerPawns(4,4);
                //player5
                playerTargetRegions.put(5,2);
                initializePlayerPawns(5,5);
                //player6
                playerTargetRegions.put(6,3);
                initializePlayerPawns(6,6);
                break;

        }
    }

    private void initializePlayerPawns(int playerId, int targetRegion) { //ustawia pionki playera o danym id według przypisanemu mu regionu startowego
        int[][] positions = getRegion(targetRegion);
        for (int[] pos : positions) {
            if (matrix[pos[0]][pos[1]] != null) {
                matrix[pos[0]][pos[1]].setPawn(new Pawn(playerId));
            }
        }
    }

    public int[][] getRegion(int Region) { //Regions are counted from the bottom one as 1 to 6 in circle
        switch (Region) {
            case 1:
                return new int[][]{
                    {12, 16}, {11, 15}, {13, 15}, {10, 14}, {12, 14}, {14, 14}, {9, 13}, {11, 13}, {13, 13}, {15, 13}
                };
            case 4:
                return new int[][]{
                    {12, 0}, {11, 1}, {13, 1}, {10, 2}, {12, 2}, {14, 2}, {9, 3}, {11, 3}, {13, 3}, {15, 3}
                };
            case 3:
                return new int[][]{
                    {0, 4}, {2, 4}, {4, 4}, {6, 4}, {1, 5}, {3, 5}, {5, 5}, {2, 6}, {4, 6}, {3,7}
                };
            case 6:
                return new int[][]{
                    {18, 12}, {20, 12}, {22, 12}, {24, 12}, {19, 11}, {21, 11}, {23, 11}, {20, 10}, {22, 10}, {21,9}
                };
            case 5:
                return new int[][]{
                    {18, 4}, {20, 4}, {22, 4}, {24, 4}, {19, 5}, {21, 5}, {23, 5}, {20, 6}, {22, 6}, {21,7}
                };
            case 2:
                return new int[][]{
                    {0, 12}, {2, 12}, {4, 12}, {6, 12}, {1, 11}, {3, 11}, {5, 11}, {2, 10}, {4, 10}, {3,9}
                };
            case 11: //Special case when 2 people are playing (15 pieces) FIRST PLAYER
                return new int[][]{
                    {12, 16}, {11, 15}, {13, 15}, {10, 14}, {12, 14}, {14, 14}, {9, 13}, {11, 13}, {13, 13}, {15, 13}, {8, 12}, {10,12}, {12,12}, {14,12}, {16,12}
                };
            case 22: //Special case when 2 people are playing (15 pieces) SECOND PLAYER
                return new int[][]{
                    {12, 0}, {11, 1}, {13, 1}, {10, 2}, {12, 2}, {14, 2}, {9, 3}, {11, 3}, {13, 3}, {15, 3}, {8, 4}, {10, 4}, {12, 4}, {14, 4}, {16, 4}
                };
            default:
                return new int[0][0];
        }
    }

    public Map<Integer, Integer> getPlayersTargetRegions() {
        return playerTargetRegions;
    }

}