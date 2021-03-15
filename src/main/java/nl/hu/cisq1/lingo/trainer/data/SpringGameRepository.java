package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SpringGameRepository extends JpaRepository<Game, Integer> {
    @Query("SELECT g.id, g.person, g.score FROM game g WHERE g.person = ?1")
    Optional<List<Game>> findByPersonId(Integer personId);
}