package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.hibernate.property.access.spi.BuiltInPropertyAccessStrategies;
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
import static org.mockito.ArgumentMatchers.*;

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
    @MethodSource("gameExamples")
    @DisplayName("guessTest, will add a valid Turn to the Round of the game if the round isn't over")
    void guess(Game game, Guess guess, int amountOfTurns) {
        Optional<Game> optionalGame = service.guess(game, guess);
        Game game2;
        List<Round> gameRounds;
        List<Turn> turnsRound = null;
        Turn turn;

        if(amountOfTurns == 0 || amountOfTurns == 20) {
            assertThrows(NoSuchElementException.class, () -> {
                Game game1 = optionalGame.orElseThrow();
            });
        }

        if(amountOfTurns == 1 || amountOfTurns == 2) {
            game2 = optionalGame.orElseThrow();
            gameRounds = new ArrayList<>(game2.getRounds());
            assertSame(1, gameRounds.size());
            turnsRound = new ArrayList<>(gameRounds.get(0).getTurns());

            //amountOfTurns in de round klopt
            assertSame(amountOfTurns, turnsRound.size());
        }

        if(amountOfTurns == 1) {
            turn = turnsRound.get(0);

            //Hint in de turn klopt
            assertEquals("P", turn.getHint().getValue());

            //Guess in de turn klopt
            assertEquals("PAIDLE", turn.getGuess().getValue());

            //Word in de turn klopt
            assertEquals("PUZZLE", turn.getWord().getValue());
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
        }
    }

    static Stream<Arguments> gameExamples() {
        Game game = new Game();
        Round round = new Round(new Word("PUZZLE"), game.getRounds().size() + 1);
        game.addRound(round);

        Game game1 = new Game();

        Game game2 = new Game();
        Round round2 = new Round(new Word("PEACE"), game2.getRounds().size() + 1);
        Turn turn2 = new Turn(round2.getFirstHint(), new Guess("PEANS"), round2.getWord());
        round2.addTurn(turn2);
        game2.addRound(round2);

        return Stream.of(
                //1. Er zijn  voorgaande rounds, en er zitten nog geen turns in de laatste ronde.
                //= Game object met nog toegevoegde turn aan laatste round met de juiste values
                Arguments.of(game, new Guess("PAIDLE"), 1),
                //2. Er zijn geen voorgaande rounds, en er zitten nog geen turns in.
                //= Optional.empty
                Arguments.of(game1, new Guess("PAIDLE"), 0),
                //3. Er zijn voorgaande rounds, en er zitten turns in de laatste ronde.
                //= Game object met nog toegevoegde turn aan laatste round met de juiste values De word van de turn is goed.
                Arguments.of(game2, new Guess("PACKS"), 2)
        );
    }
}