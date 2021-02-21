package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class HintTest {
    //1. We want to indirectly test if the getters work properly in the use cases of creating
    //2. Also: for every use case of creating, the Letter array should spell the exact hint
    //   with the exception of "." for DOT

    //Use cases of creating:
    //3. Creating a hint works with the following restrictions:
    //  the hint can only contain letters that are in the Letter enum
    //Meaning the following use cases:
    //  3.1 Hints that are fully capital will return a hint succesfully
    //  3.2 Hints can contain one or more "."
    //  3.3 Hints with one or more small letters will throw an error.
    //  3.4 Hints with weird symbols will throw an error.
    //  3.5 Hints can contain an "umlimted" amount of characters

    @Test
    @DisplayName("Creating a hint #1 - Creating a fully capital hint works")
    void creatingHintSuccesfully() {
        Hint hint = Hint.createHint("WOORD");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(5, length);
        assertEquals("WOORD", hint.getValue());
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
    @DisplayName("Creating a hint #2 - Creating a capital hint with one or multiple '.' works")
    void creatingHintSuccesfully2() {
        Hint hint = Hint.createHint("WOORD");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(5, length);
        assertEquals("WOORD", hint.getValue());
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
    @DisplayName("Creating a hint #3 - Hint with one or more small letters will throw an error")
    void creatingHintThrowsError1() {
        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createHint("WOoRD");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createHint("wOoRd");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createHint("woord");
        });
    }

    @Test
    @DisplayName("Creating a hint #4 - Hints with weird symbols will throw an error")
    void creatingHintThrowsError2() {
        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createHint("WOORD#");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createHint("wo0458p;");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createHint("woord/");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createHint("}{:");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createHint("(WOORD*");
        });
    }

    @Test
    @DisplayName("Creating a hint #5 - Test #1 5 characters - Hints can only have a length of 5, 6 or 7 characters.")
    void creatingHintWith5char() {
        Hint hint = Hint.createHint("WOORD");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(5, length);
        assertEquals("WOORD", hint.getValue());
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
    @DisplayName("Creating a Hint #5 - Test #2 6 characters - Hints can only have a length of 5, 6 or 7 characters.")
    void creatingHintWith6char() {
        Hint hint = Hint.createHint("WOORD.");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(6, length);
        assertEquals("WOORD.", hint.getValue());
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
    @DisplayName("Creating a Hint #5 - Test #3 7 characters - Hints can only have a length of 5, 6 or 7 characters.")
    void creatingHintWith7char() {
        Hint hint = Hint.createHint("WOORD.S");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(7, length);
        assertEquals("WOORD.S", hint.getValue());
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
    @DisplayName("Creating a Hint #5 - Test #4 4 characters")
    void creatingHintWith4char() {
        Hint hint = Hint.createHint("JAZZ");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(4, length);
        assertEquals("JAZZ", hint.getValue());
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
    @DisplayName("Creating a Hint #5 - Test #5 8 characters")
    void creatingHintWith8char() {
        Hint hint = Hint.createHint("ACCURACY");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(8, length);
        assertEquals("ACCURACY", hint.getValue());
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
    @DisplayName("Creating a Hint #5 - Test #6 15 characters")
    void creatingHintWith15char() {
        Hint hint = Hint.createHint("ACCOMPLISHMENTS");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(15, length);
        assertEquals("ACCOMPLISHMENTS", hint.getValue());
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
    @DisplayName("Creating a Hint #5 - Test #7 22 characters")
    void creatingWHintith22char() {
        Hint hint = Hint.createHint("COUNTERREVOLUTIONARIES");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(22, length);
        assertEquals("COUNTERREVOLUTIONARIES", hint.getValue());
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
    @DisplayName("Creating a Hint #5 - Test #10 1 characters")
    void creatingHintWith1char() {
        Hint hint = Hint.createHint("A");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(1, length);
        assertEquals("A", hint.getValue());
        assertSame(1, letters.size());
        Letter letter = letters.get(0);
        assertSame(Letter.A, letter);
    }

    @Test
    @DisplayName("Creating a Hint #5 - Test #11 2 characters")
    void creatingHintWith2char() {
        Hint hint = Hint.createHint("AB");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(2, length);
        assertEquals("AB", hint.getValue());
        assertSame(2, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        assertSame(Letter.A, letter);
        assertSame(Letter.B, letter2);
    };

    @Test
    @DisplayName("Creating a Hint #5 - Test #12 3 characters")
    void creatingHintWith3char() {
        Hint hint = Hint.createHint("AGO");
        int length = hint.getLength();
        ArrayList<Letter> letters = hint.getLetters();
        assertSame(3, length);
        assertEquals("AGO", hint.getValue());
        assertSame(3, letters.size());
        Letter letter = letters.get(0);
        Letter letter2 = letters.get(1);
        Letter letter3 = letters.get(2);
        assertSame(Letter.A, letter);
        assertSame(Letter.G, letter2);
        assertSame(Letter.O, letter3);
    };
}