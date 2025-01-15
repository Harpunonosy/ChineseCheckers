package game.board.StandardBoard;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.board.CellVertex;
import game.board.CCEdge;

public class StandardBoardTest {
    private StandardBoard board;

    @BeforeEach
    public void setUp() {
        board = new StandardBoard();
        board.initialize();
    }

    @Test
    public void testVertexInitialization() {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 17; y++) {
                if (board.isValidPosition(x, y)) {
                    assertNotNull(board.getVertexAt(x, y), "Vertex should be initialized at position (" + x + ", " + y + ")");
                } else {
                    assertNull(board.getVertexAt(x, y), "Vertex should not be initialized at position (" + x + ", " + y + ")");
                }
            }
        }
    }

    @Test
    public void testVertexConnections() {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 17; y++) {
                if (board.isValidPosition(x, y)) {
                    CellVertex vertex = board.getVertexAt(x, y);
                    assertNotNull(vertex, "Vertex should be initialized at position (" + x + ", " + y + ")");
                    for (CCEdge edge : vertex.getEdges()) {
                        CellVertex destVertex = edge.getDestVertex();
                        assertTrue(board.isValidPosition(destVertex.getLocation().x, destVertex.getLocation().y),
                                "Edge should connect to a valid vertex at position (" + destVertex.getLocation().x + ", " + destVertex.getLocation().y + ")");
                    }
                }
            }
        }
    }
}