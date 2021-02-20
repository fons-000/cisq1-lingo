package nl.hu.cisq1.lingo.words.domain;

import java.util.ArrayList;
import java.util.Optional;

public class Hint extends Word {
    public Hint(String word, ArrayList<Letter> letters) {
        super(word, letters);
    }

    public static Hint createHint(String word) {
        ArrayList<Letter> letters = new ArrayList<Letter>();
        for(char letterChar : word.toCharArray()) {
            Optional<Letter> letter1 = Letter.charToLetter(letterChar);
            //Als het word geen geldige letter/letters heeft => dan loopt het programma vast.
            Letter letter = letter1.orElseThrow();
            letters.add(letter);
        }
        //The word is already valid, if all the letters are valid.
        //Just return a regular word.
        //If all the letters are correct, and the for loop is being looped,
        //the word can not be null in any case.
        Hint hint = new Hint(word, letters);
        return hint;
    }
}