package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GuessTest {
    //1. We want to indirectly test if the getters work properly in the use cases of creating

    //Use cases of creating:
    //2. Creating a guess works with the following restrictions:
    //  the Guess can only contain letters that are in the Letter enum
    //Meaning the following use cases:
    //  2.1 Guesses that are fully capital will return a Guess succesfully
    //  2.2 Guesses can contain one or more "."
    //  2.3 Guesses with one or more small letters will throw an error.
    //  2.4 Guesses with weird symbols will throw an error.
    //  2.5 Guesses can contain an "umlimted" amount of characters
    //  2.6 Guesses with 0 characters will throw an error

    //3. Test if the equal function works with correct & false values.

    @Test
    @DisplayName("Creating a guess #1 - Creating a fully capital guess works")
    void creatingGuessSuccesfully() {
        Guess guess = Guess.createValidGuess("WOORD");
        int length = guess.getLength();
        assertSame(5, length);
        assertEquals("WOORD", guess.getValue());
    }

    @Test
    @DisplayName("Creating a guess #2 - Creating a capital guess with one or multiple '.' works")
    void creatingGuessSuccesfully2() {
        Guess guess = Guess.createValidGuess("WOORD.");
        int length = guess.getLength();
        assertSame(6, length);
        assertEquals("WOORD.", guess.getValue());
    }

    @Test
    @DisplayName("Creating a guess #3 - Guess with one or more small letters will throw an error")
    void creatingGuessThrowsError1() {
        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createValidGuess("WOoRD");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createValidGuess("wOoRd");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createValidGuess("woord");
        });
    }

    @Test
    @DisplayName("Creating a guess #4 - Guesses with weird symbols will throw an error")
    void creatingGuessThrowsError2() {
        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createValidGuess("WOORD#");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createValidGuess("wo0458p;");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createValidGuess("woord/");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createValidGuess("}{:");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createValidGuess("(WOORD*");
        });
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #1 5 characters - guesses can only have a length of 5, 6 or 7 characters.")
    void creatingGuessWith5char() {
        Guess guess = Guess.createValidGuess("FUZZY");
        int length = guess.getLength();
        assertSame(5, length);
        assertEquals("FUZZY", guess.getValue());
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #2 6 characters - guesses can only have a length of 5, 6 or 7 characters.")
    void creatingGuessWith6char() {
        Guess guess = Guess.createValidGuess("BEZAZZ");
        int length = guess.getLength();
        assertSame(6, length);
        assertEquals("BEZAZZ", guess.getValue());
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #3 7 characters - guesses can only have a length of 5, 6 or 7 characters.")
    void creatingGuessWith7char() {
        Guess guess = Guess.createValidGuess("WOORD.S");
        int length = guess.getLength();
        assertSame(7, length);
        assertEquals("WOORD.S", guess.getValue());
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #4 4 characters")
    void creatingGuessWith4char() {
        Guess guess = Guess.createValidGuess("JAZZ");
        int length = guess.getLength();
        assertSame(4, length);
        assertEquals("JAZZ", guess.getValue());
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #5 8 characters")
    void creatingGuessWith8char() {
        Guess guess = Guess.createValidGuess("ACCURACY");
        int length = guess.getLength();
        assertSame(8, length);
        assertEquals("ACCURACY", guess.getValue());
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #6 15 characters")
    void creatingGuessWith15char() {
        Guess guess = Guess.createValidGuess("ACCOMPLISHMENTS");
        int length = guess.getLength();
        assertSame(15, length);
        assertEquals("ACCOMPLISHMENTS", guess.getValue());
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #7 22 characters")
    void creatingWGuessith22char() {
        Guess guess = Guess.createValidGuess("COUNTERREVOLUTIONARIES");
        int length = guess.getLength();
        assertSame(22, length);
        assertEquals("COUNTERREVOLUTIONARIES", guess.getValue());
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #10 1 characters")
    void creatingGuessWith1char() {
        Guess guess = Guess.createValidGuess("A");
        int length = guess.getLength();
        assertSame(1, length);
        assertEquals("A", guess.getValue());
    }

    @Test
    @DisplayName("Creating a guess #5 - Test #11 2 characters")
    void creatingGuessWith2char() {
        Guess guess = Guess.createValidGuess("AB");
        int length = guess.getLength();
        assertSame(2, length);
        assertEquals("AB", guess.getValue());
    };

    @Test
    @DisplayName("Creating a guess #5 - Test #12 3 characters")
    void creatingGuessWith3char() {
        Guess guess = Guess.createValidGuess("AGO");
        int length = guess.getLength();
        assertSame(3, length);
        assertEquals("AGO", guess.getValue());
    };

    @Test
    @DisplayName("Creating a guess #5 - Test #13 0 characters")
    void creatingGuessWith0char() {
        assertThrows(NoSuchElementException.class, () -> {
            Guess guess = Guess.createValidGuess("");
        });
    }

//    @ParameterizedTest
//    @MethodSource("wordExamples")
//    @DisplayName("equalFunctionValueIsDifferentTest")
//    void valueLengthPropertyDifferent(Word word) {
//        Guess guess = new Guess("WOWSIE");
//        assertNotEquals(guess, word);
//    }
//
//    static Stream<Arguments> wordExamples() {
//        return Stream.of(
//                Arguments.of(new Word("BOTER")),
//                Arguments.of(new Word("ACTING")),
//                Arguments.of(new Word("ADVANCE"))
//        );
//    }
}