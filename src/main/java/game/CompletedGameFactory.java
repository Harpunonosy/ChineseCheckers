package game;

import game.move.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CompletedGameRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompletedGameFactory {
    private final CompletedGameRepository completedGameRepository;

    @Autowired
    public CompletedGameFactory(CompletedGameRepository completedGameRepository) {
        this.completedGameRepository = completedGameRepository;
    }

    public CompletedGame createCompletedGame(List<Move> moveDataList) {
        CompletedGame completedGame = new CompletedGame();
        List<CompletedMove> completedMoves = moveDataList.stream()
                .map(moveData -> {
                    CompletedMove completedMove = new CompletedMove();
                    // ustaw pola completedMove na podstawie moveData
                    completedMove.setCompletedGame(completedGame);
                    return completedMove;
                })
                .collect(Collectors.toList());
        completedGame.setMoves(completedMoves);
        return completedGameRepository.save(completedGame);
    }
}
