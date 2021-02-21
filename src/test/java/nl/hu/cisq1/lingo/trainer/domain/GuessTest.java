package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GuessTest {
    //1. We want to indirectly test if the getters work properly in the use cases of creating
    //2. Also: for every use case of creating, the Letter array should spell the exact guess
    //   with the exception of "." for DOT

    //Use cases of creating:
    //3. Creating a guess works with the following restrictions:
    //  the Guess can only contain letters that are in the Letter enum
    //Meaning the following use cases:
    //  3.1 Guesses that are fully capital will return a Guess succesfully
    //  3.2 Guesses can contain one or more "."
    //  3.3 Guesses with one or more small letters will throw an error.
    //  3.4 Guesses with weird symbols will throw an error.
    //  3.5 Guesses can contain an "umlimted" amount of characters

    @Test
    @DisplayName("Creating a guess #1 - Creating a fully capital guess works")
    void creatingGuessSuccesfully() {
        Guess guess = Guess.createGuess("WOORD");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(5, length);
        assertEquals("WOORD", guess.getValue());
        assertSame(5, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        Letter letter4 = letters.get(3);
        Letter letter5 = letters.get(4);
        assertSame(Letter.W, letter);
        assertSame(Letter.O, letter2);
        assertSame(Letter.O, letter3);
        assertSame(Letter.R, letter4);
        assertSame(Letter.D, letter5);
    }

    @Test
    @DisplayName("Creating a guess #2 - Creating a capital guess with one or multiple '.' works")
    void creatingGuessSuccesfully2() {
        Guess guess = Guess.createGuess("WOORD");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(5, length);
        assertEquals("WOORD", guess.getValue());
        assertSame(5, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        Letter letter4 = letters.get(3);
        Letter letter5 = letters.get(4);
        assertSame(Letter.W, letter);
        assertSame(Letter.O, letter2);
        assertSame(Letter.O, letter3);
        assertSame(Letter.R, letter4);
        assertSame(Letter.D, letter5);
    }

    @Test
    @DisplayName("Creating a guess #3 - Guess with one or more small letters will throw an error")
    void creatingGuessThrowsError1() {
        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createGuess("WOoRD");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createGuess("wOoRd");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createGuess("woord");
        });
    }

    @Test
    @DisplayName("Creating a guess #4 - Guesses with weird symbols will throw an error")
    void creatingGuessThrowsError2() {
        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createGuess("WOORD#");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createGuess("wo0458p;");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createGuess("woord/");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createGuess("}{:");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createGuess("(WOORD*");
        });
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #1 5 characters - guesses can only have a length of 5, 6 or 7 characters.")
    void creatingGuessWith5char() {
        Guess guess = Guess.createGuess("WOORD");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(5, length);
        assertEquals("WOORD", guess.getValue());
        assertSame(5, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        Letter letter4 = letters.get(3);
        Letter letter5 = letters.get(4);
        assertSame(Letter.W, letter);
        assertSame(Letter.O, letter2);
        assertSame(Letter.O, letter3);
        assertSame(Letter.R, letter4);
        assertSame(Letter.D, letter5);
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #2 6 characters - guesses can only have a length of 5, 6 or 7 characters.")
    void creatingGuessWith6char() {
        Guess guess = Guess.createGuess("WOORD.");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(6, length);
        assertEquals("WOORD.", guess.getValue());
        assertSame(6, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        Letter letter4 = letters.get(3);
        Letter letter5 = letters.get(4);
        Letter letter6 = letters.get(5);
        assertSame(Letter.W, letter);
        assertSame(Letter.O, letter2);
        assertSame(Letter.O, letter3);
        assertSame(Letter.R, letter4);
        assertSame(Letter.D, letter5);
        assertSame(Letter.DOT, letter6);
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #3 7 characters - guesses can only have a length of 5, 6 or 7 characters.")
    void creatingGuessWith7char() {
        Guess guess = Guess.createGuess("WOORD.S");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(7, length);
        assertEquals("WOORD.S", guess.getValue());
        assertSame(7, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        Letter letter4 = letters.get(3);
        Letter letter5 = letters.get(4);
        Letter letter6 = letters.get(5);
        Letter letter7 = letters.get(6);
        assertSame(Letter.W, letter);
        assertSame(Letter.O, letter2);
        assertSame(Letter.O, letter3);
        assertSame(Letter.R, letter4);
        assertSame(Letter.D, letter5);
        assertSame(Letter.DOT, letter6);
        assertSame(Letter.S, letter7);
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #4 4 characters")
    void creatingGuessWith4char() {
        Guess guess = Guess.createGuess("JAZZ");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(4, length);
        assertEquals("JAZZ", guess.getValue());
        assertSame(4, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        Letter letter4 = letters.get(3);
        assertSame(Letter.J, letter);
        assertSame(Letter.A, letter2);
        assertSame(Letter.Z, letter3);
        assertSame(Letter.Z, letter4);
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #5 8 characters")
    void creatingGuessWith8char() {
        Guess guess = Guess.createGuess("ACCURACY");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(8, length);
        assertEquals("ACCURACY", guess.getValue());
        assertSame(8, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        Letter letter4 = letters.get(3);
        Letter letter5 = letters.get(4);
        Letter letter6 = letters.get(5);
        Letter letter7 = letters.get(6);
        Letter letter8 = letters.get(7);
        assertSame(Letter.A, letter);
        assertSame(Letter.C, letter2);
        assertSame(Letter.C, letter3);
        assertSame(Letter.U, letter4);
        assertSame(Letter.R, letter5);
        assertSame(Letter.A, letter6);
        assertSame(Letter.C, letter7);
        assertSame(Letter.Y, letter8);
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #6 15 characters")
    void creatingGuessWith15char() {
        Guess guess = Guess.createGuess("ACCOMPLISHMENTS");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(15, length);
        assertEquals("ACCOMPLISHMENTS", guess.getValue());
        assertSame(15, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        Letter letter4 = letters.get(3);
        Letter letter5 = letters.get(4);
        Letter letter6 = letters.get(5);
        Letter letter7 = letters.get(6);
        Letter letter8 = letters.get(7);
        Letter letter9 = letters.get(8);
        Letter letter10 = letters.get(9);
        Letter letter11 = letters.get(10);
        Letter letter12 = letters.get(11);
        Letter letter13 = letters.get(12);
        Letter letter14 = letters.get(13);
        Letter letter15 = letters.get(14);
        assertSame(Letter.A, letter);
        assertSame(Letter.C, letter2);
        assertSame(Letter.C, letter3);
        assertSame(Letter.O, letter4);
        assertSame(Letter.M, letter5);
        assertSame(Letter.P, letter6);
        assertSame(Letter.L, letter7);
        assertSame(Letter.I, letter8);
        assertSame(Letter.S, letter9);
        assertSame(Letter.H, letter10);
        assertSame(Letter.M, letter11);
        assertSame(Letter.E, letter12);
        assertSame(Letter.N, letter13);
        assertSame(Letter.T, letter14);
        assertSame(Letter.S, letter15);
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #7 22 characters")
    void creatingWGuessith22char() {
        Guess guess = Guess.createGuess("COUNTERREVOLUTIONARIES");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(22, length);
        assertEquals("COUNTERREVOLUTIONARIES", guess.getValue());
        assertSame(22, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        Letter letter4 = letters.get(3);
        Letter letter5 = letters.get(4);
        Letter letter6 = letters.get(5);
        Letter letter7 = letters.get(6);
        Letter letter8 = letters.get(7);
        Letter letter9 = letters.get(8);
        Letter letter10 = letters.get(9);
        Letter letter11 = letters.get(10);
        Letter letter12 = letters.get(11);
        Letter letter13 = letters.get(12);
        Letter letter14 = letters.get(13);
        Letter letter15 = letters.get(14);
        Letter letter16 = letters.get(15);
        Letter letter17 = letters.get(16);
        Letter letter18 = letters.get(17);
        Letter letter19 = letters.get(18);
        Letter letter20 = letters.get(19);
        Letter letter21 = letters.get(20);
        Letter letter22 = letters.get(21);
        assertSame(Letter.C, letter);
        assertSame(Letter.O, letter2);
        assertSame(Letter.U, letter3);
        assertSame(Letter.N, letter4);
        assertSame(Letter.T, letter5);
        assertSame(Letter.E, letter6);
        assertSame(Letter.R, letter7);
        assertSame(Letter.R, letter8);
        assertSame(Letter.E, letter9);
        assertSame(Letter.V, letter10);
        assertSame(Letter.O, letter11);
        assertSame(Letter.L, letter12);
        assertSame(Letter.U, letter13);
        assertSame(Letter.T, letter14);
        assertSame(Letter.I, letter15);
        assertSame(Letter.O, letter16);
        assertSame(Letter.N, letter17);
        assertSame(Letter.A, letter18);
        assertSame(Letter.R, letter19);
        assertSame(Letter.I, letter20);
        assertSame(Letter.E, letter21);
        assertSame(Letter.S, letter22);
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #10 1 characters")
    void creatingGuessWith1char() {
        Guess guess = Guess.createGuess("A");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(1, length);
        assertEquals("A", guess.getValue());
        assertSame(1, letters.size());
        Letter letter = letters.get(0);
        assertSame(Letter.A, letter);
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #11 2 characters")
    void creatingGuessWith2char() {
        Guess guess = Guess.createGuess("AB");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(2, length);
        assertEquals("AB", guess.getValue());
        assertSame(2, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        assertSame(Letter.A, letter);
        assertSame(Letter.B, letter2);
    };

    @Test
    @DisplayName("Creating a guess #5 - Test #12 3 characters")
    void creatingGuessWith3char() {
        Guess guess = Guess.createGuess("AGO");
        int length = guess.getLength();
        ArrayList<Letter> letters = guess.getLetters();
        assertSame(3, length);
        assertEquals("AGO", guess.getValue());
        assertSame(3, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        assertSame(Letter.A, letter);
        assertSame(Letter.G, letter2);
        assertSame(Letter.O, letter3);
    };
}