package nl.hu.cisq1.lingo.trainer.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "round")
public class Round implements Serializable {
    @Id
    @JoinColumn(name = "game_id")
    @ManyToOne
    private Game game;

    @Id
    @Column(name = "round_game")
    private int roundOfGame;

    @JoinColumn(name = "word")
    @OneToOne
    private Word word;

    @JoinColumn(name = "first_hint")
    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Word firstHint;

    @OneToMany(mappedBy = "round")
    private Set<Turn> turns = new LinkedHashSet<>();

    public Round() {
    }

    public Round(Word word, int roundOfGame) {
        this.word = word;
        this.roundOfGame = roundOfGame;
        Word firstHint = Hint.createValidHint(String.valueOf(word.getValue().charAt(0)));
        this.firstHint = firstHint;
    }

    public Word getWord() {
        return word;
    }

    public Word getFirstHint() {
        return firstHint;
    }

    public Set<Turn> getTurns() {
        return turns;
    }

    public int getRoundOfGame() {
        return roundOfGame;
    }

    public boolean addTurn(Turn turn) {
        //1. Als het woord nog niet gegokt is (kijkt standaard naar de laatste turn in de lijst),
        //dit werkt omdat er geen setTurns is en alles vanaf begin via de add moet!
        //2. Als de lengte van de huidige turns list niet al >= 5 is!
        ArrayList<Turn> turns = new ArrayList<>(this.turns);
        if(this.getTurns().size() >= 1) {
            if((!turns.get(this.getTurns().size() - 1).getGuess().getValue().equals(this.word.getValue()))
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