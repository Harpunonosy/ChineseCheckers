package game.board;

import java.io.Serializable;

/**
 * Represents an edge between two vertices on the board.
 */
public class CCEdge implements Serializable {
    private CellVertex srcVertex;
    private CellVertex destVertex;

    public CCEdge(CellVertex srcVertex, CellVertex destVertex) {
        this.srcVertex = srcVertex;
        this.destVertex = destVertex;
    }

    public CellVertex getSrcVertex() {
        return srcVertex;
    }

    public void setSrcVertex(CellVertex srcVertex) {
        this.srcVertex = srcVertex;
    }

    public CellVertex getDestVertex() {
        return destVertex;
    }

    public void setDestVertex(CellVertex destVertex) {
        this.destVertex = destVertex;
    }
}