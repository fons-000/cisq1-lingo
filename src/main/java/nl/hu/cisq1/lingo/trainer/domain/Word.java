package nl.hu.cisq1.lingo.trainer.domain;

import java.util.Optional;

public class Word {
    private String value;
    private Integer length;

    public Word(String word) {
        this.value = word;
        this.length = word.length();
    }

    public static Optional<Word> createWord(String word) {
        if((word.length() == 5 || word.length() == 6 || word.length() == 7) && word.matches("^[A-Z\\.]{5,7}$")) {
            Word word1 = new Word(word);
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