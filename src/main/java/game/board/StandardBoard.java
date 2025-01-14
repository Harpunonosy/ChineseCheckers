package game.board;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import game.move.Move;
import rules.StandardRuleSet;

public class StandardBoard implements Board {
    private Map<Point, CellVertex> vertices;
    private CellVertex[][] matrix;

    public StandardBoard() {
        vertices = new HashMap<>();
        matrix = new CellVertex[25][17]; // Przykładowy rozmiar planszy
    }

    @Override
    public void initialize() {
        // Inicjalizacja wierzchołków
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 17; y++) {
                if (isValidPosition(x, y)) {
                    CellVertex vertex = new CellVertex(new Point(x, y));
                    vertices.put(new Point(x, y), vertex);
                    matrix[x][y] = vertex;
                }
            }
        }

        // Dodawanie krawędzi
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 17; y++) {
                if (matrix[x][y] != null) {
                    CellVertex vertex = matrix[x][y];
                    addEdges(vertex, x, y);
                }
            }
        }
        initializePawns();
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

    @Override
    public void makeMove(Move move, int playerId) {
        StandardRuleSet ruleSet = new StandardRuleSet();
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

    @Override
    public void display() {
        // Implementacja wyświetlania planszy
        for (int y = 0; y < 17; y++) {
            for (int x = 0; x < 25; x++) {
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

    private void initializePawns() {
        // Ustawienie pionków dla gracza 1 (dolny trójkąt)
        int[][] player1Positions = {
            {12, 16}, {11, 15}, {13, 15}, {10, 14}, {12, 14}, {14, 14}, {9, 13}, {11, 13}, {13, 13}, {15, 13}
        };
        for (int[] pos : player1Positions) {
            if (matrix[pos[0]][pos[1]] != null) {
                matrix[pos[0]][pos[1]].setPawn(new Pawn(1));
            }
        }
    
        // Ustawienie pionków dla gracza 2 (górny trójkąt)
        int[][] player2Positions = {
            {12, 0}, {11, 1}, {13, 1}, {10, 2}, {12, 2}, {14, 2}, {9, 3}, {11, 3}, {13, 3}, {15, 3}
        };
        for (int[] pos : player2Positions) {
            if (matrix[pos[0]][pos[1]] != null) {
                matrix[pos[0]][pos[1]].setPawn(new Pawn(2));
            }
        }
    }
}