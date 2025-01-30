package game;

import game.move.Move;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CompletedGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

//    @ElementCollection
//    private List<String> moves;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<CompletedMove> moves = new ArrayList<>();



    // Bezargumentowy konstruktor wymagany przez JPA
    public CompletedGame() {}

    public CompletedGame(LocalDateTime startTime, LocalDateTime endTime, List<CompletedMove> moves) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.moves = moves;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<CompletedMove> getMoves() {
        return moves;
    }

    public void setMoves(List<CompletedMove> moves) {
        this.moves = moves;
    }


    public void addMove(Move move, int i) {
    }
}
