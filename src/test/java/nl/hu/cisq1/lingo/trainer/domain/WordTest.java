package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    //1. We want to indirectly test if the getters work properly in the use cases of creating
    //2. Also: for every use case of creating, the Letter array should spell the exact word
    //   with the exception of "." for DOT

    //Use cases of creating:
    //3. Creating a word works with the following restrictions:
    //  the word can onlyl letters that are in the Letter enum
    //Meaning the following use cases:
    //  3.1 Words that are fully capital will return a Word succesfully
    //  3.2 Words can contain one or more "."
    //  3.3 Words with one or more small letters will throw an error.
    //  3.4 Words with weird symbols will throw an error.
    //  3.5 Words can only contain the following amount of characters
        // 3.5.1 - 5
        // 3.5.1 - 6
        // 3.5.1 - 7

    @Test
    @DisplayName("Creating a word #1 - Creating a fully capital word works")
    void creatingWordSuccesfully() {
        Word word = Word.createValidWord("WOORD");
        int length = word.getLength();
        ArrayList<Letter> letters = word.getLetters();
        assertSame(5, length);
        assertEquals("WOORD", word.getValue());
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
    @DisplayName("Creating a word #2 - Creating a capital word with one or multiple '.' works")
    void creatingWordSuccesfully2() {
        Word word = Word.createValidWord("WOORD");
        int length = word.getLength();
        ArrayList<Letter> letters = word.getLetters();
        assertSame(5, length);
        assertEquals("WOORD", word.getValue());
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
        ArrayList<Letter> letters = word.getLetters();
        assertSame(5, length);
        assertEquals("WOORD", word.getValue());
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
    @DisplayName("Creating a word #5 - Test #2 6 characters - words can only have a length of 5, 6 or 7 characters.")
    void creatingWordWith6char() {
        Word word = Word.createValidWord("WOORD.");
        int length = word.getLength();
        ArrayList<Letter> letters = word.getLetters();
        assertSame(6, length);
        assertEquals("WOORD.", word.getValue());
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
    @DisplayName("Creating a word #5 - Test #3 7 characters - words can only have a length of 5, 6 or 7 characters.")
    void creatingWordWith7char() {
        Word word = Word.createValidWord("WOORD.S");
        int length = word.getLength();
        ArrayList<Letter> letters = word.getLetters();
        assertSame(7, length);
        assertEquals("WOORD.S", word.getValue());
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