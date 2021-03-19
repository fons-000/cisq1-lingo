package nl.hu.cisq1.lingo.trainer.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "round")
public class Round implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "round_id")
    private int id;

    @JoinColumn(name = "game_id")
    @ManyToOne
    private Game game;

    @Column(name = "round_game")
    private int roundOfGame;

    @JoinColumn(name = "word")
    @OneToOne(cascade = CascadeType.ALL)
    private Word word;

    public void setGame(Game game) {
        this.game = game;
    }

    //    @JoinColumn(name = "first_hint")
//    @OneToOne
//    @NotFound(action = NotFoundAction.IGNORE)
    @Transient
    private Word firstHint;

    //Deze heb ik er nieuw aangeplakt.
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL)
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

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "Round{" +
                "game=" + game +
                ", roundOfGame=" + roundOfGame +
                ", word=" + word +
                ", firstHint=" + firstHint +
                ", turns=" + turns +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return id == round.id && roundOfGame == round.roundOfGame && Objects.equals(game, round.game) && Objects.equals(word, round.word) && Objects.equals(firstHint, round.firstHint) && Objects.equals(turns, round.turns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, game, roundOfGame, word, firstHint, turns);
    }
}