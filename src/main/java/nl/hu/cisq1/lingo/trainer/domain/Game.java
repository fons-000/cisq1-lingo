package nl.hu.cisq1.lingo.trainer.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity(name = "game")
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(generator = "game_game_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "game_game_id_seq", sequenceName = "game_game_id_seq")
    @Column(name = "game_id")
    private int id;

    @JoinColumn(name = "person_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Person person;

    @Column(name = "score")
    private int score;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public void setRounds(List<Round> rounds) {
        this.rounds = new LinkedHashSet<>(rounds);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        ArrayList<Round> dbGameRounds = new ArrayList<>(game.getRounds());
        ArrayList<Round> gameRounds = new ArrayList<>(rounds);
        if(person != null) {
            return score == game.score && dbGameRounds.equals(gameRounds) && person.equals(game.getPerson());
        }
        return score == game.score && dbGameRounds.equals(gameRounds);
    }

    public String showGame() {
        String out = "Game:\n" +
                " id     = " + id + "\n" +
                " score  = " + score + "\n";
        if(person != null) {
            out+=   " person:\n";
            out+=   "  id    =" + person.getId() +"\n";
            out+=   "  name  =" + person.getName() +"\n";
            out+=   "  account:\n";
            out+=   "   id  =" + person.getAccount().getId() +"\n";
            out+=   "   username  =" + person.getAccount().getName() +"\n";
            out+=   "   password  =" + person.getAccount().getPassword() +"\n";
            out+=   "  role  =" + person.getRole() +"\n";
            out+=   "  games:\n";
            for(Game game : person.getGames()) {
                out+= " id     = " + game.getId() + "\n";
                out+= " score  = " + game.getId() + "\n";
                out+= " person:\n";
                out+= "  id    =" + person.getId() +"\n";
                out+= "  name  =" + person.getName() +"\n";
                out+= "  account:\n";
                out+= "   id  =" + person.getAccount().getId() +"\n";
                out+= "   username  =" + person.getAccount().getName() +"\n";
                out+= "   password  =" + person.getAccount().getPassword() +"\n";
                out+= "  role  =" + person.getRole() +"\n";
            }
        }
            out+= " rounds:\n";
                for (Round roundx : rounds) {
                    out += " round " + roundx.getId() + "\n";
                    out += "   roundofgame: " + roundx.getRoundOfGame() + "\n";
                    out += "   word:        " + roundx.getWordValue() + "\n";
                    out += "   turns:\n";
                    for (Turn turnx : roundx.getTurns()) {
                        out += " turn " + turnx.getId() + "\n";
                        out += "   turnround:   " + turnx.getTurnRound() + "\n";
                        out += "   hintstring:  " + turnx.getHintString() + "\n";
                        out += "   guessstring: " + turnx.getGuessString() + "\n";
                    }
                }
                return out;
    }

    @Override
    public String toString() {
        return "Game{" +
                ", score=" + score +
                ", rounds=" + rounds +
                '}';
    }
}