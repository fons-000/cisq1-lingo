package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.*;

public class TurnDTO {
    private Feedback feedback;
    private Word hint;
    private Word guess;

    public TurnDTO(Turn turn) {
        System.out.println("Gaat nu naar returnFeedbackCurrentTurn van turnDTO!");
        this.feedback = turn.returnFeedbackCurrentTurn();
        this.hint = turn.getHint();
        this.guess = turn.getGuess();
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
                "feedback=" + feedback +
                ", hint=" + hint +
                ", guess=" + guess +
                '}';
    }
}