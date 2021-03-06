package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.*;

public class TurnDTO {
    private int turnOfRound;
    private Feedback feedback;
    private Word hint;
    private Word guess;

    public TurnDTO(Turn turn) {
        this.turnOfRound = turn.getTurnRound();
        this.feedback = turn.returnFeedbackCurrentTurn();
        this.hint = turn.getHint();
        this.guess = turn.getGuess();
    }

    public int getTurnOfRound() {
        return turnOfRound;
    }

    public Feedback getFeedback() {
        return this.feedback;
    }

    public Word getHint() {
        return hint;
    }

    public Word getGuess() {
        return guess;
    }

    @Override
    public String toString() {
        return "TurnDTO{" +
                "turnOfRound=" + turnOfRound +
                ", feedback=" + feedback +
                ", hint=" + hint +
                ", guess=" + guess +
                '}';
    }
}