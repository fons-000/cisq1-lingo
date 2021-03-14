package nl.hu.cisq1.lingo.trainer.application;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainerService {
    private final WordService wordService;
    private final SpringGameRepository springGameRepository;

    public Game startNewGame(Person person) {
        //1. Get a random 5 letter word.
        Word word = new Word(this.wordService.provideRandomWord(5));

        //2. Create a new game and add the game to the person!
        Game game = new Game();
        person.addGame(game);

        //3. Add the first round to the game with it's initial values
        Round round = new Round(word, game.getRounds().size() + 1);
        game.addRound(round);

        return game;
    }

    public Optional<Game> guess(Game game, Guess guess) {
        //Kijkt of addTurn werkt voor de laatste ronde.
        //Indien deze niet werkt, betekent dat een nieuwe ronde gestart moet worden.
        //Zie use cases addTurn in de Round class,
        //dit betekent dat het een foutmelding moet geven (woord is al geraden, er is al 5 keer gegokt of de game bevat nog geen rounds), maak een nieuwe round aan.

        //1. Maak eerst een turn aan, die je later probeert toe te voegen.
        //1.1 Er zijn nog geen turns gemaakt in de laatst aangemaakte round (dit zou alleen voor de eerste round moeten gebeuren).
        Turn turn;

        if(game.getRounds().size() > 0) {
            ArrayList<Round> rounds = new ArrayList<>(game.getRounds());
            Round lastRound = rounds.get(rounds.size() - 1);
            if(lastRound.getTurns().size() == 0) {
                //Kijk naar de firstHint attribute van de round voor de Hint van de turn.
                //Kijk naar de Word attribuut van Round voor de word
                //Zet de meegekregen guess als guess.
                turn = new Turn(lastRound.getFirstHint(), guess ,lastRound.getWord());
            }

            else {
                //1.2 Er zijn wel turns gemaakt in de laatste round het spel.
                //Kijk wat er uit de returnHint voor next turn komt van de vorige turn en gebruik deze hint.
                //Kijk naar de Word attribuut van Round voor de word.
                //Zet de meegegeven guess als guess.
                ArrayList<Turn> turns = new ArrayList<>(lastRound.getTurns());
                Turn lastTurn = turns.get(turns.size() - 1);
                //Set the feedback, so we can generate a new hint for the next turn.
                Feedback feedback = lastTurn.returnFeedbackCurrentTurn();
                lastTurn.setFeedback(feedback);
                turn = new Turn(lastTurn.returnHintForNextTurn(), guess, lastRound.getWord());
            }
            //2. Kijk nu of je de Turn toe kan voegen aan de laatste ronde van het spel, dit kan namelijk zo foutgaan.

            lastRound.addTurn(turn);
            //1. Maak van rounds weer een set
            //2. Set de set als rounds in de game
            //3. Retourneer de game
            Set set = new LinkedHashSet<>(rounds);
            game.setRounds(set);
            return Optional.of(game);
        }
        return Optional.empty();
    }
}