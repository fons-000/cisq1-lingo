package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Optional;

public class Word {
    private String value;
    private Integer length;
    private ArrayList<Letter> letters = new ArrayList<Letter>();

    public Word() {}
    public Word(String word, ArrayList<Letter> letters) {
        this.value = word;
        this.length = word.length();
        this.letters = letters;
    }

    public static Optional<Word> createWord(String word) {
        ArrayList<Letter> letters = new ArrayList<Letter>();
        if(word.length() == 5 || word.length() == 6 || word.length() == 7) {
            for(char letterChar : word.toCharArray()) {
                Optional<Letter> letter1 = Letter.charToLetter(letterChar);
                //Als het word geen geldige letter/letters heeft => dan loopt het programma vast.
                Letter letter = letter1.orElseThrow();
                letters.add(letter);
            }
            Word word1 = new Word(word, letters);
            return Optional.of(word1);
        }
        return Optional.empty();
    }

    public static Word createValidWord(String word) {
        //Creates a valid word or throws an error!
        Optional<Word> optionalWord = createWord(word);
        Word word1 = optionalWord.orElseThrow();
        return word1;
    }

    public String getValue() {
        return value;
    }

    public Integer getLength() {
        return length;
    }

    public ArrayList<Letter> getLetters() {
        return letters;
    }

    //Test de servicefunctie als de service goed opgezet is!
//    public Word generateWordForRound(int roundOfGame) {
//        Word word;
//        if(roundOfGame % 3 == 0) {
//            word = generateASevenLetterWord();
//        }
//        else if(roundOfGame % 2 == 0) {
//            word = generateASixLetterWord();
//        }
//
//        else {
//            word = generateAFiveLetterWord();
//        }
//        return word;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(getClass() == Word.class || getClass() == Hint.class || getClass() == Guess.class)) return false;
        Word word = (Word) o;
        return value.equals(word.value) && length.equals(word.length);
    }
}