package nl.hu.cisq1.lingo.words.domain;

import java.util.ArrayList;

public class Game {
    private int score;
    private ArrayList<Round> rounds = new ArrayList<Round>();

    public Game(int score) {
        this.score = score;
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

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return score == game.score && rounds.equals(game.rounds);
    }
}