package game.board;
import game.move.Move;

public interface Board {
  void initialize();
  void display();
  void makeMove(Move move, int playerId);
  public CellVertex getVertexAt(int x, int y);  
}
