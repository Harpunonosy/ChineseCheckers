package game.board;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CellVertex implements Serializable {
    private static final long serialVersionUID = 1L;
    private Point location;
    private Pawn pawn;
    private List<CCEdge> edges;

    public CellVertex(Point location) {
        this(location, 0);
    }

    public CellVertex(Point location, int content) {
        this.location = location;
        this.edges = new ArrayList<>();
        this.pawn = null;
    }
    
    public Point getLocation() {
        return location;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public List<CCEdge> getEdges() {
        return edges;
    }

    public void addEdge(CCEdge edge) {
        edges.add(edge);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CellVertex that = (CellVertex) obj;
        return location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}