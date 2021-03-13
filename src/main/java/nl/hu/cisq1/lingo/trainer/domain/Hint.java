package nl.hu.cisq1.lingo.trainer.domain;

import java.util.Optional;

public class Hint extends Word {

    public Hint(String word) {
        super(word);
    }

    public static Optional<Hint> createHint(String word) {
        if(word.matches("^[A-Z\\.]+$")) {
            Hint hint = new Hint(word);
            return Optional.of(hint);
        }
        return Optional.empty();
    }

    public static Hint createValidHint(String word) {
        //Creates a hint or throws an error!
        Optional<Hint> optionalHint = createHint(word);
        Hint hint = optionalHint.orElseThrow();
        return hint;
    }
}