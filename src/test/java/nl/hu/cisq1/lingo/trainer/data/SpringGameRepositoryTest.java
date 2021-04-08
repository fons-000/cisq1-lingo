package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;

@SpringBootTest
@Sql({"/data/lingo_words.sql"})
public class SpringGameRepositoryTest {
    Game game = new Game();
    Game game2 = new Game();

    @BeforeEach
    public void beforeEach() {
        //Save deze game!

        Person person = new Person("FS Fons", "1234", "Fons Thijssen", Role.PLAYER);
        game.setPerson(person);
        person.addGame(game);

        Word word = Word.createValidWord("GENEZER");

        Round round = new Round(word, game.getRounds().size() + 1);
        game.addRound(round);
        round.setGame(game);

        Turn turn1 = new Turn(round.getFirstHint(), Word.createValidWord("ZZZZZ"), round.getWord());
        turn1.setTurnRound(round.getTurns().size() + 1);
        turn1.setRound(round);
        assertTrue(round.addTurn(turn1));
        Feedback feedback = turn1.returnFeedbackCurrentTurn();
        turn1.setFeedback(feedback);

        Turn turn2 = new Turn(turn1.returnHintForNextTurn(), Word.createValidWord("GRAPE"), round.getWord());
        turn2.setTurnRound(round.getTurns().size() + 1);
        turn2.setRound(round);
        assertTrue(round.addTurn(turn2));
        //round.setGame does the trick for the DB, because round owns the association.
        //game.setPerson does the trick for the DB, because game owns the association

        //Update deze game!
        //Expected heeft geen ID's
        game2 = springGameRepository.findById(4).orElseThrow();
//        System.out.println("Dit is de game_id: ");
//        System.out.println(game2.getId());
        game2.setScore(1000);
        //Houd rekening, word van round & turn moet ook in de words table zitten!
        Round round1 = new Round(Word.createValidWord("AMPEX"), game2.getRounds().size() + 1);
        Turn turn3 = new Turn(round1.getFirstHint(), Word.createValidWord("AMOVE"), round1.getWord());
        turn3.setTurnRound(round.getTurns().size() + 1);
        assertTrue(round1.addTurn(turn3));
        turn3.setRound(round1);
        game2.addRound(round1);
        round1.setGame(game2);

        List<Round> game2Rounds = new ArrayList<>(game2.getRounds());
        Collections.sort(game2Rounds);
        game2.setRounds(game2Rounds);
    }

    @Autowired
    private SpringGameRepository springGameRepository;

    @Test
    @DisplayName("updateGame")
    public void updateGame() {
        springGameRepository.save(this.game2);
        Game dbGame = springGameRepository.findById(this.game2.getId()).orElseThrow();
        List<Round> dbRoundsList = new ArrayList<>(dbGame.getRounds());
        Collections.sort(dbRoundsList);
        dbGame.setRounds(dbRoundsList);
        assertEquals(this.game2, dbGame);
    }

    @Test
    @DisplayName("saveGame")
    public void saveGame() {
        System.out.println(game.showGame());
        springGameRepository.save(this.game);
        Game dbGame = springGameRepository.findById(this.game.getId()).orElseThrow();
        //Pakt de eerste en enige ronde uit de DB => en daar de turns van
        // sortTurns
        List<Round> dbRounds = new ArrayList<>(dbGame.getRounds());
        List<Turn> dbTurns = new ArrayList<>(dbRounds.get(0).getTurns());
        Collections.sort(dbTurns);
        dbGame.getRounds().iterator().next().setTurns(dbTurns);

//        assertEquals(100, dbGame.getScore());
//        Person dbPerson = dbGame.getPerson();
//        assertEquals(this.game.getPerson(), dbPerson);
//        assertEquals(1, dbGame.getRounds().size());
//        System.out.println(this.game);
//        System.out.println(dbGame);
        assertEquals(this.game, dbGame);
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