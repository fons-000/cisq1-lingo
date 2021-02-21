package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    //  3.6 Hints with 0 characters will throw an error

    @Test
    @DisplayName("Creating a hint #1 - Creating a fully capital hint works")
    void creatingHintSuccesfully() {
        Hint hint = Hint.createValidHint("WOORD");
        int length = hint.getLength();
        assertSame(5, length);
        assertEquals("WOORD", hint.getValue());
    }

    @Test
    @DisplayName("Creating a hint #2 - Creating a capital hint with one or multiple '.' works")
    void creatingHintSuccesfully2() {
        Hint hint = Hint.createValidHint("WOORD");
        int length = hint.getLength();
        assertSame(5, length);
        assertEquals("WOORD", hint.getValue());
    }

    @Test
    @DisplayName("Creating a hint #3 - Hint with one or more small letters will throw an error")
    void creatingHintThrowsError1() {
        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createValidHint("WOoRD");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createValidHint("wOoRd");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createValidHint("woord");
        });
    }

    @Test
    @DisplayName("Creating a hint #4 - Hints with weird symbols will throw an error")
    void creatingHintThrowsError2() {
        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createValidHint("WOORD#");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createValidHint("wo0458p;");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createValidHint("woord/");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createValidHint("}{:");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createValidHint("(WOORD*");
        });
    }

    @Test
    @DisplayName("Creating a hint #5 - Test #1 5 characters - Hints can only have a length of 5, 6 or 7 characters.")
    void creatingHintWith5char() {
        Hint hint = Hint.createValidHint("WOORD");
        int length = hint.getLength();
        assertSame(5, length);
        assertEquals("WOORD", hint.getValue());
    }

    @Test
    @DisplayName("Creating a Hint #5 - Test #2 6 characters - Hints can only have a length of 5, 6 or 7 characters.")
    void creatingHintWith6char() {
        Hint hint = Hint.createValidHint("WOORD.");
        int length = hint.getLength();
        assertSame(6, length);
        assertEquals("WOORD.", hint.getValue());
    }

    @Test
    @DisplayName("Creating a Hint #5 - Test #3 7 characters - Hints can only have a length of 5, 6 or 7 characters.")
    void creatingHintWith7char() {
        Hint hint = Hint.createValidHint("WOORD.S");
        int length = hint.getLength();
        assertSame(7, length);
        assertEquals("WOORD.S", hint.getValue());
    }

    @Test
    @DisplayName("Creating a Hint #5 - Test #4 4 characters")
    void creatingHintWith4char() {
        Hint hint = Hint.createValidHint("JAZZ");
        int length = hint.getLength();
        assertSame(4, length);
        assertEquals("JAZZ", hint.getValue());
    }

    @Test
    @DisplayName("Creating a Hint #5 - Test #5 8 characters")
    void creatingHintWith8char() {
        Hint hint = Hint.createValidHint("ACCURACY");
        int length = hint.getLength();
        assertSame(8, length);
        assertEquals("ACCURACY", hint.getValue());
    }

    @Test
    @DisplayName("Creating a Hint #5 - Test #6 15 characters")
    void creatingHintWith15char() {
        Hint hint = Hint.createValidHint("ACCOMPLISHMENTS");
        int length = hint.getLength();
        assertSame(15, length);
        assertEquals("ACCOMPLISHMENTS", hint.getValue());
    }

    @Test
    @DisplayName("Creating a Hint #5 - Test #7 22 characters")
    void creatingWHintith22char() {
        Hint hint = Hint.createValidHint("COUNTERREVOLUTIONARIES");
        int length = hint.getLength();
        assertSame(22, length);
        assertEquals("COUNTERREVOLUTIONARIES", hint.getValue());
    }

    @Test
    @DisplayName("Creating a Hint #5 - Test #10 1 characters")
    void creatingHintWith1char() {
        Hint hint = Hint.createValidHint("A");
        int length = hint.getLength();
        assertSame(1, length);
        assertEquals("A", hint.getValue());
    }

    @Test
    @DisplayName("Creating a Hint #5 - Test #11 2 characters")
    void creatingHintWith2char() {
        Hint hint = Hint.createValidHint("AB");
        int length = hint.getLength();
        assertSame(2, length);
        assertEquals("AB", hint.getValue());
    };

    @Test
    @DisplayName("Creating a Hint #5 - Test #12 3 characters")
    void creatingHintWith3char() {
        Hint hint = Hint.createValidHint("AGO");
        int length = hint.getLength();
        assertSame(3, length);
        assertEquals("AGO", hint.getValue());
    };

    @Test
    @DisplayName("Creating a Hint #5 - Test #13 0 characters")
    void creatingGuessWith0char() {
        assertThrows(NoSuchElementException.class, () -> {
            Hint hint = Hint.createValidHint("");
        });
    }
}