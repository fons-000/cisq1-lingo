package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@SpringBootTest
@Sql({"/data/lingo_words.sql"})
public class SpringGameRepositoryTest {
    Game game = new Game();

    @BeforeEach
    public void beforeEach() {
        Person person = new Person("FS Fons", "1234", "Fons Thijssen", Role.PLAYER);
        person.setId(1);
        game = new Game();
        Word word = Word.createValidWord("GENEZER");
        Round round = new Round(word, game.getRounds().size() + 1);
        Turn turn1 = new Turn(round.getFirstHint(), new Word("ZZZZZ"), round.getWord());
        Feedback feedback = turn1.returnFeedbackCurrentTurn();
        turn1.setFeedback(feedback);
        Turn turn2 = new Turn(turn1.returnHintForNextTurn(), new Word("GRAPE"), round.getWord());
        //round.addTurn should do the trick, because turn owns the association
        assertTrue(round.addTurn(turn1));
        assertTrue(round.addTurn(turn2));
        game.addRound(round);
        //round.setGame does the trick for the DB, because round owns the association.
        round.setGame(game);
        //game.setPerson does the trick for the DB, because game owns the association
        game.setPerson(person);
    }

    @Autowired
    private SpringGameRepository springGameRepository;

    @Test
    @DisplayName("updateGame")
    public void updateGame() {
        Game game = springGameRepository.findById(4).orElseThrow();
        game.setScore(1000);
        springGameRepository.save(game);

        Game game1 = springGameRepository.findById(4).orElseThrow();
        assertEquals(1000, game1.getScore());
    }

    @Test
    @DisplayName("saveGame")
    public void saveGame() {
        //Saved nog niet helemaal door
        Game game = springGameRepository.save(this.game);
        System.out.println(game.getId());
        Game dbGame = springGameRepository.findById(game.getId()).orElseThrow();
//        assertEquals(game, dbGame);
    }

    @Test
    @DisplayName("findById")
    void findById() {
        Optional<Game> optionalGame = springGameRepository.findById(2);
        Game game = optionalGame.orElseThrow();
        assertEquals(2, game.getId());
        assertEquals(200, game.getScore());
        Person person = game.getPerson();
        assertEquals(2, person.getId());
        assertEquals("Fons Thijssen", person.getName());
        assertEquals("fons-001", person.getAccount().getName());
        assertEquals("5678", person.getAccount().getPassword());
        assertEquals(2, person.getAccount().getId());
        assertSame(Role.ADMINISTRATOR, person.getRole());

        Set<Round> rounds = game.getRounds();
        assertEquals(2, rounds.size());
        ArrayList<Round> arrayListRound = new ArrayList<>(rounds);

        Round round = arrayListRound.stream()
                .filter(r -> r.getRoundOfGame() == 1)
                .reduce((a, b) -> {
                    throw new RuntimeException("Multiple rounds found with the same id");
                })
                .orElseThrow(() -> new RuntimeException("Round not found"));

        Round round2 = arrayListRound.stream()
                .filter(r -> r.getRoundOfGame() == 2)
                .reduce((a, b) -> {
                    throw new RuntimeException("Multiple rounds found with the same id");
                })
                .orElseThrow(() -> new RuntimeException("Round not found"));

        assertEquals(4, round.getId());
        assertEquals(1, round.getRoundOfGame());
        assertEquals(new Word("RISKANT"), round.getWord());
        ArrayList<Turn> turnsRound1 = new ArrayList<>(round.getTurns());
        assertEquals(1, turnsRound1.size());
        //Hier krijgen we de turn via de round!
        Turn turn = turnsRound1.get(0);
        assertEquals(1, turn.getId());
        assertEquals(1, turn.getTurnRound());
        assertEquals(new Word("AANPAK"), turn.getGuess());
        assertEquals(new Word("AANLEG"), turn.getHint());

        assertEquals(5, round2.getId());
        assertEquals(2, round2.getRoundOfGame());
        assertEquals(new Word("WEDERGA"), round2.getWord());
        ArrayList<Turn> turnsRound2 = new ArrayList<>(round2.getTurns());
        assertEquals(1, turnsRound2.size());
        Turn turn2 = turnsRound2.get(0);
        assertEquals(2, turn2.getId());
        assertEquals(1, turn2.getTurnRound());
        assertEquals(new Word("RASUUR"), turn2.getGuess());
        assertEquals(new Word("VUURGOD"), turn2.getHint());
    }
}