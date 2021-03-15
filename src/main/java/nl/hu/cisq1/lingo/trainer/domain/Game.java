package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "game")
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue
    @Column(name = "game_id")
    private int id;

    @JoinColumn(name = "person_id")
    @ManyToOne
    private Person person;

    @Column(name = "score")
    private int score;

    @OneToMany(mappedBy = "game")
    private Set<Round> rounds = new LinkedHashSet<>();

    public Game() {
        this.score = 100;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean addRound(Round round) {
        return this.rounds.add(round);
    }

    public Set<Round> getRounds() {
        return rounds;
    }

    public void setRounds(Set<Round> rounds) {
        this.rounds = rounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return score == game.score
                & rounds.equals(game.rounds);
    }
}