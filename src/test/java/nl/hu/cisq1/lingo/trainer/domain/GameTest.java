package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    //GameTest
    //
    //1. Gaat een game aanmaken goed, zitten de juiste waardes erin?
    //2. Zitten de juiste waardes er in na een set?
    //3. Werkt de equals functie?
    //	3.1 Hetzelfde object => goed
    //	3.2 Is null => fout
    //	3.3 andere klasse => fout
    //	3.4 Goede klasse, met goede waardes => goed
    //	3.5 Goede klasse, met foute waardes => fout
    //4. Werkt de addRound functie?
    Game game = new Game();

    @AfterEach
    public void after() {
        game.setScore(300);
    }

    @Test
    @DisplayName("Creating default game has 100 points")
    void creatingGame() {
        Game game = new Game();
        int score = game.getScore();
        assertEquals(100, score);
    }

    @Test
    @DisplayName("Setting attribute works")
    void setAttributes() {
        game.setScore(700);
        int score = game.getScore();
        assertEquals(700, score);
    }

    @Test
    @DisplayName("Equal function test #1: Equal object")
    void equalObject() {
        assertEquals(game, game);
    }

    @Test
    @DisplayName("Equal function test #2: Null")
    void nullObject() {
        assertNotEquals(game, null);
    }

    @Test
    @DisplayName("Equal function test #3: Object with different class")
    void differentClass() {
        Account account = new Account("yolo01", "1234");
        assertNotEquals(game, account);
    }

    @Test
    @DisplayName("Equal function test #4: Object with right class & attribitues")
    void sameAttributesObject() {
        Game game2 = new Game();
        assertEquals(game, game2);
    }

    @Test
    @DisplayName("Equal function test #5: Object with right class & wrong attribitues")
    void wrongAttributesObject() {
        Game game2 = new Game();
        game2.setScore(250);
        Word word = Word.createValidWord("THEFIRE");
        Set<Round> roundsList = game2.getRounds();
        int currentRounds = roundsList.size();
        int nextRoundId = currentRounds + 1;
        Round round = new Round(word, nextRoundId);
        assertTrue(game2.addRound(round));
        assertEquals(1, roundsList.size());
        assertNotEquals(game, game2);
    }

    @Test
    @DisplayName("Equal function test #6: Object with right class & score but not the same rounds")
    void wrongRounds() {
        Game game2 = new Game();
        game2.setScore(300);
        Word word = Word.createValidWord("THEFIRE");
        Set<Round> roundsList = game2.getRounds();
        int currentRounds = roundsList.size();
        int nextRoundId = currentRounds + 1;
        Round round = new Round(word, nextRoundId);
        assertTrue(game2.addRound(round));
        assertEquals(1, roundsList.size());
        assertNotEquals(game, game2);
    }

    @Test
    @DisplayName("Equal function test #7: Object with right class & round but not the same score")
    void wrongScore() {
        Game game2 = new Game();
        game2.setScore(250);
        assertNotEquals(game, game2);
    }

    @Test
    @DisplayName("Add round function works")
    void addRound() {
        Set<Round> roundsList = game.getRounds();
        assertEquals(0, roundsList.size());
        //When the letters of a Word are not in the enum Letters, a NoSuchElementException gets thrown.
        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("THEFire");
        });
        //This should work createWord should work
        Word word = Word.createValidWord("THEFIRE");
        int currentRounds = roundsList.size();
        int nextRoundId = currentRounds + 1;
        Round round = new Round(word, nextRoundId);
        assertTrue(game.addRound(round));
        assertEquals(1, roundsList.size());
        ArrayList<Round> roundsArrayList = new ArrayList<>(roundsList);
        assertSame("THEFIRE", roundsArrayList.get(0).getWord().getValue());
    }
}