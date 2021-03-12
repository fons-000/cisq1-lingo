package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "words")
public class Word {

    @Id
    @Column(name = "word")
    private String value;

    @Column(name = "length")
    private Integer length;

    @OneToOne
    private Round round;

    @OneToOne(mappedBy = "hint")
    private Turn turn;

    public Word() {
    }

    public Word(String word) {
        this.value = word;
        this.length = word.length();
    }

    public static Optional<Word> createWord(String word) {
        if((word.length() == 5 || word.length() == 6 || word.length() == 7) && word.matches("^[A-Z\\.]{5,7}$")) {
            Word word1 = new Word(word);
            return Optional.of(word1);
        }
        return Optional.empty();
    }

    public static Word createValidWord(String word) {
        //Creates a valid word or throws an error!
        Optional<Word> optionalWord = createWord(word);
        Word word1 = optionalWord.orElseThrow();
        return word1;
    }

    public String getValue() {
        return value;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (o.getClass() != Word.class && o.getClass() != Hint.class && o.getClass() != Guess.class)) {
            return false;
        }
            Word word = (Word) o;
            return value.equals(word.value);
    }
}