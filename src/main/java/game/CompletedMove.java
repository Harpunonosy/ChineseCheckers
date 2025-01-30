package game;

import game.move.Move;
import jakarta.persistence.*;

@Entity
public class CompletedMove {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int moveNumber;

    @ManyToOne
    @JoinColumn(name = "completed_game_id")
    private CompletedGame completedGame;

    public CompletedGame getCompletedGame() {
        return completedGame;
    }

    public void setCompletedGame(CompletedGame completedGame) {
        this.completedGame = completedGame;
    }

    public CompletedMove(Move move, int moveNumber) {
        this.startX = move.getStartX();
        this.startY = move.getStartY();
        this.endX = move.getEndX();
        this.endY = move.getEndY();
        this.moveNumber = moveNumber;
    }

    public CompletedMove() {

    }

    public int getMoveNumber() {
        return moveNumber;
    }
    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    @Override
    public String toString() {
        return "CompletedMoves{move number "+ moveNumber +
                ": startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                '}';
    }
}
