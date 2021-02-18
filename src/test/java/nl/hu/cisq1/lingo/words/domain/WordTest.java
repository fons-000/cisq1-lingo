package nl.hu.cisq1.lingo.words.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

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

    //4. generateAFiveLetterWord() should return a five letter word
    //5. generateASixLetterWord() should return a six letter word
    //6. generateASevenLetterWord() should return a seven letter word

//    @Test
//    @DisplayName("Creating a word #1 - Creating a fully capital word works")


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
    @DisplayName("Creating a word #1 - Creating a capital word with one or multiple '.' works")
    void lengthBasedOnWord() {
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

}
