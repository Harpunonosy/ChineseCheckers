package repository;

import game.CompletedGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedGameRepository extends JpaRepository<CompletedGame, Long> {
}
