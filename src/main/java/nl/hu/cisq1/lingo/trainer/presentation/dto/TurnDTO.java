package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.*;

public class TurnDTO {
    private Feedback feedback;
    private Word hint;
    private Word guess;

    public TurnDTO(Turn turn) {
        this.feedback = turn.getFeedback();
        this.hint = turn.getHint();
        this.guess = turn.getGuess();
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public Word getHint() {
        return hint;
    }

    public Word getGuess() {
        return guess;
    }
}