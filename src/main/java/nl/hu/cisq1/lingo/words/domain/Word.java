package nl.hu.cisq1.lingo.words.domain;

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
        System.out.println("This is the incoming string: ");
        System.out.println(word);
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

    public static Word createGuess(String word) {
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
            Word word1 = new Word(word, letters);
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

    public Word generateWordForRound(int roundOfGame) {
        Word word;
        if(roundOfGame % 3 == 0) {
            word = generateASevenLetterWord();
        }
        else if(roundOfGame % 2 == 0) {
            word = generateASixLetterWord();
        }

        else {
            word = generateAFiveLetterWord();
        }
        return word;
    }

    public Word generateAFiveLetterWord() {
        //Deze functie moet later een koppeling hebben met de database!
        Word validWord = Word.createValidWord("PIZZA");
        return validWord;
    }

    public Word generateASixLetterWord() {
        //Deze functie moet later een koppeling hebben met de database!
        Word validWord = Word.createValidWord("PUZZLE");
        return validWord;
    }

    public Word generateASevenLetterWord() {
        //Deze functie moet later een koppeling hebben met de database!
        Word validWord = Word.createValidWord("JACUZZI");
        return validWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return value.equals(word.value) && length.equals(word.length);
    }
}