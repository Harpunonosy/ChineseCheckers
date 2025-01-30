package repository;

import game.CompletedMove;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedMoveRepository extends JpaRepository<CompletedMove, Long> {
}
