package nl.hu.cisq1.lingo.words.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackTest {
    //Feedbacktest
    //
    //1. Gaat feedback aanmaken goed, zitten de juiste waardes erin na een:
        //1. Create & Set
        //2. Add
    //2. Gaat het genereren van feedback goed op de volgende use cases:
        //1. De lengte van de guess is te lang of te kort (alleen maar INVALID)
        //   Guess: HAMSTER
        //   Word: HAMER
        //   Feedback: INVALID, INVALID, INVALID, INVALID, INVALID, INVALID
        //------------------------------------------------------------
        //   Guess KAM
        //   Word: KAMER
        //   Feedback: INVALID, INVALID, INVALID
        //2. Het woord is goed geraden (alleen maar CORRECT)
        //   Guess: GEBAK
        //   Word: GEBAK
        //   Feedback: CORRECT, CORRECT, CORRECT, CORRECT, CORRECT
        //3. Het woord is fout geraden, wat de volgende subusecases geeft
            //3.1 De guess bevat <= letters per soort letter in het woord (Zie de OO op verschillende start/eindpunten van voorbeeld 1)
            //    Guess: BOOST
            //    Word:  BLOOT
            //    Feedback: CORRECT, PRESENT, CORRECT, ABSENT, CORRECT
            //------------------------------------------------------------
            //    Guess: BANG
            //    Word: BAAN
            //    Feedback: CORRECT, CORRECT, PRESENT, ABSENT
            //3.2 De guess bevat > letters per soort letter in het woord (Zie de eerste twee letters van voorbeeld 1)
            //    Guess: OORFLAP
            //    Word: OPZICHT
            //    Feedback: CORRECT, ABSENT, ABSENT, ABSENT, ABSENT, ABSENT, PRESENT
            //------------------------------------------------------------
            //    Guess: HAHAH (3 H) (2 A)
            //    Word: AHAHA (2 H) (3 A)
            // Wat zou moeten betekenen bij de 3 'H' ABSENT
            //    Feedback: PRESENT, PRESENT, PRESENT, PRESENT, ABSENT

    ArrayList<FeedbackItem> feedbackItems = new ArrayList<FeedbackItem>(Arrays.asList
            (FeedbackItem.CORRECT, FeedbackItem.ABSENT, FeedbackItem.PRESENT,
                    FeedbackItem.PRESENT, FeedbackItem.CORRECT, FeedbackItem.ABSENT));

    Feedback feedback = new Feedback(feedbackItems);

    public int getFeedbackItemsSize() {
        return feedback.getFeedbackItems().size();
    }

    @AfterEach
    public void after() {
        feedback.setFeedbackItems(new ArrayList<FeedbackItem>(Arrays.asList
                (FeedbackItem.CORRECT, FeedbackItem.ABSENT, FeedbackItem.PRESENT,
                        FeedbackItem.PRESENT, FeedbackItem.CORRECT, FeedbackItem.ABSENT)));
    }

    @Test
    @DisplayName("Creating Feedback and setting feedbackItems gets the right attributes")
    void creatingFeedback() {
        Feedback feedback2 = new Feedback();

        assertSame(0, feedback2.getFeedbackItems().size());

        feedback2.setFeedbackItems(feedbackItems);

        assertSame(6, feedback2.getFeedbackItems().size());

        FeedbackItem feedbackItem1 = feedback2.getFeedbackItems().get(0);
        FeedbackItem feedbackItem2 = feedback2.getFeedbackItems().get(1);
        FeedbackItem feedbackItem3 = feedback2.getFeedbackItems().get(2);
        FeedbackItem feedbackItem4 = feedback2.getFeedbackItems().get(3);
        FeedbackItem feedbackItem5 = feedback2.getFeedbackItems().get(4);
        FeedbackItem feedbackItem6 = feedback2.getFeedbackItems().get(5);

        assertSame(FeedbackItem.CORRECT, feedbackItem1);
        assertSame(FeedbackItem.ABSENT, feedbackItem2);
        assertSame(FeedbackItem.PRESENT, feedbackItem3);
        assertSame(FeedbackItem.PRESENT, feedbackItem4);
        assertSame(FeedbackItem.CORRECT, feedbackItem5);
        assertSame(FeedbackItem.ABSENT, feedbackItem6);
    }

    @Test
    @DisplayName("Add feedbackItems works")
    void addAttributes() {
        assertSame(6, getFeedbackItemsSize());

        feedbackItems.add(FeedbackItem.CORRECT);
        feedbackItems.add(FeedbackItem.ABSENT);
        feedbackItems.add(FeedbackItem.PRESENT);
        feedbackItems.add(FeedbackItem.INVALID);

        assertSame(10, getFeedbackItemsSize());

        FeedbackItem feedbackItem1 = feedback.getFeedbackItems().get(0);
        FeedbackItem feedbackItem2 = feedback.getFeedbackItems().get(1);
        FeedbackItem feedbackItem3 = feedback.getFeedbackItems().get(2);
        FeedbackItem feedbackItem4 = feedback.getFeedbackItems().get(3);
        FeedbackItem feedbackItem5 = feedback.getFeedbackItems().get(4);
        FeedbackItem feedbackItem6 = feedback.getFeedbackItems().get(5);
        FeedbackItem feedbackItem7 = feedback.getFeedbackItems().get(6);
        FeedbackItem feedbackItem8 = feedback.getFeedbackItems().get(7);
        FeedbackItem feedbackItem9 = feedback.getFeedbackItems().get(8);
        FeedbackItem feedbackItem10 = feedback.getFeedbackItems().get(9);

        assertSame(FeedbackItem.CORRECT, feedbackItem1);
        assertSame(FeedbackItem.ABSENT, feedbackItem2);
        assertSame(FeedbackItem.PRESENT, feedbackItem3);
        assertSame(FeedbackItem.PRESENT, feedbackItem4);
        assertSame(FeedbackItem.CORRECT, feedbackItem5);
        assertSame(FeedbackItem.ABSENT, feedbackItem6);

        assertSame(FeedbackItem.CORRECT, feedbackItem7);
        assertSame(FeedbackItem.ABSENT, feedbackItem8);
        assertSame(FeedbackItem.PRESENT, feedbackItem9);
        assertSame(FeedbackItem.INVALID, feedbackItem10);
    }

    @Test
    @DisplayName("Generate feedback #1, return all INVALID if guess length is to long")
    void generateInvalidFeedbackToLong() {
        //1. De lengte van de guess is te lang (alleen maar INVALID)
        //   Guess: HAMSTER
        //   Word: HAMER
        //   Feedback: INVALID, INVALID, INVALID, INVALID, INVALID, INVALID, INVALID

        Word word = Word.createValidWord("HAMER");
        Word guess = Word.createValidWord("HAMSTER");
        Optional<Feedback> optionalFeedback = Feedback.generateFeedback(word, guess);
        //Throwed als de feedback empty is
        Feedback feedback = optionalFeedback.orElseThrow();
        ArrayList<FeedbackItem> feedbackItems = feedback.getFeedbackItems();

        assertSame(7, feedbackItems.size());

        FeedbackItem feedbackItem1 = feedbackItems.get(0);
        FeedbackItem feedbackItem2 = feedbackItems.get(1);
        FeedbackItem feedbackItem3 = feedbackItems.get(2);
        FeedbackItem feedbackItem4 = feedbackItems.get(3);
        FeedbackItem feedbackItem5 = feedbackItems.get(4);
        FeedbackItem feedbackItem6 = feedbackItems.get(5);
        FeedbackItem feedbackItem7 = feedbackItems.get(6);

        assertSame(FeedbackItem.INVALID, feedbackItem1);
        assertSame(FeedbackItem.INVALID, feedbackItem2);
        assertSame(FeedbackItem.INVALID, feedbackItem3);
        assertSame(FeedbackItem.INVALID, feedbackItem4);
        assertSame(FeedbackItem.INVALID, feedbackItem5);
        assertSame(FeedbackItem.INVALID, feedbackItem6);
        assertSame(FeedbackItem.INVALID, feedbackItem7);
    }

    @Test
    @DisplayName("Generate feedback #2, return all INVALID if guess length is to short")
    void generateInvalidFeedbackToShort() {
        //1. De lengte van de guess is te kort (alleen maar INVALID)
        //   Guess KAM
        //   Word: KAMER
        //   Feedback: INVALID, INVALID, INVALID

        Word word = Word.createValidWord("KAMER");
        Word guess = Word.createValidWord("KAM");
        Optional<Feedback> optionalFeedback = Feedback.generateFeedback(word, guess);
        //Throwed als de feedback empty is
        Feedback feedback = optionalFeedback.orElseThrow();
        ArrayList<FeedbackItem> feedbackItems = feedback.getFeedbackItems();

        assertSame(3, feedbackItems.size());

        FeedbackItem feedbackItem1 = feedbackItems.get(0);
        FeedbackItem feedbackItem2 = feedbackItems.get(1);
        FeedbackItem feedbackItem3 = feedbackItems.get(2);

        assertSame(FeedbackItem.INVALID, feedbackItem1);
        assertSame(FeedbackItem.INVALID, feedbackItem2);
        assertSame(FeedbackItem.INVALID, feedbackItem3);
    }


    @Test
    @DisplayName("Generate feedback #3, return all CORRECT when the word is guessed.")
    void generateCorrectGuess() {
        //2. Het woord is goed geraden (alleen maar CORRECT)
        //   Guess: GEBAK
        //   Word: GEBAK
        //   Feedback: CORRECT, CORRECT, CORRECT, CORRECT, CORRECT

        Word word = Word.createValidWord("GEBAK");
        Word guess = Word.createValidWord("GEBAK");
        Optional<Feedback> optionalFeedback = Feedback.generateFeedback(word, guess);
        //Throwed als de feedback empty is
        Feedback feedback = optionalFeedback.orElseThrow();
        ArrayList<FeedbackItem> feedbackItems = feedback.getFeedbackItems();

        assertSame(5, feedbackItems.size());

        FeedbackItem feedbackItem1 = feedbackItems.get(0);
        FeedbackItem feedbackItem2 = feedbackItems.get(1);
        FeedbackItem feedbackItem3 = feedbackItems.get(2);
        FeedbackItem feedbackItem4 = feedbackItems.get(3);
        FeedbackItem feedbackItem5 = feedbackItems.get(4);

        assertSame(FeedbackItem.CORRECT, feedbackItem1);
        assertSame(FeedbackItem.CORRECT, feedbackItem2);
        assertSame(FeedbackItem.CORRECT, feedbackItem3);
        assertSame(FeedbackItem.CORRECT, feedbackItem4);
        assertSame(FeedbackItem.CORRECT, feedbackItem5);
    }

    @Test
    @DisplayName("Generate feedback #4.1.1 Wrong word => Check #1 <= letters sort word if PRESENT & ABSENT (no such letter) works correctly. ")
    void generateWrongPresentLetterGuess() {
        //3. Het woord is fout geraden, wat de volgende subusecases geeft
            //3.1 De guess bevat <= letters per soort letter in het woord
            // (Zie de OO op verschillende start/eindpunten van voorbeeld 1)
            //    Guess: BOOST
            //    Word:  BLOOT
            //    Feedback: CORRECT, PRESENT, CORRECT, ABSENT, CORRECT

        Word word = Word.createValidWord("BLOOT");
        Word guess = Word.createValidWord("BOOST");
        Optional<Feedback> optionalFeedback = Feedback.generateFeedback(word, guess);
        //Throwed als de feedback empty is
        Feedback feedback = optionalFeedback.orElseThrow();
        ArrayList<FeedbackItem> feedbackItems = feedback.getFeedbackItems();

        assertSame(5, feedbackItems.size());

        FeedbackItem feedbackItem1 = feedbackItems.get(0);
        FeedbackItem feedbackItem2 = feedbackItems.get(1);
        FeedbackItem feedbackItem3 = feedbackItems.get(2);
        FeedbackItem feedbackItem4 = feedbackItems.get(3);
        FeedbackItem feedbackItem5 = feedbackItems.get(4);

        assertSame(FeedbackItem.CORRECT, feedbackItem1);
        assertSame(FeedbackItem.PRESENT, feedbackItem2);
        assertSame(FeedbackItem.CORRECT, feedbackItem3);
        assertSame(FeedbackItem.ABSENT, feedbackItem4);
        assertSame(FeedbackItem.CORRECT, feedbackItem5);
    }

    @Test
    @DisplayName("Generate feedback #4.1.2 Wrong word => Check #2 <= letters if PRESENT & ABSENT (no such letter) works correctly.")
    void generateWrongPresentLetterGuess2() {
        //3. Het woord is fout geraden, wat de volgende subusecases geeft
        //3.1 De guess bevat <= letters per soort letter in het woord
        //    Guess: BANG
        //    Word: BAAN
        //    Feedback: CORRECT, CORRECT, PRESENT, ABSENT

        Word word = Word.createValidWord("BAAN");
        Word guess = Word.createValidWord("BANG");
        Optional<Feedback> optionalFeedback = Feedback.generateFeedback(word, guess);
        //Throwed als de feedback empty is
        Feedback feedback = optionalFeedback.orElseThrow();
        ArrayList<FeedbackItem> feedbackItems = feedback.getFeedbackItems();

        assertSame(4, feedbackItems.size());

        FeedbackItem feedbackItem1 = feedbackItems.get(0);
        FeedbackItem feedbackItem2 = feedbackItems.get(1);
        FeedbackItem feedbackItem3 = feedbackItems.get(2);
        FeedbackItem feedbackItem4 = feedbackItems.get(3);

        assertSame(FeedbackItem.CORRECT, feedbackItem1);
        assertSame(FeedbackItem.CORRECT, feedbackItem2);
        assertSame(FeedbackItem.PRESENT, feedbackItem3);
        assertSame(FeedbackItem.ABSENT, feedbackItem4);
    }

    @Test
    @DisplayName("Generate feedback #4.2.1 Wrong word => Check #1 > letters if PRESENT & ABSENT (no such letter) works correctly.")
    void generateWrongPresentLetterGuess3() {
        //3.2 De guess bevat > letters per soort letter in het woord (Zie de eerste twee letters van voorbeeld 1)
        //    Guess: OORFLAP
        //    Word: OPZICHT
        //    Feedback: CORRECT, ABSENT, ABSENT, ABSENT, ABSENT, ABSENT, PRESENT

        Word word = Word.createValidWord("OPZICHT");
        Word guess = Word.createValidWord("OORFLAP");
        Optional<Feedback> optionalFeedback = Feedback.generateFeedback(word, guess);
        //Throwed als de feedback empty is
        Feedback feedback = optionalFeedback.orElseThrow();
        ArrayList<FeedbackItem> feedbackItems = feedback.getFeedbackItems();

        assertSame(7, feedbackItems.size());

        FeedbackItem feedbackItem1 = feedbackItems.get(0);
        FeedbackItem feedbackItem2 = feedbackItems.get(1);
        FeedbackItem feedbackItem3 = feedbackItems.get(2);
        FeedbackItem feedbackItem4 = feedbackItems.get(3);
        FeedbackItem feedbackItem5 = feedbackItems.get(4);
        FeedbackItem feedbackItem6 = feedbackItems.get(5);
        FeedbackItem feedbackItem7 = feedbackItems.get(6);

        assertSame(FeedbackItem.CORRECT, feedbackItem1);
        assertSame(FeedbackItem.ABSENT, feedbackItem2);
        assertSame(FeedbackItem.ABSENT, feedbackItem3);
        assertSame(FeedbackItem.ABSENT, feedbackItem4);
        assertSame(FeedbackItem.ABSENT, feedbackItem5);
        assertSame(FeedbackItem.ABSENT, feedbackItem6);
        assertSame(FeedbackItem.PRESENT, feedbackItem7);
    }

    @Test
    @DisplayName("Generate feedback #4.2.2 Wrong word => Check #2 > letters if PRESENT & ABSENT (no such letter) works correctly.")
    void generateWrongPresentLetterGuess4() {
        //3.2 De guess bevat > letters per soort letter in het woord (Zie de eerste twee letters van voorbeeld 1)
        //    Guess: HAHAH (3 H) (2 A)
        //    Word: AHAHA (2 H) (3 A)
        // Wat zou moeten betekenen bij de 3 'H' ABSENT
        //    Feedback: PRESENT, PRESENT, PRESENT, PRESENT, ABSENT

        Word word = Word.createValidWord("AHAHA");
        Word guess = Word.createValidWord("HAHAH");
        Optional<Feedback> optionalFeedback = Feedback.generateFeedback(word, guess);
        //Throwed als de feedback empty is
        Feedback feedback = optionalFeedback.orElseThrow();
        ArrayList<FeedbackItem> feedbackItems = feedback.getFeedbackItems();

        assertSame(5, feedbackItems.size());

        FeedbackItem feedbackItem1 = feedbackItems.get(0);
        FeedbackItem feedbackItem2 = feedbackItems.get(1);
        FeedbackItem feedbackItem3 = feedbackItems.get(2);
        FeedbackItem feedbackItem4 = feedbackItems.get(3);
        FeedbackItem feedbackItem5 = feedbackItems.get(4);

        assertSame(FeedbackItem.PRESENT, feedbackItem1);
        assertSame(FeedbackItem.PRESENT, feedbackItem2);
        assertSame(FeedbackItem.PRESENT, feedbackItem3);
        assertSame(FeedbackItem.PRESENT, feedbackItem4);
        assertSame(FeedbackItem.ABSENT, feedbackItem5);
    }
}