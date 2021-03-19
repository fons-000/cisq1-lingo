package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "turn")
//       uniqueConstraints = {@UniqueConstraint(columnNames = {"round_game", "game_id"})}
public class Turn {
    @Id
    @GeneratedValue
    @Column(name = "turn_id")
    private int id;

    @Column(name = "turn_round")
    private int turnRound;

    @Transient
    private Feedback feedback;

//    turn.round_game = round.round_game & turn.game_id = round.game_id
    //Hier dus geen CasCadetype.all, is al gezegd door de andere.
    @JoinColumn(name = "round_id")
    @ManyToOne
    private Round round;

    //Deze zeurt ie nu over.
    @JoinColumn(name = "turn_hint")
    @OneToOne(cascade = CascadeType.ALL)
    private Word hint;

    @Column(name = "turn_guess")
    @OneToOne(cascade = CascadeType.ALL)
    private Word guess;

    @Transient
    private Word word;

    public Turn() {
    }

    public Turn(Word hint, Word guess, Word word) {
        this.hint = hint;
        this.guess = guess;
        this.word = word;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
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

    public Word getWord() {
        return word;
    }

    public int getId() {
        return id;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public int getTurnRound() {
        return turnRound;
    }

    //Ga dit later verwijderen
    public Round getRound() {
        return round;
    }

    //Deze methode moet eerst uitgevoerd worden, en daarna moet de uitkomst geset worden in de turn,
    //voordat returnHintForNextTurn aangeroepen kan worden.
    public Feedback returnFeedbackCurrentTurn() {
        Optional<Feedback> optionalFeedback = Feedback.generateFeedback(this.word, this.guess);
        //Throwed als de feedback empty is
        Feedback feedback = optionalFeedback.orElseThrow();
        return feedback;
    }

    public Hint returnHintForNextTurn() {
        Optional<Hint> optionalNextHint = this.generateHintForNextTurn();
        //Throwed als de feedback empty is en retourneerd niks (null)
        Hint nextHint = optionalNextHint.orElseThrow();
        return nextHint;
    }

    public Optional<Hint> generateHintForNextTurn() {
        String previousHintString = this.hint.getValue();
        ArrayList<FeedbackItem> feedbackItems = this.feedback.getFeedbackItems();
        String newHintValue = "";
        char[] newHintValueCharArray = new char[this.word.getLength()];

        //Stap 0 => Voeg evenveel puntjes toe aan de size van Word
        for(int i = 0; i < this.word.getLength(); i++) {
            newHintValueCharArray[i] = '.';
        }

        //Stap 1 => Voeg alle correct gegokte letters toe van de huidige turn
        for(int i = 0; i < feedbackItems.size(); i++) {
            if(feedbackItems.get(i) == FeedbackItem.CORRECT) {
                char correctLetter = this.word.getValue().charAt(i);
                newHintValueCharArray[i] = correctLetter;
            }
        }

        //Stap 2 => Voeg nu alle al eerder gegokte letters toe (of overschrijf deze)
        for(int i = 0; i < previousHintString.length(); i++) {
            if(previousHintString.charAt(i) != '.') {
                char letter = this.word.getValue().charAt(i);
                newHintValueCharArray[i] = letter;
            }
        }

        newHintValue = String.valueOf(newHintValueCharArray);
        //Deze moet geretourneerd worden, maar eerst verpakt in een Optional!
        Hint newHint = Hint.createValidHint(newHintValue);
        Optional<Hint> validNewHintOptional = Optional.of(newHint);

        //Stap 4 => Verpak de newHint in een Optional als de hint niet hetzelfde is
        //als het word of return een lege Optional
        if(word.equals(newHint)) {
            return Optional.empty();
        }
        return validNewHintOptional;
    }

    @Override
    public String toString() {
        return "Turn{" +
                "id=" + id +
                ", turnRound=" + turnRound +
                ", feedback=" + feedback +
                ", hint=" + hint +
                ", guess=" + guess +
                ", word=" + word +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return id == turn.id && turnRound == turn.turnRound && Objects.equals(feedback, turn.feedback) && Objects.equals(round, turn.round) && Objects.equals(hint, turn.hint) && Objects.equals(guess, turn.guess) && Objects.equals(word, turn.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, turnRound, feedback, round, hint, guess, word);
    }
}