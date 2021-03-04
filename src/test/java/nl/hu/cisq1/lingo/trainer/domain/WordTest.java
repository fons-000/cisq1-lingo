package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    //1. We want to indirectly test if the getters work properly in the use cases of creating

    //Use cases of creating:
    //2. Creating a word works with the following restrictions:
    //  the word can onlyl letters that are in the Letter enum
    //Meaning the following use cases:
    //  2.1 Words that are fully capital will return a Word succesfully
    //  2.2 Words can contain one or more "."
    //  2.3 Words with one or more small letters will throw an error.
    //  2.4 Words with weird symbols will throw an error.
    //  2.5 Words can only contain the following amount of characters
        // 2.5.1 - 5
        // 2.5.1 - 6
        // 2.5.1 - 7

    //3 Equals function test
    //3.1. Same object
    //3.2. Object with same attributes.
    //3.3 Object is null
    //3.4 Object is wat anders dan Word, Hint of Guess
    //3.5 Test de onderstaande tabel
    //Equaltesting attributes
    //1. Object value = word value (same as #2, so skip)
    //2. Object value != word value
    //(In total: 2 equal tests)

    //4. Test word.equals(guess) for fully branched equals.

    Word word= new Word("DRAGON");

    @Test
    @DisplayName("Equal function test #1: Equal object")
    void equalObject() {
        assertEquals(word, word);
    }

    @Test
    @DisplayName("Equal function test #2: object with same attributes")
    void equalAttributesObject() {
        Word word1 = new Word("DRAGON");
        assertEquals(word, word1);
    }

    @Test
    @DisplayName("Equal function test #3: Null")
    void nullObject() {
        assertNotEquals(word, null);
    }

    @Test
    @DisplayName("Equal function test #4: Object with different class then Word, Hint or Guess")
    void differentClass() {
        Account account = new Account("yolo01", "1234");
        assertNotEquals(word, account);
    }

    @ParameterizedTest
    @MethodSource("wordExamples")
    @DisplayName("equalFunctionValueIsDifferentTest")
    void valueLengthPropertyDifferent(Word word) {
        assertNotEquals(this.word, word);
    }

    static Stream<Arguments> wordExamples() {
        return Stream.of(
                Arguments.of(new Word("BOTER")),
                Arguments.of(new Word("ACTING")),
                Arguments.of(new Word("ADVANCE"))
        );
    }

    @Test
    @DisplayName("Equal guess and word")
    void equalHint() {
        Guess guess = new Guess("BOOM");
        Word word = new Word("BOOM");
        assertEquals(word, guess);
    }

    @Test
    @DisplayName("Creating a word #1 - Creating a fully capital word works")
    void creatingWordSuccesfully() {
        Word word = Word.createValidWord("WOORD");
        int length = word.getLength();
        assertSame(5, length);
        assertEquals("WOORD", word.getValue());
    }

    @Test
    @DisplayName("Creating a word #2 - Creating a capital word with one or multiple '.' works")
    void creatingWordSuccesfully2() {
        Word word = Word.createValidWord("WOORD");
        int length = word.getLength();
        assertSame(5, length);
        assertEquals("WOORD", word.getValue());
    }

    @Test
    @DisplayName("Creating a word #3 - Words with one or more small letters will throw an error")
    void creatingWordThrowsError1() {
        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("WOoRD");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("wOoRd");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("woord");
        });
    }

    @Test
    @DisplayName("Creating a word #4 - Words with weird symbols will throw an error")
    void creatingWordThrowsError2() {
        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("WOORD#");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("wo0458p;");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("woord/");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("}{:");
        });

        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("(WOORD*");
        });
    }

    @Test
    @DisplayName("Creating a word #5 - Test #1 5 characters - words can only have a length of 5, 6 or 7 characters.")
    void creatingWordWith5char() {
        Word word = Word.createValidWord("WOORD");
        int length = word.getLength();
        assertSame(5, length);
        assertEquals("WOORD", word.getValue());
    }

    @Test
    @DisplayName("Creating a word #5 - Test #2 6 characters - words can only have a length of 5, 6 or 7 characters.")
    void creatingWordWith6char() {
        Word word = Word.createValidWord("WOORD.");
        int length = word.getLength();
        assertSame(6, length);
        assertEquals("WOORD.", word.getValue());
    }

    @Test
    @DisplayName("Creating a word #5 - Test #3 7 characters - words can only have a length of 5, 6 or 7 characters.")
    void creatingWordWith7char() {
        Word word = Word.createValidWord("WOORD.S");
        int length = word.getLength();
        assertSame(7, length);
        assertEquals("WOORD.S", word.getValue());
    }

    @Test
    @DisplayName("Creating a word #5 - Test #4 4 characters - words can only have a length of 5, 6 or 7 characters.")
    void creatingWordWith4char() {
        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("TRAP");
        });
    }

    @Test
    @DisplayName("Creating a word #5 - Test #5 8 characters - words can only have a length of 5, 6 or 7 characters.")
    void creatingWordWith8char() {
        assertThrows(NoSuchElementException.class, () -> {
            Word word = Word.createValidWord("TRAPTRAP");
        });
    }
}