package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Sql({"/data/lingo_words.sql"})
@Import(CiTestConfiguration.class)
public class TrainerServiceIntegrationTest {
    @Autowired
    private TrainerService service;

    @Autowired
    private SpringGameRepository springGameRepository;

    Person person = new Person("Yoloman", "9104", "Brandon", Role.PLAYER);

    @AfterEach
    public void after() {
        System.out.println("It comes in the after each!");
        person = new Person("Yoloman", "9104", "Brandon", Role.PLAYER);
    }

    @Test
    @DisplayName("StartNewGame has the proper values")
    void startNewGameTest() {
        //Everything should be removed and counter should be 5

        //We willen testen dat.
        //1. De score 100 is
        //2. De game 1 round heeft
        //3. De lengte van het eerste woord 5 is om de eerste ronde
        //4. Persoon heeft de game (getGames.size = 1)
        Game game = service.startNewGame(person);
        assertSame(100, game.getScore());
        assertSame(1, game.getRounds().size());
        assertSame(5, game.getRounds().iterator().next().getWord().getValue().length());
//        Game dbGame = springGameRepository.findById(5).orElseThrow();
        Game dbGame = service.getGameById(5).orElseThrow();
        assertEquals(game, dbGame);
    }

    //Je zou eigenlijk binnen de parameterizedTest willen, dat values geschoond worden.
    @ParameterizedTest
    @MethodSource("guessTestExamples")
    @DisplayName("guessTest, will add a valid Turn to the Round of the game if the round isn't over")
    void guess(Game game, Guess guess, int amountOfTurns) {
        Optional<Game> optionalGame = service.guess(game, guess);
        Game game2 = null;
        List<Round> gameRounds;
        List<Turn> turnsRound = null;
        Turn turn;

        if(amountOfTurns == 0) {
            assertThrows(NoSuchElementException.class, () -> {
                Game game1 = optionalGame.orElseThrow();
            });
        }

        if(amountOfTurns == 1 || amountOfTurns == 2 || amountOfTurns == 12) {
            game2 = optionalGame.orElseThrow();
            gameRounds = new ArrayList<>(game2.getRounds());
            assertSame(1, gameRounds.size());
            turnsRound = new ArrayList<>(gameRounds.get(0).getTurns());

            //amountOfTurns in de round klopt
            if(amountOfTurns == 1 || amountOfTurns == 2) {
                assertSame(amountOfTurns, turnsRound.size());
            }
            else {
                assertSame(1, turnsRound.size());
            }
        }

        if(amountOfTurns == 1) {
            turn = turnsRound.get(0);

            //Hint in de turn klopt
            assertEquals("A", turn.getHint().getValue());

            //Guess in de turn klopt
            assertEquals("PAIDLE", turn.getGuess().getValue());

            //Word in de turn klopt
            assertEquals("AALBES", turn.getWord().getValue());

            //De score van de game is hetzelfde gebleven (het woord is nog niet geraden)
            assertSame(100, game2.getScore());
//            Game dbGame = springGameRepository.findById(game2.getId()).orElseThrow();
            Game dbGame = service.getGameById(game2.getId()).orElseThrow();
            //Game uit de service vergelijken met de game in de DB
            //(kijken of de game in de service daadwerkelijk is opgeslagen!)
            assertEquals(game2, dbGame);
        }

        else if(amountOfTurns == 12) {
            turn = turnsRound.get(0);

            //Hint in de turn klopt
            assertEquals("E", turn.getHint().getValue());

            //Guess in de turn klopt
            assertEquals("ECHOPUT", turn.getGuess().getValue());

            //Word in de turn klopt
            assertEquals("ECHOPUT", turn.getWord().getValue());

            //De score van de game is opgehoogd met 25 (in 1 turn geraden)
            assertSame(125, game2.getScore());
//            Game dbGame = springGameRepository.findById(game2.getId()).orElseThrow();
            Game dbGame = service.getGameById(game2.getId()).orElseThrow();
            //Game uit de service vergelijken met de game in de DB
            //(kijken of de game in de service daadwerkelijk is opgeslagen!)
            assertEquals(game2, dbGame);
        }


        else if (amountOfTurns == 2) {
            turn = turnsRound.get(0);

            //Hint in de eerste turn klopt
            assertEquals("C", turn.getHint().getValue());

            //Guess in de eerste turn klopt
            assertEquals("PEANS", turn.getGuess().getValue());

            //Word in de eerste turn klopt
            assertEquals("CHARGE", turn.getWord().getValue());

            turn = turnsRound.get(1);

            //Hint in de tweede turn klopt
            assertEquals("C.....", turn.getHint().getValue());

            //Guess in de tweede turn klopt
            assertEquals("PACKS", turn.getGuess().getValue());

            //Word in de tweede turn klopt
            assertEquals("CHARGE", turn.getWord().getValue());

            //De score van de game is hetzelfde gebleven (het woord is nog niet geraden)
            assertSame(100, game2.getScore());
//            Game dbGame = springGameRepository.findById(game2.getId()).orElseThrow();
            Game dbGame = service.getGameById(game2.getId()).orElseThrow();
            //Game uit de service vergelijken met de game in de DB
            //(kijken of de game in de service daadwerkelijk is opgeslagen!)
            //Maar er zitten meerdere turns in, dus eerst sorten voor een goede vergelijking!
            //sortTurns
            List<Round> dbRounds = new ArrayList<>(dbGame.getRounds());
            List<Turn> dbTurns = new ArrayList<>(dbRounds.get(0).getTurns());
            Collections.sort(dbTurns);
            dbGame.getRounds().iterator().next().setTurns(dbTurns);
            assertEquals(game2, dbGame);
        }
    }

    @ParameterizedTest
    @MethodSource("startNewRoundExampels")
    @DisplayName("startNewRound, will add a new round appropiately to it's previous round")
    void startNewRound(Game game, int expectedWordLengthNextRound, int expectedRounds) {
        System.out.println("This is the game that doesn't want to start a new round: ");
        System.out.println(game.showGame());
        Optional<Game> optionalGame = service.startNewRound(game);
        if(expectedWordLengthNextRound == 0) {
            assertThrows(NoSuchElementException.class, () -> {
                Game game1 = optionalGame.orElseThrow();
            });
        }
        else {
            Game game1 = optionalGame.orElseThrow();
            List<Round> gameRounds = new ArrayList<>(game1.getRounds());
            assertSame(expectedWordLengthNextRound, gameRounds.get(gameRounds.size() - 1).getWord().getLength());
            assertSame(expectedRounds, gameRounds.size());

            Game dbGame = service.getGameById(game1.getId()).orElseThrow();
//            Game dbGame = springGameRepository.findById(game1.getId()).orElseThrow();

            List<Round> dbRounds = new ArrayList<>(dbGame.getRounds());
            Collections.sort(dbRounds);
            dbGame.setRounds(dbRounds);
            assertEquals(game1, dbGame);
        }
    }

    @Test
    @DisplayName("getGameById")
    void getGameById() {
        Game game = new Game();
        Round round1 = new Round(new Word("NAJAAR"), 3);
        Round round2 = new Round(new Word("AAIBAAR"), 2);
        Round round3 = new Round(new Word("AAGJE"), 1);
        game.addRound(round1);
        game.addRound(round2);
        game.addRound(round3);
        Game game1 = service.getGameById(1).orElseThrow();
        assertEquals(game, game1);
    }

    static Stream<Arguments> guessTestExamples() {
        Person person = new Person("turnExampleUser", "kjsnrgtjn", "Fons", Role.HEAD_ADMINISTRATOR);
        Person person1 = new Person("turnExampleUser1", "90275", "Zion", Role.HEAD_ADMINISTRATOR);
        Person person2 = new Person("turnExampleUser2", "fiwuegy8", "Ryan", Role.PLAYER);
        Person person3 = new Person("turnExampleUser3", "w8rgh8wyr", "Carly", Role.ADMINISTRATOR);

        Game game = new Game();
        Round round = new Round(new Word("AALBES"), game.getRounds().size() + 1);
        round.setGame(game);
        game.addRound(round);
        game.setPerson(person);
        person.addGame(game);
        System.out.println("Workings test game: ");
        System.out.println(game.showGame());

        Game game1 = new Game();
        game1.setPerson(person1);
        person1.addGame(game1);
        System.out.println("Workings test game: ");
        System.out.println(game1.showGame());

        Game game2 = new Game();
        Round round2 = new Round(new Word("CHARGE"), game2.getRounds().size() + 1);
        Turn turn2 = new Turn(round2.getFirstHint(), new Guess("PEANS"), round2.getWord());

        round2.addTurn(turn2);
        turn2.setRound(round2);

        round2.setGame(game2);
        game2.addRound(round2);

        game2.setPerson(person2);
        person2.addGame(game2);

        System.out.println("Failing test game: ");
        System.out.println(game2.showGame());

        Game game3 = new Game();
        Round round3 = new Round(new Word("ECHOPUT"), game.getRounds().size() + 1);
        round3.setGame(game3);
        game3.addRound(round3);
        game3.setPerson(person3);
        person3.addGame(game3);
        System.out.println("Failing test game: ");
        System.out.println(game3.showGame());

        return Stream.of(
                //1. Er is een voorgaande ronde/rondes, en er zitten nog geen turns in de laatste ronde.
                //Het woord wordt fout gegokt.
                //= Game object met nog toegevoegde turn aan laatste round met de juiste values
                Arguments.of(game, new Guess("PAIDLE"), 1),
                //2. Er is geen voorgaande ronde/rondes, en er zitten nog geen turns in.
                //Het woord wordt fout gegokt
                //= Optional.empty
                Arguments.of(game1, new Guess("PAIDLE"), 0),
                //3. Er is een voorgaande ronde/rondes, en er zitten turns in de laatste ronde.
                //Het woord wordt fout gegokt
                //= Game object met nog toegevoegde turn aan laatste round met de juiste values De word van de turn is goed.
                Arguments.of(game2, new Guess("PACKS"), 2),
                //1. Er is een voorgaande ronde/rondes, en er zitten nog geen turns in de laatste ronde.
                //Het woord wordt goed gegokt.
                //= Game object met nog toegevoegde turn aan laatste round met de juiste values
                //12 staat in ons geval voor 1.2 (tweede test met 1 turn)
                Arguments.of(game3, new Guess("ECHOPUT"), 12)
        );
    }

    static Stream<Arguments> startNewRoundExampels() {
        Person person = new Person("roundExampleUser", "784ty84h", "Michiel", Role.HEAD_ADMINISTRATOR);
        Person person1 = new Person("roundExampleUser1", "98pw4gh", "Allison", Role.HEAD_ADMINISTRATOR);
        Person person2 = new Person("roundExampleUser2", "784gwhr", "Dann", Role.PLAYER);
        Person person3 = new Person("roundExampleUser3", "094jfwh8r", "Jane", Role.ADMINISTRATOR);
        Person person4 = new Person("roundExampleUser3", "wezels94Ko", "Rick", Role.PLAYER);

        //5 letter word next round
        Game game = new Game();
        game.setPerson(person);
        person.addGame(game);

        //5 letter word next round
        Game game1 = new Game();
        Round round1 = new Round(new Word("BAMIBAL"), game1.getRounds().size() + 1);
        Turn turn1 = new Turn(round1.getFirstHint(), new Guess("BAMIBAL"), round1.getWord());
        turn1.setRound(round1);
        round1.addTurn(turn1);
        round1.setGame(game1);
        game1.addRound(round1);
        game1.setPerson(person1);
        person1.addGame(game1);

        //6 letter next round
        Game game2 = new Game();
        Round round2 = new Round(new Word("BELEN"), game2.getRounds().size() + 1);
        Turn turn2 = new Turn(round2.getFirstHint(), new Guess("BELEN"), round2.getWord());
        turn2.setRound(round2);
        round2.addTurn(turn2);
        round2.setGame(game2);
        game2.addRound(round2);
        game2.setPerson(person2);
        person2.addGame(game2);

        //7 letter next round
        Game game3 = new Game();
        Round round3 = new Round(new Word("BEWIND"), game3.getRounds().size() + 1);
        Turn turn3 = new Turn(round3.getFirstHint(), new Guess("BEWIND"), round3.getWord());
        turn3.setRound(round3);
        round3.addTurn(turn3);
        round3.setGame(game3);
        game3.addRound(round3);
        game3.setPerson(person3);
        person3.addGame(game3);

        //Optional.empty
        Game game4 = new Game();
        Round round4 = new Round(new Word("BROWSER"), game4.getRounds().size() + 1);
        Turn turn4 = new Turn(round4.getFirstHint(), new Guess("BIKKEL"), round4.getWord());
        turn4.setRound(round4);
        round4.addTurn(turn4);
        round4.setGame(game4);
        game4.addRound(round4);
        game4.setPerson(person4);
        person4.addGame(game4);

//        System.out.println("This is game: ");
//        System.out.println(game.showGame());
//
//        System.out.println("This is game 1: ");
//        System.out.println(game1.showGame());
//
//        System.out.println("This is game 2: ");
//        System.out.println(game2.showGame());
//
//        System.out.println("This is game 3: ");
//        System.out.println(game3.showGame());

        return Stream.of(
                //1. Er zijn geen rounds = 5 letter word next round.
                Arguments.of(game, 5, 1),
                //2. Er zijn al rounds, het woord is al geraden in de laatste turn van de round
                //en de vorige round woordlengte was 7 = 5 letter woord next round.
                Arguments.of(game1, 5, 2),
                //3. Er zijn al rounds, het woord is al geraden in de laatste turn van de round en de vorige round woordlengte was 5 = 6 letter woord next round.
                Arguments.of(game2, 6, 2),
                //4. Er zijn al rounds, het woord is al geraden in de laatste turn van de round en de vorige round woordlengte was 6 = 7 letter woord next round.
                Arguments.of(game3, 7, 2),
                //5. Er zijn al rounds en het woord is nog niet geraden = Optional.empty
                Arguments.of(game4, 0, 0)
        );
    }
}