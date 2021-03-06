package nl.hu.cisq1.lingo.trainer.domain;

import java.util.*;

public class Feedback {
    private List<FeedbackItem> feedbackItems = new ArrayList<FeedbackItem>();

    public Feedback() {
    }

    public Feedback(List<FeedbackItem> feedbackItems) {
        this.feedbackItems = feedbackItems;
    }

    public List<FeedbackItem> getFeedbackItems() {
        return feedbackItems;
    }

    public boolean addFeedbackItem(FeedbackItem feedbackItem) {
        return this.feedbackItems.add(feedbackItem);
    }

    public void setFeedbackItems(ArrayList<FeedbackItem> feedbackItems) {
        this.feedbackItems = feedbackItems;
    }

    public static Optional<Feedback> generateFeedback(Word word, Word guess) {
        //Retourneer false als de feedback onsuccesvol is verwerkt
        //Retourneer true als de feedback succesvol is verwerkt
        Feedback feedback = new Feedback();
        String guessValue = guess.getValue();
        String wordValue = word.getValue();
        char[] wordCharArray = wordValue.toCharArray();
        char[] guessCharArray = guessValue.toCharArray();
        int guessLength = guess.getLength();
        char[] feedbackCharArray = new char[guessLength];
        //Als de lengte van de gok ongelijk is aan de lengte van het woord,
        //geef een lijst van INVALID terug die even lang is als het aantal letters van de guess
        if(guessCharArray.length != wordCharArray.length) {
            for(char letter : guessCharArray) {
                feedback.addFeedbackItem(FeedbackItem.INVALID);
            }
        }

        else {
            // Step 1
            for(int i = 0; i < wordCharArray.length; i++) {
                //Geef CORRECT terug wanneer de letter op de juiste plaats + hetzelfde is
                if (wordCharArray[i] == guessCharArray[i]) {
                        feedbackCharArray[i] = 'C';
                }
                else //Geef ABSENT terug wanneer de letter helemaal niet in het word zit
                    if(!wordValue.contains(String.valueOf(guessCharArray[i]))) {
                        feedbackCharArray[i] = 'A';
                    }
            }

            // Step 3
            for(int i = 0; i < guessCharArray.length; i++) {
                if(feedbackCharArray[i] != 'C' && feedbackCharArray[i] != 'A') {

                    // Check how many time the guessed letter is in the word (not correct)
                    int numGuessedLetterInWord=0;
                    for(int j = 0; j < wordCharArray.length; j++) {
                        if(guessCharArray[i] == wordCharArray[j] && feedbackCharArray[j] != 'C') {
                            numGuessedLetterInWord++;
                        }
                    }

                    // Check how many times the letter has been guessed so far
                    int numLetterGuessedSoFar=0;
                    for (int k=0;k<=i;k++) {
                        if (guessCharArray[k] == guessCharArray[i] && feedbackCharArray[k] != 'C') {
                            numLetterGuessedSoFar++;
                        }
                    }

                    if (numGuessedLetterInWord >= numLetterGuessedSoFar) {
                        //Geef PRESENT of ABSENT terug a.d.h.v het aantal zelfde letters in het woord en de guess
                        //Een letter kan wel in het woord zitten,
                        //maar het kan nog steeds ABSENT zijn als er teveel van hetzelfde soort letters in een guess zitten.
                        //T1 2 >= 1
                        //T2 2 >= 2
                        feedbackCharArray[i] = 'P';
                    } else {
                        //T3: 2 < 3
                        feedbackCharArray[i] = 'A';
                    }
                }
            }}

            for(char letter : feedbackCharArray) {
                switch (letter) {
                    case 'P':
                        feedback.addFeedbackItem(FeedbackItem.PRESENT);
                        break;
                    case 'A':
                        feedback.addFeedbackItem(FeedbackItem.ABSENT);
                        break;
                    case 'C':
                        feedback.addFeedbackItem(FeedbackItem.CORRECT);
                        break;
                }
            }
        //Optional.of zou moeten werken, werkt niet als feedback null is
        Optional<Feedback> optionalFeedback = Optional.of(feedback);
        return optionalFeedback;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackItems=" + feedbackItems +
                '}';
    }
}