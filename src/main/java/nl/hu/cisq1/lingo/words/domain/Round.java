package nl.hu.cisq1.lingo.words.domain;

public class Round {
    private int roundOfGame;
    private Word word;
    private Word guess;
    private Word hint;

    public Round(Word word, int roundOfGame) {
        this.roundOfGame = roundOfGame;
        this.word = word;
        this.hint = generateHint(word);
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