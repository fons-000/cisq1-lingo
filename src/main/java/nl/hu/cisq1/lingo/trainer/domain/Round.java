package nl.hu.cisq1.lingo.trainer.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "round")
public class Round implements Serializable, Comparable<Round> {
    @Id
    @GeneratedValue(generator = "round_round_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "round_round_id_seq", sequenceName = "round_round_id_seq")
    @Column(name = "round_id")
    private int id;

    @JoinColumn(name = "game_id")
    @ManyToOne
    private Game game;

    @Column(name = "round_game")
    private int roundOfGame;

    @Column(name = "word")
    private String wordValue;

    @Transient
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
        this.wordValue = word.getValue();
        this.roundOfGame = roundOfGame;
        Word firstHint = Hint.createValidHint(String.valueOf(word.getValue().charAt(0)));
        this.firstHint = firstHint;
    }

    public Word getWord() {
        return new Word(this.wordValue);
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

    public Game getGame() {
        return game;
    }

    public void setWordValue(String wordValue) {
        this.word = new Word(wordValue);
        this.wordValue = wordValue;
    }

    public void setTurns(List<Turn> turns) {
        this.turns = new LinkedHashSet<>(turns);
    }

    public String getWordValue() {
        return  this.wordValue;
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
                ", roundOfGame=" + roundOfGame +
                ", wordValue='" + wordValue + '\'' +
                ", turns=" + turns +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("Comes in the equals of Round");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        ArrayList<Turn> dbTurns = new ArrayList<>(turns);
        ArrayList<Turn> turns = new ArrayList<>(round.getTurns());
        System.out.println(roundOfGame == round.roundOfGame);
        System.out.println(Objects.equals(wordValue, round.wordValue));
        System.out.println(dbTurns.equals(turns));
        return roundOfGame == round.roundOfGame && Objects.equals(wordValue, round.wordValue)
            && dbTurns.equals(turns);
        //id == round.id &&
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundOfGame, wordValue, turns);
    }

    @Override
    public int compareTo(Round round) {
        return this.roundOfGame - round.getRoundOfGame();
    }
}