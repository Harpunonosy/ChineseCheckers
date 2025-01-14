package board;

public class CCEdge {
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