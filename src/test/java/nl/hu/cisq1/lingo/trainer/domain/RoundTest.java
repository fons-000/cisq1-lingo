package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundTest {
    //Round
    //1. Creating round works with the right properties after add
    //2. getFirstHint works (this is tested in in the test case for 1.)
    //3. Turns cannot be added when
    //  3.1 - 5 Turns are already in the round.
    //  3.2 - The word has been guessed!


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
        //Check if getFirstHint works
        assertSame(1, round.getFirstHint().getValue().length());
        assertSame(word.getValue().charAt(0), round.getFirstHint().getValue().charAt(0));
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
        assertSame(5, round.getTurns().size());
        //---------------------------------------
        //Kijk of de waarde van de turn goed in de Round verwerkt is.
        //Doe dit vervolgens ook voor meerdere turns en check of de gegevens correct zijn + het niet 5 turns overscheidt.
        //---------------------------------------
        //Checking turn 1
        //---------------------------------------
        String wordTurnString = round.getTurns().get(0).getWord().getValue();
        String hintTurnString = round.getTurns().get(0).getHint().getValue();
        String guessTurnString = round.getTurns().get(0).getGuess().getValue();
        Feedback feedbackTurn = round.getTurns().get(0).getFeedback();

        assertEquals("HAMER", wordTurnString);
        assertEquals("HAM..", hintTurnString);
        assertEquals("HAMSTER", guessTurnString);

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
        //---------------------------------------
        //Checking turn 2
        //---------------------------------------
        String wordTurnString11 = round.getTurns().get(1).getWord().getValue();
        String hintTurnString11 = round.getTurns().get(1).getHint().getValue();
        String guessTurnString11 = round.getTurns().get(1).getGuess().getValue();
        Feedback feedbackTurn11 = round.getTurns().get(1).getFeedback();

        assertEquals("HAMER", wordTurnString11);
        assertEquals("HAM..", hintTurnString11);
        assertEquals("HARDE", guessTurnString11);

        ArrayList<FeedbackItem> feedbackItems11 = feedbackTurn11.getFeedbackItems();

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
        String wordTurnString12 = round.getTurns().get(2).getWord().getValue();
        String hintTurnString12 = round.getTurns().get(2).getHint().getValue();
        String guessTurnString12 = round.getTurns().get(2).getGuess().getValue();
        Feedback feedbackTurn12 = round.getTurns().get(2).getFeedback();

        assertEquals("HAMER", wordTurnString12);
        assertEquals("HAM..", hintTurnString12);
        assertEquals("HAMEL", guessTurnString12);

        ArrayList<FeedbackItem> feedbackItems12 = feedbackTurn12.getFeedbackItems();

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
        String wordTurnString13 = round.getTurns().get(3).getWord().getValue();
        String hintTurnString13 = round.getTurns().get(3).getHint().getValue();
        String guessTurnString13 = round.getTurns().get(3).getGuess().getValue();
        Feedback feedbackTurn13 = round.getTurns().get(3).getFeedback();

        assertEquals("HAMER", wordTurnString13);
        assertEquals("HAME.", hintTurnString13);
        assertEquals("RUFTI", guessTurnString13);

        ArrayList<FeedbackItem> feedbackItems13 = feedbackTurn13.getFeedbackItems();

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
        String wordTurnString14 = round.getTurns().get(4).getWord().getValue();
        String hintTurnString14 = round.getTurns().get(4).getHint().getValue();
        String guessTurnString14 = round.getTurns().get(4).getGuess().getValue();
        Feedback feedbackTurn14 = round.getTurns().get(4).getFeedback();

        assertEquals("HAMER", wordTurnString14);
        assertEquals("HAME.", hintTurnString14);
        assertEquals("GOVER", guessTurnString14);

        ArrayList<FeedbackItem> feedbackItems14 = feedbackTurn14.getFeedbackItems();

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

    @Test
    @DisplayName("Cannot add another turn if the word has been guessed")
    void noNewRoundWordGuessed() {
        //roundOfGame maakt nog niet zoveel uit, dit wordt later opgevangen in de service.
        //Dit kan je voor nu even negeren. Wordt ook opgevangen in de GameTest
        Word word = Word.createValidWord("AHAHA");
        Word hint = Hint.createValidHint("AH...");
        Word guess = Guess.createValidGuess("HAHAH");
        Round round = new Round(word, 2);

        Turn turn = new Turn(hint, guess, word);
        Feedback feedback = turn.returnFeedbackCurrentTurn();
        turn.setFeedback(feedback);
        //De turn kan toegevoegd worden, geldig Word + past nog in list + nog niet gegokt
        assertSame(true, round.addTurn(turn));

        Word hint2 = turn.returnHintForNextTurn();
        Word guess2 = Guess.createValidGuess("AHAHH");

        Turn turn2 = new Turn(hint2, guess2, word);
        Feedback feedback2 = turn2.returnFeedbackCurrentTurn();
        turn2.setFeedback(feedback2);
        //De turn kan toegevoegd worden, geldig Word + past nog in list + nog niet gegokt
        assertSame(true, round.addTurn(turn2));

        Word hint3 = turn2.returnHintForNextTurn();
        Word guess3 = Guess.createValidGuess("AHAHA");

        Turn turn3 = new Turn(hint3, guess3, word);
        Feedback feedback3 = turn3.returnFeedbackCurrentTurn();
        turn3.setFeedback(feedback3);
        assertSame(true, round.addTurn(turn3));

        //Er kan geen geldige hint maar aangemaakt worden voor de volgende turn, omdat het woord al geraden is!
        //Dit betekent dat de turn niet toegevoegd kan worden, omdat het generen van de hint vastloopt!
        assertThrows(NoSuchElementException.class, () -> {
            Word hint4 = turn3.returnHintForNextTurn();
        });
    }

    @ParameterizedTest
    @MethodSource("turnExamples")
    @DisplayName("addTurnConditionsTest")
    void addTurn(Hint hint, Guess previousGuess, Word word, int turnAmount, boolean result) {
        //After looking at the previous guess and if it was the same as Word and if there already is it least one turn:
        //The following test cases will be checked (see turnExamples)
        Word word1 = new Word("GUESSING");
        Turn turn = new Turn(hint, previousGuess, word);

        //Setting up working (good) turn variables.
        //Guess2 makes sure that there are no previous guesses from a turn that are the same as the word of a round
        Guess guess2 = new Guess("GUES");
        Hint hint2 = new Hint("G");
        Word word2 = new Word("GUESSING");
        Turn turn2 = new Turn(hint2, guess2, word2);

        //roundOfGame maakt nog niet zoveel uit, dit wordt later opgevangen in de service.
        //Dit kan je voor nu even negeren. Wordt ook opgevangen in de GameTest
        //Hier wordt ten minste 1, zelfs meer turns gezet.
        Round round = new Round(word1, 25);
        for(int i = 0; i < (turnAmount - 1); i++) {
            round.addTurn(turn2);
        }

        //This is the previous turn, klopt want previousGuess is variabel en opgegeven.
        Turn turn3 = new Turn(hint2, previousGuess, word1);
        //De laatst vorige turn heeft nu een guess die in de helft van de gevallen juist of onjuist is.
        round.addTurn(turn3);

        //Alle drie de condities worden nu afgevangen, de guess van de huidige turn komt niet voor in de branche van de addTurn van round.
        //Dit zou nu moeten werken.
        assertSame(turnAmount, round.getTurns().size());
        assertSame(result, round.addTurn(turn));
    }

    static Stream<Arguments> turnExamples() {
        Word word = new Word("GUESSING");
        Word word1 = new Word("WRONG");
        //De hint maakt voor nu even niet zoveel uit.
        Hint hint = new Hint("G");
        Guess previousGuess = new Guess("GUESSING");
        Guess previousGuess1 = new Guess("GUESWRONG");
        int turnAmount = 4;
        int turnAmount1 = 5;

        return Stream.of(
                //1. Guess van de vorige turn is gelijk aan het word van de round, er zijn minder dan 5 turns, round word is hetzelfde als turn word. false
                Arguments.of(hint, previousGuess, word, turnAmount, false),
                //2. Guess van de vorige turn is gelijk aan het word van de round, er zijn minder dan 5 turns, round word is ongelijk als turn word. false
                Arguments.of(hint, previousGuess, word1, turnAmount, false),
                //3. Guess van de vorige turn is gelijk aan het word van de round, er zijn meer dan 5 turns, round word is hetzelfde als turn word. false
                Arguments.of(hint, previousGuess, word, turnAmount1, false),
                //4. Guess van de vorige turn is gelijk aan het word van de round, er zijn meer dan 5 turns, round word is ongelijk als turn word. false
                Arguments.of(hint, previousGuess, word1, turnAmount1, false),
                //5. Guess van de vorige turn is ongelijk aan het word van de round, er zijn minder dan 5 turns, round word is hetzelfde als turn word. true
                Arguments.of(hint, previousGuess1, word, turnAmount, true),
                //6. Guess van de vorige turn is ongelijk aan het word van de round, er zijn minder dan 5 turns, round word is ongelijk als turn word. false
                Arguments.of(hint, previousGuess1, word1, turnAmount, false),
                //7. Guess van de vorige turn is ongelijk aan het word van de round, er zijn meer dan 5 turns, round word is hetzelfde als turn word. false
                Arguments.of(hint, previousGuess1, word, turnAmount1, false),
                //8. Guess van de vorige turn is ongelijk aan het word van de round, er zijn meer dan 5 turns, round word is ongelijk als turn word. false
                Arguments.of(hint, previousGuess1, word1, turnAmount1, false)
        );
    }
}