package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class CellVertex {
    private Point location;
    private int content;
    private List<CCEdge> edges;

    public CellVertex(Point location) {
        this(location, 0);
    }

    public CellVertex(Point location, int content) {
        this.location = location;
        this.content = content;
        this.edges = new ArrayList<>();
    }

    public Point getLocation() {
        return location;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
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