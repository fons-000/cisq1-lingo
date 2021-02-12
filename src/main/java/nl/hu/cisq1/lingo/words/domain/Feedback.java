package nl.hu.cisq1.lingo.words.domain;

import java.util.ArrayList;
import java.util.Optional;

public class Feedback {
    private ArrayList<FeedbackItem> feedbackItems = new ArrayList<FeedbackItem>();

    public ArrayList<FeedbackItem> getFeedbackItems() {
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
        String feedbackWord = "------";
        char[] wordCharArray = wordValue.toCharArray();
        char[] guessCharArray = guessValue.toCharArray();
        char[] feedbackCharArray = feedbackWord.toCharArray();
        boolean returnFeedback = true;

        //Als de lengte van de gok ongelijk is aan de lengte van het woord,
        //geef een lijst van INVALID terug die even lang is als het aantal letters van de guess
        if(guessCharArray.length != wordCharArray.length) {
            for(char letter : guessCharArray) {
                if(!(feedback.addFeedbackItem(FeedbackItem.INVALID))) {
                    returnFeedback = false;
                }
            }
        }

        else {
            // Step 1
            for(int i = 0; i < wordCharArray.length; i++) {
                //Geef CORRECT terug wanneer de letter op de juiste plaats + hetzelfde is
                if (wordCharArray[i] == guessCharArray[i]) {
//                    if (!(feedback.addFeedbackItem(FeedbackItem.CORRECT))) {
//                        returnFeedback = false;
//                    } else {
                        feedbackCharArray[i] = 'C';
//                    }
                }
            }
            // Step 2
            for(int i = 0; i < wordCharArray.length; i++) {
                //Geef ABSENT terug wanneer de letter helemaal niet in het word zit
                if(!wordValue.contains(String.valueOf(guessCharArray[i]))) {
//                    if(!(feedback.addFeedbackItem(FeedbackItem.ABSENT))) {
//                        returnFeedback = false;
//                    } else {
                        feedbackCharArray[i] = 'A';
//                    }
                }
            }

            // Edge case:
            // W: A U T T O P
            // G: A T S S T T
            // F: C P A A P A
            //              ^
            //              there are already 2 T's present the third is absent, not present

            // After Step 1 and 2 this is the result:
            // W: A U T T O P     j-loop,
            // G: A T S S T T     i-loop, k-loop(k<=i)
            // F: C - A A - -

            // We're looking at three T cases:
            // T1 (i=1)
            // T2 (i=4)
            // T3 (i=5)

            // Step 3
            for(int i = 0; i < guessCharArray.length; i++)
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
                        //T1 2 >= 1
                        //T2 2 >= 2
                        feedbackCharArray[i] = 'P';
                    } else {
                        //T3: 2 < 3
                        feedbackCharArray[i] = 'A';
                    }
            }



        if(returnFeedback) {
            //Optional.of zou moeten werken, werkt niet als feedback null is
            Optional<Feedback> optionalFeedback = Optional.of(feedback);
            return optionalFeedback;
        }
        return Optional.empty();
    }
}