package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class RoundDTOTest {
    //RoundDTO
    //Voor RoundDTO gaan we kijken of de waardes die in een round object zou zitten (zie RoundTest)
    //ook in RoundDTO zit, als deze round meegegeven wordt in de constructor.

    //Kijk indirect of de getRoundOfGame & getFirstHint goed werken.

    Turn turn = new Turn();
    Turn turn2 = new Turn();
    Turn turn3 = new Turn();
    Turn turn4 = new Turn();
    Turn turn11 = new Turn();
    Turn turn12 = new Turn();
    Turn turn13 = new Turn();
    Turn turn14 = new Turn();
    Turn turn15 = new Turn();

    @BeforeEach
    public void after() {
        //Turns: turn2, turn3 and turn4 are truly for the purpose to produce false when trying to add
        //it to a round when the word is different
        //Turns: turn, turn11, turn12, turn13, turn14 and turn 15 are used to simulate a round of a game
        Word word = Word.createValidWord("HAMER");
        Word hint = Hint.createValidHint("HAM..");
        Word guess = Guess.createValidGuess("HAMSTER");
        turn = new Turn(hint, guess, word);

        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);

        Word word11 = Word.createValidWord("HAMER");
        Word hint11 = turn.returnHintForNextTurn();
        Word guess11 = Guess.createValidGuess("HARDE");
        turn11 = new Turn(hint11, guess11, word11);

        Feedback feedback11 = turn11.returnFeedbackCurrentTurn();
        turn11.setFeedback(feedback11);

        Word word12 = Word.createValidWord("HAMER");
        Word hint12 = turn11.returnHintForNextTurn();
        Word guess12 = Guess.createValidGuess("HAMEL");
        turn12 = new Turn(hint12, guess12, word12);

        Feedback feedback12 = turn12.returnFeedbackCurrentTurn();
        turn12.setFeedback(feedback12);

        Word word13 = Word.createValidWord("HAMER");
        Word hint13 = turn12.returnHintForNextTurn();
        Word guess13 = Guess.createValidGuess("RUFTI");
        turn13 = new Turn(hint13, guess13, word13);

        Feedback feedback13 = turn13.returnFeedbackCurrentTurn();
        turn13.setFeedback(feedback13);

        Word word14 = Word.createValidWord("HAMER");
        Word hint14 = turn13.returnHintForNextTurn();
        Word guess14 = Guess.createValidGuess("GOVER");
        turn14 = new Turn(hint14, guess14, word14);

        Feedback feedback14 = turn14.returnFeedbackCurrentTurn();
        turn14.setFeedback(feedback14);

        Word word15 = Word.createValidWord("HAMER");
        Word hint15 = turn13.returnHintForNextTurn();
        Word guess15 = Guess.createValidGuess("PEACE");
        turn15 = new Turn(hint15, guess15, word15);

        Feedback feedback15 = turn15.returnFeedbackCurrentTurn();
        turn15.setFeedback(feedback15);

        Word word2 = Word.createValidWord("KAMER");
        Word hint2 = Hint.createValidHint("K....");
        Word guess2 = Guess.createValidGuess("KAM");
        turn2 = new Turn(hint2, guess2, word2);

        Feedback feedback2 = turn2.returnFeedbackCurrentTurn();
        turn2.setFeedback(feedback2);

        Word word3 = Word.createValidWord("GEBAK");
        Word hint3 = Hint.createValidHint("GEB..");
        Word guess3 = Guess.createValidGuess("GEBAK");
        turn3 = new Turn(hint3, guess3, word3);

        Feedback feedback3 = turn3.returnFeedbackCurrentTurn();
        turn3.setFeedback(feedback3);

        Word word4 = Word.createValidWord("BLOOT");
        Word hint4 = Hint.createValidHint("B....");
        Word guess4 = Guess.createValidGuess("BOOST");
        turn4 = new Turn(hint4, guess4, word4);

        Feedback feedback4 = turn4.returnFeedbackCurrentTurn();
        turn4.setFeedback(feedback4);
    }

    @Test
    @DisplayName("Creating round object works with the right property's "+
            " => also cannot make a sixth turn if the word hasn't been gueseed yet")
    void creatingRound() {
        //roundOfGame int wordt opgevangen, zodra er wordt gewerkt met een service!
        //Voor nu maakt dit attribuut niet zoveel uit en kan dit gewoon gebruikt worden
        //Wordt ook opgevangen in de GameTest
        Word word = Word.createValidWord("HAMER");
        Round round = new Round(word, 1);
        //1. Turn kan alleen worden  toegevoegd als het word van de turn hetzelfde is
        //en de lengte van de huidige turns kleiner dan 5 is.
        //Ook moet de turn de uiteraard juiste waardes hebben

        //Kijk of de word overeenkomt voor het toevoegen van een turn.
        //---------------------------------------
        assertSame(false, round.addTurn(turn2));
        assertSame(false, round.addTurn(turn3));
        assertSame(false, round.addTurn(turn4));
        assertSame(true, round.addTurn(turn));
        assertSame(true, round.addTurn(turn11));
        assertSame(true, round.addTurn(turn12));
        assertSame(true, round.addTurn(turn13));
        assertSame(true, round.addTurn(turn14));
        //Now try to create a valid word sixth round. This should return false, very important!
        //---------------------------------------
        assertSame(false, round.addTurn(turn15));
        //---------------------------------------

        RoundDTO roundDTO = RoundDTO.createRoundDTO(round);

        assertSame(5, roundDTO.getTurnDTOS().size());
        //---------------------------------------
        //Kijk of de waarde van de turn goed in de Round verwerkt is.
        //Doe dit vervolgens ook voor meerdere turns en check of de gegevens correct zijn + het niet 5 turns overscheidt.
        //---------------------------------------
        //---------------------------------------
        //Kijken of de juiste roundOfGame & firstHint erin zitten
        assertSame(1, roundDTO.getRoundOfGame());
        assertEquals("H", roundDTO.getFirstHint().getValue());
        //---------------------------------------
        //Checking turn 1
        //---------------------------------------
        String hintTurnString = roundDTO.getTurnDTOS().get(0).getHint().getValue();
        String guessTurnString = roundDTO.getTurnDTOS().get(0).getGuess().getValue();
        Feedback feedbackTurn = roundDTO.getTurnDTOS().get(0).getFeedback();

        assertEquals("HAM..", hintTurnString);
        assertEquals("HAMSTER", guessTurnString);

        List<FeedbackItem> feedbackItems = feedbackTurn.getFeedbackItems();

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
        //---------------------------------------
        //Checking turn 2
        //---------------------------------------
        String hintTurnString11 = roundDTO.getTurnDTOS().get(1).getHint().getValue();
        String guessTurnString11 = roundDTO.getTurnDTOS().get(1).getGuess().getValue();
        Feedback feedbackTurn11 = roundDTO.getTurnDTOS().get(1).getFeedback();

        assertEquals("HAM..", hintTurnString11);
        assertEquals("HARDE", guessTurnString11);

        List<FeedbackItem> feedbackItems11 = feedbackTurn11.getFeedbackItems();

        assertSame(5, feedbackItems11.size());

        FeedbackItem feedbackItem111 = feedbackItems11.get(0);
        FeedbackItem feedbackItem112 = feedbackItems11.get(1);
        FeedbackItem feedbackItem113 = feedbackItems11.get(2);
        FeedbackItem feedbackItem114 = feedbackItems11.get(3);
        FeedbackItem feedbackItem115 = feedbackItems11.get(4);

        assertSame(FeedbackItem.CORRECT, feedbackItem111);
        assertSame(FeedbackItem.CORRECT, feedbackItem112);
        assertSame(FeedbackItem.PRESENT, feedbackItem113);
        assertSame(FeedbackItem.ABSENT, feedbackItem114);
        assertSame(FeedbackItem.PRESENT, feedbackItem115);
        //---------------------------------------
        //Checking turn 3
        //---------------------------------------
        String hintTurnString12 = roundDTO.getTurnDTOS().get(2).getHint().getValue();
        String guessTurnString12 = roundDTO.getTurnDTOS().get(2).getGuess().getValue();
        Feedback feedbackTurn12 = roundDTO.getTurnDTOS().get(2).getFeedback();

        assertEquals("HAM..", hintTurnString12);
        assertEquals("HAMEL", guessTurnString12);

        List<FeedbackItem> feedbackItems12 = feedbackTurn12.getFeedbackItems();

        assertSame(5, feedbackItems12.size());

        FeedbackItem feedbackItem121 = feedbackItems12.get(0);
        FeedbackItem feedbackItem122 = feedbackItems12.get(1);
        FeedbackItem feedbackItem123 = feedbackItems12.get(2);
        FeedbackItem feedbackItem124 = feedbackItems12.get(3);
        FeedbackItem feedbackItem125 = feedbackItems12.get(4);

        assertSame(FeedbackItem.CORRECT, feedbackItem121);
        assertSame(FeedbackItem.CORRECT, feedbackItem122);
        assertSame(FeedbackItem.CORRECT, feedbackItem123);
        assertSame(FeedbackItem.CORRECT, feedbackItem124);
        assertSame(FeedbackItem.ABSENT, feedbackItem125);
        //---------------------------------------
        //Checking turn 4
        //---------------------------------------
        String hintTurnString13 = roundDTO.getTurnDTOS().get(3).getHint().getValue();
        String guessTurnString13 = roundDTO.getTurnDTOS().get(3).getGuess().getValue();
        Feedback feedbackTurn13 = roundDTO.getTurnDTOS().get(3).getFeedback();

        assertEquals("HAME.", hintTurnString13);
        assertEquals("RUFTI", guessTurnString13);

        List<FeedbackItem> feedbackItems13 = feedbackTurn13.getFeedbackItems();

        assertSame(5, feedbackItems13.size());

        FeedbackItem feedbackItem131 = feedbackItems13.get(0);
        FeedbackItem feedbackItem132 = feedbackItems13.get(1);
        FeedbackItem feedbackItem133 = feedbackItems13.get(2);
        FeedbackItem feedbackItem134 = feedbackItems13.get(3);
        FeedbackItem feedbackItem135 = feedbackItems13.get(4);

        assertSame(FeedbackItem.PRESENT, feedbackItem131);
        assertSame(FeedbackItem.ABSENT, feedbackItem132);
        assertSame(FeedbackItem.ABSENT, feedbackItem133);
        assertSame(FeedbackItem.ABSENT, feedbackItem134);
        assertSame(FeedbackItem.ABSENT, feedbackItem135);
        //---------------------------------------
        //Checking turn 5
        //---------------------------------------
        String hintTurnString14 = roundDTO.getTurnDTOS().get(4).getHint().getValue();
        String guessTurnString14 = roundDTO.getTurnDTOS().get(4).getGuess().getValue();
        Feedback feedbackTurn14 = roundDTO.getTurnDTOS().get(4).getFeedback();

        assertEquals("HAME.", hintTurnString14);
        assertEquals("GOVER", guessTurnString14);

        List<FeedbackItem> feedbackItems14 = feedbackTurn14.getFeedbackItems();

        assertSame(5, feedbackItems14.size());

        FeedbackItem feedbackItem141 = feedbackItems14.get(0);
        FeedbackItem feedbackItem142 = feedbackItems14.get(1);
        FeedbackItem feedbackItem143 = feedbackItems14.get(2);
        FeedbackItem feedbackItem144 = feedbackItems14.get(3);
        FeedbackItem feedbackItem145 = feedbackItems14.get(4);

        assertSame(FeedbackItem.ABSENT, feedbackItem141);
        assertSame(FeedbackItem.ABSENT, feedbackItem142);
        assertSame(FeedbackItem.ABSENT, feedbackItem143);
        assertSame(FeedbackItem.CORRECT, feedbackItem144);
        assertSame(FeedbackItem.CORRECT, feedbackItem145);
        //---------------------------------------
    }
}