package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat; import static org.hamcrest.Matchers.*;

import java.util.*;

@SpringBootTest
@Transactional
public class SpringGameRepositoryTest {

    @Autowired
    private SpringGameRepository springGameRepository;

    @Test
    @DisplayName("findByPersonIdTest")
    void findByPersonIdTest() {
        Optional<List<Game>> optionalGames = springGameRepository.findByPersonId(1);
        List<Game> games = optionalGames.orElseThrow();
        assertSame(games.size(), 2);

        Game tmpgame1 = games.get(0);
        Game tmpgame2 = games.get(1);

        Game game1;
        Game game2;

        //Sorteer de games uit de DB, om zo makkelijker te testen.
        //persoon zou game_id 1 & 3 hebben!
        if(tmpgame1.getId() == 1) {
            game1 = tmpgame1;
            game2 = tmpgame2;
        } else {
            game1 = tmpgame2;
            game2 = tmpgame1;
        }

//        assertEquals(1, game1.getId());
//        assertEquals(100, game1.getScore());
//        Person person = game1.getPerson();

//        assertEquals(3, game2.getId());
//        assertEquals(100, game1.getScore());
//        Person person2 = game2.getPerson();
//
//        assertEquals(1, person.getId());
//        assertEquals("Fons Thijssen", person.getName());
//        assertEquals("FS Fons", person.getAccount().getName());
//        assertEquals("1234", person.getAccount().getPassword());
//        assertEquals(1, person.getAccount().getId());
//        assertSame(Role.PLAYER, person.getRole());
//
//        assertEquals(1, person2.getId());
//        assertEquals("Fons Thijssen", person2.getName());
//        assertEquals("FS Fons", person2.getAccount().getName());
//        assertEquals("1234", person2.getAccount().getPassword());
//        assertEquals(1, person2.getAccount().getId());
//        assertSame(Role.PLAYER, person2.getRole());

        //game_id = 1
        Set<Round> rounds = game1.getRounds();
        ArrayList<Round> listRounds = new ArrayList<>(rounds);
        assertEquals(3, listRounds.size());

//        ArrayList<Round> sortedRounds = new ArrayList<>(listRounds);
//        while (!(sortedRounds.get(0).getRoundOfGame() < sortedRounds.get(1).getRoundOfGame()
//                && sortedRounds.get(1).getRoundOfGame() < sortedRounds.get(2).getRoundOfGame())) {
//            for(int i = 0; i < sortedRounds.size(); i++) {
//                if(i <= (sortedRounds.size() - 2)) {
//                    if(sortedRounds.get(i).getRoundOfGame() > sortedRounds.get(i + 1).getRoundOfGame()) {
//                        //Wissel ze dan om
//                        Round roundBeforeSet = sortedRounds.get(i);
//                        sortedRounds.set(i, sortedRounds.get(i + 1));
//                        sortedRounds.set(i + 1, roundBeforeSet);
//                    }
//                }
//            }
//        }

//        Round round1 = listRounds.get(0);
//        Round round2 = listRounds.get(1);
//        Round round3 = listRounds.get(2);
//
//        //assertThat, om er zeker van te zijn dat de rondes in ieder geval uit de DB komen/juiste ID's hebben.
//        assertThat(round1.getRoundOfGame(), anyOf(is(1), is(2), is(3)));
//        assertThat(round2.getRoundOfGame(), anyOf(is(1), is(2), is(3)));
//        assertThat(round3.getRoundOfGame(), anyOf(is(1), is(2), is(3)));
//        assertNotEquals(round1.getRoundOfGame(), round2.getRoundOfGame());
//        assertNotEquals(round1.getRoundOfGame(), round3.getRoundOfGame());
//        assertNotEquals(round2.getRoundOfGame(), round3.getRoundOfGame());

        //Als je er zeker van bent, dat alle drie de juiste identifiers hebben, kan je eroverheenlopen en de waardes checken.

//        assertEquals(new Word("NAJAAR"), round1.getWord());
//        assertEquals(new Word("NAJAAR"), round1.getFirstHint());
//        assertEquals(new Word("RISKANT"), round2.getWord());
//        assertEquals(new Word("RISKANT"), round2.getFirstHint());
//        assertEquals(new Word("WEDERGA"), round3.getWord());
//        assertEquals(new Word("WEDERGA"), round3.getFirstHint());



        //game_id = 3
        Set<Round> rounds2 = game2.getRounds();






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
//        ArrayList<Round> roundsarraylist = new ArrayList(rounds);
//        System.out.println(roundsarraylist.get(0).getTurns().iterator().next().getGuess());
//        System.out.println(roundsarraylist.get(1).getTurns().iterator().next().getGuess());
        assertEquals(2, rounds.size());
        ArrayList<Round> arrayListRound = new ArrayList<>(rounds);


//        Round tmpround1 = arrayListRound.get(0);
//        Round tmpround2 = arrayListRound.get(1);
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

//        Round round1;
//        Round round2;

        //Sorteer de rounds uit de DB, om zo makkelijker te testen.
        //&& 2 == tmpround2.getRoundOfGame()
//        if(1 == tmpround1.getRoundOfGame()) {
//            round1 = tmpround1; //1 roundOfGame
//            round2 = tmpround2; //2 roundOfGame
//        }
//        else {
//            round1 = tmpround2;
//            round2 = tmpround1;
//        }

        System.out.println("Dit is round: ");
        System.out.println(round);
        assertEquals(1, round.getRoundOfGame());
        assertEquals(new Word("RISKANT"), round.getWord());
        ArrayList<Turn> turnsRound1 = new ArrayList<>(round.getTurns());
        assertEquals(1, turnsRound1.size());
        Turn turn = turnsRound1.get(0);
        assertEquals(new Word("AANPAK"), turn.getGuess());
        assertEquals(new Word("AANLEG"), turn.getHint());

        System.out.println("Dit is round2: ");
        System.out.println(round2);
        assertEquals(2, round2.getRoundOfGame());
        ArrayList<Turn> turnsRound2 = new ArrayList<>(round2.getTurns());
        assertEquals(1, turnsRound2.size());
        Turn turn2 = turnsRound2.get(0);
        assertEquals(new Word("RASUUR"), turn2.getGuess());
        assertEquals(new Word("VUURGOD"), turn2.getHint());

//        assertEquals(2, round2.getRoundOfGame());
//        assertEquals(new Word("WEDERGA"), round2.getWord());
//        assertEquals(new Word("WEDERGA"), round2.getFirstHint());
//
//        ArrayList<Turn> turnsRound2 = new ArrayList<>(round2.getTurns());
//
//        assertEquals(1, turnsRound2.size());
//        Turn turn2 = turnsRound2.get(0);
//        System.out.println(turn2.getGuess());
//        System.out.println(turn2.getHint());
//        assertEquals(new Word("WEDERGA"), turn2.getGuess());
//        assertEquals(new Word("WEDERGA"), turn2.getHint());
    }

//    @Test
//    @DisplayName("removeById")
//    void removeById() {
//        Optional<Game> optionalGame = springGameRepository.findById(2);
//        Game game = optionalGame.orElseThrow();
//        springGameRepository.delete(game);
//        Optional<Game> optionalGame2 = springGameRepository.findById(2);
//        assertThrows(NoSuchElementException.class, () -> {
//            Game game1 = optionalGame2.orElseThrow();
//        });
//    }
}