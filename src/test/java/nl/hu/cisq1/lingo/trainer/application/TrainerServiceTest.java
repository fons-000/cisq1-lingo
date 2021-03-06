package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainerServiceTest {
    WordService wordService = mock(WordService.class);
    SpringGameRepository gameRepository = mock(SpringGameRepository.class);
    TrainerService service = new TrainerService(wordService, gameRepository);

    //Met mocken zorg je ervoor dat je er vanuit gaat dat dependencies werken zoals ze behoren, zodat
    //je puur alleen hoeft te richten op de omliggende functionaliteit in de methode.

    Person person = new Person("FS Fons", "8743", "Fons", Role.PLAYER);

    @AfterEach
    public void after() {
        person = new Person("FS Fons", "8743", "Fons", Role.PLAYER);
    }

    @Test
    @DisplayName("StartNewGame has the proper values")
    void startNewGameTest() {
        //We willen testen dat.
        //1. De score 100 is
        //2. De game 1 round heeft
        //3. De lengte van het eerste woord 5 is om de eerste ronde
        //4. Persoon heeft de game (getGames.size = 1)
        when(wordService.provideRandomWord(any())).thenReturn("PANDA");
        Game game = service.startNewGame(person);
        assertSame(100, game.getScore());
        assertSame(1, game.getRounds().size());
        assertSame(5, game.getRounds().iterator().next().getWord().getValue().length());
        assertSame("PANDA", game.getRounds().iterator().next().getWord().getValue());
    }

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
            assertEquals("P", turn.getHint().getValue());

            //Guess in de turn klopt
            assertEquals("PAIDLE", turn.getGuess().getValue());

            //Word in de turn klopt
            assertEquals("PUZZLE", turn.getWord().getValue());

            //De score van de game is hetzelfde gebleven (het woord is nog niet geraden)
            assertSame(100, game2.getScore());
        }

        else if(amountOfTurns == 12) {
            turn = turnsRound.get(0);

            //Hint in de turn klopt
            assertEquals("A", turn.getHint().getValue());

            //Guess in de turn klopt
            assertEquals("ALLES", turn.getGuess().getValue());

            //Word in de turn klopt
            assertEquals("ALLES", turn.getWord().getValue());

            //De score van de game is opgehoogd met 25 (in 1 turn geraden)
            assertSame(125, game2.getScore());
        }


        else if (amountOfTurns == 2) {
            turn = turnsRound.get(0);

            //Hint in de eerste turn klopt
            assertEquals("P", turn.getHint().getValue());

            //Guess in de eerste turn klopt
            assertEquals("PEANS", turn.getGuess().getValue());

            //Word in de eerste turn klopt
            assertEquals("PEACE", turn.getWord().getValue());

            turn = turnsRound.get(1);

            //Hint in de tweede turn klopt
            assertEquals("PEA..", turn.getHint().getValue());

            //Guess in de tweede turn klopt
            assertEquals("PACKS", turn.getGuess().getValue());

            //Word in de tweede turn klopt
            assertEquals("PEACE", turn.getWord().getValue());

            //De score van de game is hetzelfde gebleven (het woord is nog niet geraden)
            assertSame(100, game2.getScore());
        }
    }

    @ParameterizedTest
    @MethodSource("startNewRoundExampels")
    @DisplayName("startNewRound, will add a new round appropiately to it's previous round")
    void startNewRound(Game game, String expedtedWordNextRound, int expectedWordLengthNextRound, int expectedRounds) {
        when(wordService.provideRandomWord(5)).thenReturn("FUZZY");
        when(wordService.provideRandomWord(6)).thenReturn("BUZZER");
        when(wordService.provideRandomWord(7)).thenReturn("ACADEMY");

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
            assertSame(expedtedWordNextRound, gameRounds.get(gameRounds.size() - 1).getWord().getValue());
            assertSame(expectedRounds, gameRounds.size());
        }
    }

    @Test
    @DisplayName("getGameById")
    void getGameById() {
        Game game = new Game();
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        Game game1 = service.getGameById(1).orElseThrow();
        assertEquals(game, game1);
    }

    static Stream<Arguments> guessTestExamples() {
        Game game = new Game();
        Round round = new Round(new Word("PUZZLE"), game.getRounds().size() + 1);
        game.addRound(round);

        Game game1 = new Game();

        Game game2 = new Game();
        Round round2 = new Round(new Word("PEACE"), game2.getRounds().size() + 1);
        Turn turn2 = new Turn(round2.getFirstHint(), new Guess("PEANS"), round2.getWord());
        round2.addTurn(turn2);
        game2.addRound(round2);

        Game game3 = new Game();
        Round round3 = new Round(new Word("ALLES"), game.getRounds().size() + 1);
        game3.addRound(round3);

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
                Arguments.of(game3, new Guess("ALLES"), 12)
        );
    }

    static Stream<Arguments> startNewRoundExampels() {
        //5 letter word next round
        Game game = new Game();

        //5 letter word next round
        Game game1 = new Game();
        Round round1 = new Round(new Word("JAZZING"), game1.getRounds().size() + 1);
        Turn turn1 = new Turn(round1.getFirstHint(), new Guess("JAZZING"), round1.getWord());
        round1.addTurn(turn1);
        game1.addRound(round1);

        //6 letter next round
        Game game2 = new Game();
        Round round2 = new Round(new Word("DIZZY"), game2.getRounds().size() + 1);
        Turn turn2 = new Turn(round2.getFirstHint(), new Guess("DIZZY"), round2.getWord());
        round2.addTurn(turn2);
        game2.addRound(round2);

        //7 letter next round
        Game game3 = new Game();
        Round round3 = new Round(new Word("FUZZES"), game3.getRounds().size() + 1);
        Turn turn3 = new Turn(round3.getFirstHint(), new Guess("FUZZES"), round3.getWord());
        round3.addTurn(turn3);
        game3.addRound(round3);

        //Optional.empty
        Game game4 = new Game();
        Round round4 = new Round(new Word("WHIZZY"), game4.getRounds().size() + 1);
        Turn turn4 = new Turn(round4.getFirstHint(), new Guess("WADDLE"), round4.getWord());
        round4.addTurn(turn4);
        game4.addRound(round4);

        return Stream.of(
                //1. Er zijn geen rounds = 5 letter word next round.
                Arguments.of(game, "FUZZY", 5, 1),
                //2. Er zijn al rounds, het woord is al geraden in de laatste turn van de round
                //en de vorige round woordlengte was 7 = 5 letter woord next round.
                Arguments.of(game1, "FUZZY", 5, 2),
                //3. Er zijn al rounds, het woord is al geraden in de laatste turn van de round en de vorige round woordlengte was 5 = 6 letter woord next round.
                Arguments.of(game2, "BUZZER", 6, 2),
                //4. Er zijn al rounds, het woord is al geraden in de laatste turn van de round en de vorige round woordlengte was 6 = 7 letter woord next round.
                Arguments.of(game3, "ACADEMY", 7, 2),
                //5. Er zijn al rounds en het woord is nog niet geraden = Optional.empty
                Arguments.of(game4, "", 0, 0)
        );
    }
}