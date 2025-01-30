package service;

import game.CompletedGame;
import game.CompletedMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CompletedGameRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameService {
    private final CompletedGameRepository completedGameRepository;

    @Autowired
    public GameService(CompletedGameRepository completedGameRepository) {
        this.completedGameRepository = completedGameRepository;
    }

    // Zapisuje ukończoną grę do bazy danych
    public void saveCompletedGame(List<CompletedMove> moves, String winner) {
        CompletedGame completedGame = new CompletedGame(
                1,
                LocalDateTime.now().minusMinutes(30),  // Przykładowy czas rozpoczęcia
                LocalDateTime.now(),                   // Czas zakończenia
                moves
        );
        completedGameRepository.save(completedGame);
    }

    // Pobiera wszystkie zapisane gry
    public List<CompletedGame> getAllCompletedGames() {
        return completedGameRepository.findAll();
    }

    // Pobiera pojedynczą zapisaną grę na podstawie ID
    public CompletedGame getCompletedGameById(Long id) {
        return completedGameRepository.findById(id).orElse(null);
    }
}
