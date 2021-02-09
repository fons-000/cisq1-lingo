package nl.hu.cisq1.lingo.words.domain;

public class Round {
    private static int totalRounds = 0;
    private int id;
    private Word word;
    private Word guess;
    private Word hint;

    public Round(Word word) {
        totalRounds++;
        this.id = totalRounds;
        this.word = word;
        this.hint = generateHint(word);
        //HIER GESTOPT MET PROGRAMMEREN!
    }

    public Word generateHint(Word word) {
        //RETURN BEGINLETTER + ALLEEN MAAR *
        return word;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Word getGuess() {
        return guess;
    }

    public void setGuess(Word guess) {
        this.guess = guess;
    }

    public Word getHint() {
        return hint;
    }

    public void setHint(Word hint) {
        this.hint = hint;
    }
}