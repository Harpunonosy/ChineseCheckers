package board;
import move.Move;

public interface Board {
  void initialize();
  void display();
  void makeMove(Move move, int playerId);  
}
