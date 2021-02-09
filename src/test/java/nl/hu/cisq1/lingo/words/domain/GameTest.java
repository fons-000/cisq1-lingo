package nl.hu.cisq1.lingo.words.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    Game game = new Game(300);

    @AfterEach
    public void after() {
        game.setScore(300);
    }

    @Test
    @DisplayName("Creating score has the right attribute")
    void creatingPerson() {
        Game game = new Game(500);
        int score = game.getScore();
        assertEquals(500, score);
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
        Game game2 = new Game(300);
        assertEquals(game, game2);
    }

    @Test
    @DisplayName("Equal function test #5: Object with right class & wrong attribitues")
    void wrongAttributesObject() {
        Game game2 = new Game(250);
        assertNotEquals(game, game2);
    }

    @Test
    @DisplayName("Addround function works")
    void addRound() {
        ArrayList<Round> roundsList = game.getRounds();
        assertEquals(0, roundsList.size());
        Word word = new Word("Firefighter");
        Round round = new Round(word);
        assertTrue(game.addRound(round));
        assertEquals(1, roundsList.size());
        assertSame("Firefighter", roundsList.get(0).getWord().getValue());
    }
}