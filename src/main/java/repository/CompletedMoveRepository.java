package repository;

import game.CompletedMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedMoveRepository extends JpaRepository<CompletedMove, Long> {
}
