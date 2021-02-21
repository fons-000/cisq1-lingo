package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Optional;

public class Round {
    private int roundOfGame;
    private Word word;
    private Word firstHint;
    private ArrayList<Turn> turns = new ArrayList<Turn>();

    public Round(Word word, int roundOfGame) {
        this.word = word;
        this.roundOfGame = roundOfGame;
        Word firstHint = Hint.createValidHint(String.valueOf(word.getValue().charAt(0)));
        this.firstHint = firstHint;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
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
        //1. Als het woord nog niet gegokt is (kijkt standaard naar de laatste turn in de lijst),
        //dit werkt omdat er geen setTurns is en alles vanaf begin via de add moet!
        //2. Als de lengte van de huidige turns list niet al >= 5 is!
        if(this.getTurns().size() >= 1) {
            if(!this.getTurns().get(this.getTurns().size() - 1).getGuess().getValue().equals(this.word.getValue())
                    && !(this.getTurns().size() >= 5)
                    && this.word.equals(turn.getWord())) {
                return this.turns.add(turn);
            }
        }
        else {
            if(this.word.equals(turn.getWord())) {
                return this.turns.add(turn);
            }
        }
        return false;
    }
}