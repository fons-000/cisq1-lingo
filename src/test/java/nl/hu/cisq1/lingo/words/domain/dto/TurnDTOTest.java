package nl.hu.cisq1.lingo.words.domain.dto;

import nl.hu.cisq1.lingo.words.domain.Feedback;
import nl.hu.cisq1.lingo.words.domain.FeedbackItem;
import nl.hu.cisq1.lingo.words.domain.Turn;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnDTOTest {
    //TurnDTO
    //Voor TurnDTO gaan we alle use cases van turn langs, en kijken we of TurnDTO de verwachte turnvalues heeft.
    //Zie onder alle use cases (waardes) die een Turn kan hebben. Omdat de returnCurrentFeedback testen,
    //alle use cases bevatten van een huidig gevulde Turn, zijn dit de tests die we gebruiken en relevant zijn voor
    //de conversie naar TurnDTO.

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
        Word word = Word.createValidWord("HAMER");
        Word hint = Word.createValidWord("HAM");
        Word guess = Word.createValidWord("HAMSTER");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        TurnDTO turnDTO = new TurnDTO(turn);

        String hintTurnString = turnDTO.getHint().getValue();
        String guessTurnString = turnDTO.getGuess().getValue();
        Feedback feedbackTurn = turnDTO.getFeedback();

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
        Word word = Word.createValidWord("KAMER");
        Word hint = Word.createValidWord("K");
        Word guess = Word.createValidWord("KAM");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        TurnDTO turnDTO = new TurnDTO(turn);

        String hintTurnString = turnDTO.getHint().getValue();
        String guessTurnString = turnDTO.getGuess().getValue();
        Feedback feedbackTurn = turnDTO.getFeedback();

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
        Word word = Word.createValidWord("GEBAK");
        Word hint = Word.createValidWord("GEB");
        Word guess = Word.createValidWord("GEBAK");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        TurnDTO turnDTO = new TurnDTO(turn);

        String hintTurnString = turnDTO.getHint().getValue();
        String guessTurnString = turnDTO.getGuess().getValue();
        Feedback feedbackTurn = turnDTO.getFeedback();

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
        Word word = Word.createValidWord("BLOOT");
        Word hint = Word.createValidWord("B");
        Word guess = Word.createValidWord("BOOST");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        TurnDTO turnDTO = new TurnDTO(turn);

        String hintTurnString = turnDTO.getHint().getValue();
        String guessTurnString = turnDTO.getGuess().getValue();
        Feedback feedbackTurn = turnDTO.getFeedback();

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
        Word word = Word.createValidWord("BAAN");
        Word hint = Word.createValidWord("B");
        Word guess = Word.createValidWord("BANG");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        TurnDTO turnDTO = new TurnDTO(turn);

        String hintTurnString = turnDTO.getHint().getValue();
        String guessTurnString = turnDTO.getGuess().getValue();
        Feedback feedbackTurn = turnDTO.getFeedback();

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
        Word word = Word.createValidWord("OPZICHT");
        Word hint = Word.createValidWord("OOR");
        Word guess = Word.createValidWord("OORFLAP");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        TurnDTO turnDTO = new TurnDTO(turn);

        String hintTurnString = turnDTO.getHint().getValue();
        String guessTurnString = turnDTO.getGuess().getValue();
        Feedback feedbackTurn = turnDTO.getFeedback();

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
        Word word = Word.createValidWord("AHAHA");
        Word hint = Word.createValidWord("AH");
        Word guess = Word.createValidWord("HAHAH");
        Turn turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        TurnDTO turnDTO = new TurnDTO(turn);

        String hintTurnString = turnDTO.getHint().getValue();
        String guessTurnString = turnDTO.getGuess().getValue();
        Feedback feedbackTurn = turnDTO.getFeedback();

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
}