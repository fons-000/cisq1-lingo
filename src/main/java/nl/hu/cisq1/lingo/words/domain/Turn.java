package nl.hu.cisq1.lingo.words.domain;

import java.util.ArrayList;
import java.util.Optional;

public class Turn {
    private Feedback feedback;
    private Word hint;
    private Word guess;
    //word moet later verwijderd worden als er een service laag is!
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

    //Deze methode moet eerst uitgevoerd worden, en daarna moet de uitkomst geset worden in de turn,
    //voordat returnHintForNextTurn aangeroepen kan worden.
    public Feedback returnFeedbackCurrentTurn() {
        Optional<Feedback> optionalFeedback = Feedback.generateFeedback(this.word, this.guess);
        //Throwed als de feedback empty is
        Feedback feedback = optionalFeedback.orElseThrow();
        return feedback;
    }

    public Word returnHintForNextTurn() {
        Optional<Word> optionalNextHint = this.generateHintForNextTurn();
        //Throwed als de feedback empty is en retourneerd niks (null)
        Word nextHint = optionalNextHint.orElseThrow();
        return nextHint;
    }

    public Optional<Word> generateHintForNextTurn() {
        String previousHintString = this.hint.getValue();
        ArrayList<FeedbackItem> feedbackItems = this.feedback.getFeedbackItems();
        String newHintValue = "";
        char[] newHintValueCharArray = new char[this.word.getLength()];

        //Deze moet geretourneerd worden (maar moet eerst verpakt worden in een Optional)
        Word newHint = new Word();

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
        newHint.setValue(newHintValue);


        //Stap 4 => Verpak de newHint in een Optional als de hint niet hetzelfde is
        //als het word of return een lege Optional
        if(!newHint.equals(this.word)) {
            Optional<Word> newHintOptional = Optional.of(newHint);
            return newHintOptional;
        }
        return Optional.empty();
    }
}