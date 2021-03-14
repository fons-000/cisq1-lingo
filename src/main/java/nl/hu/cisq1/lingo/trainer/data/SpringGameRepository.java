package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringGameRepository extends JpaRepository<Game, Integer> {
    @Override
    List<Game> findAll();

    @Override
    Optional<Game> findById(Integer integer);

    @Override
    <S extends Game> S save(S s);

    @Override
    void deleteById(Integer integer);
}