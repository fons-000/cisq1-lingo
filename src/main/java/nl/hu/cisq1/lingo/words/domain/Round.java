package nl.hu.cisq1.lingo.words.domain;

import java.util.ArrayList;

public class Round {
    private int roundOfGame;
    private Word word;
    private Word firstHint;
    private ArrayList<Turn> turns = new ArrayList<Turn>();

    public Round(Word word, int roundOfGame) {
        this.word = word;
        this.roundOfGame = roundOfGame;
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

    public boolean setFirstHint(Word firstHint) {
        if(new Word(String.valueOf(word.getValue().charAt(0))).equals(firstHint)) {
            this.firstHint = firstHint;
            return true;
        }
        return false;
    }

    public Word getFirstHint() {
        return firstHint;
    }

    public ArrayList<Turn> getTurns() {
        return turns;
    }

    public int getRoundOfGame() {
        return roundOfGame;
    }

    public boolean addTurn(Turn turn) {
        if(this.word.equals(turn.getWord()) && this.turns.size() < 5 && !this.word.equals(turn.getGuess())) {
            return this.turns.add(turn);
        }
        return false;
    }
}