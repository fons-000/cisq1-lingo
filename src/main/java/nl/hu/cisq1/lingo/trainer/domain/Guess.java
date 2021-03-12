package nl.hu.cisq1.lingo.trainer.domain;

import java.util.Optional;

public class Guess extends Word {

    public Guess() {
    }

    public Guess(String word) {
        super(word);
    }

    public static Optional<Guess> createGuess(String word) {
        if(word.matches("^[A-Z\\.]+$")) {
            Guess guess = new Guess(word);
            return Optional.of(guess);
        }
        return Optional.empty();
    }

    public static Guess createValidGuess(String word) {
        //Creates a hint or throws an error!
        Optional<Guess> optionalGuess = createGuess(word);
        Guess guess = optionalGuess.orElseThrow();
        return guess;
    }
}