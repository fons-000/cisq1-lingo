package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@SpringBootTest
@Transactional
public class SpringGameRepositoryTest {

    @Autowired
    private SpringGameRepository springGameRepository;

    @Test
    @DisplayName("findByPersonIdTest")
    void findByPersonIdTest() {
        Optional<List<Game>> optionalGames = springGameRepository.findByPersonId(2);
        List<Game> games = optionalGames.orElseThrow();
        assertSame(games.size(), 2);
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
        ArrayList<Round> roundsarraylist = new ArrayList(rounds);
        System.out.println(roundsarraylist.get(0).getTurns().iterator().next().getGuess());
        System.out.println(roundsarraylist.get(1).getTurns().iterator().next().getGuess());

        assertEquals(2, rounds.size());
        ArrayList<Round> arrayListRound = new ArrayList<>(rounds);

        Round tmpround1 = arrayListRound.get(0);
        Round tmpround2 = arrayListRound.get(1);


        Round round1;
        Round round2;

        //Sorteer de rounds uit de DB, om zo makkelijker te testen.
        if(1 == tmpround1.getRoundOfGame()) {
            round1 = tmpround1;
            round2 = tmpround2;
        } else {
            round1 = tmpround2;
            round2 = tmpround1;
        }
        assertEquals(1, round1.getRoundOfGame());
        assertEquals(new Word("RISKANT"), round1.getWord());
        assertEquals(new Word("RISKANT"), round1.getFirstHint());
        ArrayList<Turn> turnsRound1 = new ArrayList<>(round1.getTurns());
        assertEquals(1, turnsRound1.size());
        Turn turn = turnsRound1.get(0);
        assertEquals(new Word("RISKANT"), turn.getGuess());
        assertEquals(new Word("RISKANT"), turn.getHint());

        assertEquals(2, round2.getRoundOfGame());
        assertEquals(new Word("WEDERGA"), round2.getWord());
        assertEquals(new Word("WEDERGA"), round2.getFirstHint());

        ArrayList<Turn> turnsRound2 = new ArrayList<>(round2.getTurns());

        assertEquals(1, turnsRound2.size());
        Turn turn2 = turnsRound2.get(0);
//        assertEquals(new Word("WEDERGA"), turn2.getGuess());
//        assertEquals(new Word("WEDERGA"), turn2.getHint());
    }
}