package game.board;

public class Main {
    public static void main(String[] args) {
        StandardBoard board = new StandardBoard();
        board.initialize();
        board.display();
        CellVertex vertex = board.getVertexAt(12, 10);
        for(CCEdge edge : vertex.getEdges()){
            System.out.println("WIERZCHOŁEK DO: " + edge.getDestVertex().getLocation());
        }
    }
}