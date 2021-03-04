package nl.hu.cisq1.lingo.words.domain;

import nl.hu.cisq1.lingo.words.domain.WordEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordEntityTest {
    //It's useless to test/implement a static constructor for WordEntity
    //Since Word already has one. We will make a seperate function for the conversion
    //in later phases of the project.

    //It's also useless to use the setValue, since we will fetch these Words from the database.
    //The equals methode is not needed, because after the conversion to Word, we can use the Word.equals.

    //For now, the basic tests of WordEntity and it's service and controller will do.

    @Test
    @DisplayName("length is based on given word")
    void lengthBasedOnWord() {
        WordEntity word = new WordEntity("woord");
        int length = word.getLength();
        assertEquals(5, length);
    }
}