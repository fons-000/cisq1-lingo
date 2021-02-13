package nl.hu.cisq1.lingo.words.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TurnTest {
    //Turn
    //1. Kan turn goed gemaakt worden en werken de sets? This is already tested in other tests.
    //2. Kijk of het generen van feedback voor de volgende turn mogelijk is

    //Onderstaande use cases zijn al getest in FeedbackTest, however testen we nu of de acceser van deze methode
    //de uitkomst goed doorgeeft. Werkt het aanmaken/verder invullen van een Turn goed in combinatie met Feedback?
    //---------------------------------------------------------------------------------------------------------
    //Gaat het genereren van feedback goed op de volgende use cases:
    //1. De lengte van de guess is te lang of te kort (alleen maar INVALID)
    //   Guess: HAMSTER
    //   Word: HAMER
    //   Feedback: INVALID, INVALID, INVALID, INVALID, INVALID, INVALID
    //   Turn's HINT: "HAM.."
    //   Nex turns's HINT: "HAM.."
    //------------------------------------------------------------
    //   Guess KAM
    //   Word: KAMER
    //   Feedback: INVALID, INVALID, INVALID
    //   Turn's HINT: "K...."
    //   Next turn's HINT: "K...."
    //2. Het woord is goed geraden (alleen maar CORRECT)
    //   Guess: GEBAK
    //   Word: GEBAK
    //   Feedback: CORRECT, CORRECT, CORRECT, CORRECT, CORRECT
    //   Turn's HINT "GEB.."
    //   Next turn's HINT (Niks, generen lukt niet en gooit foutmelding op)

    //3. Het woord is fout geraden, wat de volgende subusecases geeft
    //3.1 De guess bevat <= letters per soort letter in het woord (Zie de OO op verschillende start/eindpunten van voorbeeld 1)
    //    Guess: BOOST
    //    Word:  BLOOT
    //    Feedback: CORRECT, PRESENT, CORRECT, ABSENT, CORRECT
    //    Turn's HINT "B...."
    //    Next turn's HINT "B.O.T"
    //------------------------------------------------------------
    //    Guess: BANG
    //    Word: BAAN
    //    Feedback: CORRECT, CORRECT, PRESENT, ABSENT
    //    Turn's HINT "B..."
    //    Next turn's HINT "BA.."
    //3.2 De guess bevat > letters per soort letter in het woord (Zie de eerste twee letters van voorbeeld 1)
    //    Guess: OORFLAP
    //    Word: OPZICHT
    //    Feedback: CORRECT, ABSENT, ABSENT, ABSENT, ABSENT, ABSENT, PRESENT
    //    Turn's HINT "O......"
    //    Next turn's HINT "O......"
    //------------------------------------------------------------
    //    Guess: HAHAH (3 H) (2 A)
    //    Word: AHAHA (2 H) (3 A)
    // Wat zou moeten betekenen bij de 3 'H' ABSENT
    //    Feedback: PRESENT, PRESENT, PRESENT, PRESENT, ABSENT
    //    Turn's HINT "AH..."
    //    Next turn's HINT "AH..."
    //---------------------------------------------------------------------------------------------------------

    //3. Kijk of het generen van een Next turn's hint werkt (zie Next turn's Hint bij de bovenstaande use cases)

    @Test
    @DisplayName("returnCurrentFeedback#1 -> Test #1 - to long. Return valid Turn object.")
    void returnCurrentFeedback1() {
        Word word = new Word("HAMER");
        Word hint = new Word("HAM");
        Word guess = new Word("HAMSTER");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        String wordTurnString = turn.getWord().getValue();
        String hintTurnString = turn.getHint().getValue();
        String guessTurnString = turn.getGuess().getValue();
        Feedback feedbackTurn = turn.getFeedback();

        assertSame("HAMER", wordTurnString);
        assertSame("HAM", hintTurnString);
        assertSame("HAMSTER", guessTurnString);

        ArrayList<FeedbackItem> feedbackItems = feedbackTurn.getFeedbackItems();

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
    @DisplayName("returnCurrentFeedbackk#1 -> Test #2 - to short. Return valid Turn object.")
    void returnCurrentFeedback2() {
        Word word = new Word("KAMER");
        Word hint = new Word("K");
        Word guess = new Word("KAM");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        String wordTurnString = turn.getWord().getValue();
        String hintTurnString = turn.getHint().getValue();
        String guessTurnString = turn.getGuess().getValue();
        Feedback feedbackTurn = turn.getFeedback();

        assertSame("KAMER", wordTurnString);
        assertSame("K", hintTurnString);
        assertSame("KAM", guessTurnString);

        ArrayList<FeedbackItem> feedbackItems = feedbackTurn.getFeedbackItems();

        assertSame(3, feedbackItems.size());

        FeedbackItem feedbackItem1 = feedbackItems.get(0);
        FeedbackItem feedbackItem2 = feedbackItems.get(1);
        FeedbackItem feedbackItem3 = feedbackItems.get(2);

        assertSame(FeedbackItem.INVALID, feedbackItem1);
        assertSame(FeedbackItem.INVALID, feedbackItem2);
        assertSame(FeedbackItem.INVALID, feedbackItem3);
    }

    @Test
    @DisplayName("returnCurrentFeedbackk#2 -> the word has been guessed correctly! Return valid Turn object.")
    void returnCurrentFeedback3() {
        Word word = new Word("GEBAK");
        Word hint = new Word("GEB");
        Word guess = new Word("GEBAK");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        String wordTurnString = turn.getWord().getValue();
        String hintTurnString = turn.getHint().getValue();
        String guessTurnString = turn.getGuess().getValue();
        Feedback feedbackTurn = turn.getFeedback();

        assertSame("GEBAK", wordTurnString);
        assertSame("GEB", hintTurnString);
        assertSame("GEBAK", guessTurnString);

        ArrayList<FeedbackItem> feedbackItems = feedbackTurn.getFeedbackItems();

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
    @DisplayName("returnCurrentFeedbackk#3 -> the word has been guessed wrongly -" +
            " guess <= letters per sort leter! Return valid Turn object.")
    void returnCurrentFeedback4() {
        Word word = new Word("BLOOT");
        Word hint = new Word("B");
        Word guess = new Word("BOOST");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        String wordTurnString = turn.getWord().getValue();
        String hintTurnString = turn.getHint().getValue();
        String guessTurnString = turn.getGuess().getValue();
        Feedback feedbackTurn = turn.getFeedback();

        assertSame("BLOOT", wordTurnString);
        assertSame("B", hintTurnString);
        assertSame("BOOST", guessTurnString);

        ArrayList<FeedbackItem> feedbackItems = feedbackTurn.getFeedbackItems();

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
    @DisplayName("returnCurrentFeedbackk#3 -> #2 the word has been guessed wrongly -" +
            " guess <= letters per sort leter! Return valid Turn object.")
    void returnCurrentFeedback5() {
        Word word = new Word("BAAN");
        Word hint = new Word("B");
        Word guess = new Word("BANG");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        String wordTurnString = turn.getWord().getValue();
        String hintTurnString = turn.getHint().getValue();
        String guessTurnString = turn.getGuess().getValue();
        Feedback feedbackTurn = turn.getFeedback();

        assertSame("BAAN", wordTurnString);
        assertSame("B", hintTurnString);
        assertSame("BANG", guessTurnString);

        ArrayList<FeedbackItem> feedbackItems = feedbackTurn.getFeedbackItems();

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
    @DisplayName("returnCurrentFeedbackk#4 -> the word has been guessed wrongly -" +
            " guess > letters per sort leter! Return valid Turn object.")
    void returnCurrentFeedback6() {
        Word word = new Word("OPZICHT");
        Word hint = new Word("OOR");
        Word guess = new Word("OORFLAP");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        String wordTurnString = turn.getWord().getValue();
        String hintTurnString = turn.getHint().getValue();
        String guessTurnString = turn.getGuess().getValue();
        Feedback feedbackTurn = turn.getFeedback();

        assertSame("OPZICHT", wordTurnString);
        assertSame("OOR", hintTurnString);
        assertSame("OORFLAP", guessTurnString);

        ArrayList<FeedbackItem> feedbackItems = feedbackTurn.getFeedbackItems();

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
    @DisplayName("returnCurrentFeedbackk#4 -> #2 the word has been guessed wrongly -" +
            " guess > letters per sort leter! Return valid Turn object.")
    void returnCurrentFeedback7() {
        Word word = new Word("AHAHA");
        Word hint = new Word("AH");
        Word guess = new Word("HAHAH");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        String wordTurnString = turn.getWord().getValue();
        String hintTurnString = turn.getHint().getValue();
        String guessTurnString = turn.getGuess().getValue();
        Feedback feedbackTurn = turn.getFeedback();

        assertSame("AHAHA", wordTurnString);
        assertSame("AH", hintTurnString);
        assertSame("HAHAH", guessTurnString);

        ArrayList<FeedbackItem> feedbackItems = feedbackTurn.getFeedbackItems();

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

    @Test
    @DisplayName("generateNewHint#1 -> Test #1 - to long.")
    void returnHint1() {
        Word word = new Word("HAMER");
        Word hint = new Word("HAM..");
        Word guess = new Word("HAMSTER");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        Word newHint = turn.returnHintForNextTurn();
        String newHintValue = newHint.getValue();
        assertEquals("HAM..", newHintValue);
    }

    @Test
    @DisplayName("generateNewHint#1 -> Test #2 - to short.")
    void returnHint2() {
        Word word = new Word("KAMER");
        Word hint = new Word("K....");
        Word guess = new Word("KAM");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        Word newHint = turn.returnHintForNextTurn();
        String newHintValue = newHint.getValue();
        assertEquals("K....", newHintValue);
    }

    @Test
    @DisplayName("generateNewHint#2 -> the word has been guessed correctly!")
    void returnHint3() {
        Word word = new Word("GEBAK");
        Word hint = new Word("GEB..");
        Word guess = new Word("GEBAK");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        //When a word is guessed, it can't generate a new hint => because there is no next turn
        assertThrows(NullPointerException.class, () -> {
                Word newHint = turn.returnHintForNextTurn();
        });
    }

    @Test
    @DisplayName("generateNewHint#3 -> Test #1 - the word has been guessed wrongly. - " +
            "guess <= letters per sort leter!")
    void returnHint4() {
        Word word = new Word("BLOOT");
        Word hint = new Word("B....");
        Word guess = new Word("BOOST");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        Word newHint = turn.returnHintForNextTurn();
        String newHintValue = newHint.getValue();
        assertEquals("B.O.T", newHintValue);
    }

    @Test
    @DisplayName("generateNewHint#3 -> Test #2 - the word has been guessed wrongly. - " +
            "guess <= letters per sort leter!")
    void returnHint5() {
        Word word = new Word("BAAN");
        Word hint = new Word("B....");
        Word guess = new Word("BANG");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        Word newHint = turn.returnHintForNextTurn();
        String newHintValue = newHint.getValue();
        assertEquals("BA..", newHintValue);
    }

    @Test
    @DisplayName("generateNewHint#4 -> Test #1 - the word has been guessed wrongly. - " +
            "guess <= letters per sort leter!")
    void returnHint6() {
        Word word = new Word("OPZICHT");
        Word hint = new Word("O......");
        Word guess = new Word("OORFLAP");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        Word newHint = turn.returnHintForNextTurn();
        String newHintValue = newHint.getValue();
        assertEquals("O......", newHintValue);
    }

    @Test
    @DisplayName("generateNewHint#4 -> Test #2 - the word has been guessed wrongly. - " +
            "guess <= letters per sort leter!")
    void returnHint7() {
        Word word = new Word("AHAHA");
        Word hint = new Word("AH...");
        Word guess = new Word("HAHAH");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        Word newHint = turn.returnHintForNextTurn();
        String newHintValue = newHint.getValue();
        assertEquals("AH...", newHintValue);
    }
}