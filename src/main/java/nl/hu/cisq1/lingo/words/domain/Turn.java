package nl.hu.cisq1.lingo.words.domain;

import java.util.Optional;

public class Turn {
    private Feedback feedback;
    private Word hint;
    private Word guess;

    //word moet later verwijderd worden als er een service laag is!
    private Word word;

    public Turn(Word hint, Word word) {
        this.hint = hint;
        this.word = word;
    }

    public void setGuess(Word guess) {
        this.guess = guess;
    }

    public boolean generateFeedback() {
        Optional<Feedback> optionalFeedback = Feedback.generateFeedback(word, guess);
        //Throwed als de feedback present is
        Feedback feedback = optionalFeedback.orElseThrow();
        this.feedback = feedback;
        return true;
    }

//    public boolean generateHint() {
//        //Genereert een hint
//    }
}