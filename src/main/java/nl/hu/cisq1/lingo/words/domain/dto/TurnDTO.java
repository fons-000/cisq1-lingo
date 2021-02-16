package nl.hu.cisq1.lingo.words.domain.dto;

import nl.hu.cisq1.lingo.words.domain.Feedback;
import nl.hu.cisq1.lingo.words.domain.Turn;
import nl.hu.cisq1.lingo.words.domain.Word;

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