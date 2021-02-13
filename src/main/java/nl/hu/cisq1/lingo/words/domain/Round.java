package nl.hu.cisq1.lingo.words.domain;

import java.util.ArrayList;

public class Round {
    private int roundOfGame;
    private Word word;
    private Word firstHint;
    private ArrayList<Turn> turns = new ArrayList<Turn>();

    public Round(Word word, int roundOfGame) {
        this.roundOfGame = roundOfGame;
        this.word = word;
    }

    public Word returnFirstHint() {
        char firstLetter = this.word.getValue().charAt(0);
        String firstHintValue = String.valueOf(firstLetter);
        Word firstHint = new Word(firstHintValue);
        return firstHint;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public void setFirstHint(Word firstHint) {
        this.firstHint = firstHint;
    }

    public Word getFirstHint() {
        return firstHint;
    }

    public boolean addTurn(Turn turn) {
        return this.turns.add(turn);
    }
}