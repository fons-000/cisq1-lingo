package nl.hu.cisq1.lingo.words.domain;

import nl.hu.cisq1.lingo.words.domain.WordEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordEntityTest {

    @Test
    @DisplayName("length is based on given word")
    void lengthBasedOnWord() {
        WordEntity word = new WordEntity("woord");
        int length = word.getLength();
        assertEquals(5, length);
    }
}