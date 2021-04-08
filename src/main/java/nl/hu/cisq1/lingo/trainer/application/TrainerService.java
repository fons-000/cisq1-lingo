package nl.hu.cisq1.lingo.trainer.application;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.*;

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
        game.setPerson(person);
        person.addGame(game);

        //3. Add the first round to the game with it's initial values
        Round round = new Round(word, game.getRounds().size() + 1);
        round.setGame(game);
        game.addRound(round);

        springGameRepository.save(game);
        return game;
    }

    public Optional<Game> guess(Game game, Guess guess) {
        System.out.println("Komt in de guess functie!");
        //Kijkt of addTurn werkt voor de laatste ronde.
        //Indien deze niet werkt, betekent dat een nieuwe ronde gestart moet worden.
        //Zie use cases addTurn in de Round class,
        //dit betekent dat het een foutmelding moet geven (woord is al geraden, er is al 5 keer gegokt of de game bevat nog geen rounds), maak een nieuwe round aan.

        //1. Maak eerst een turn aan, die je later probeert toe te voegen.
        //1.1 Er zijn nog geen turns gemaakt in de laatst aangemaakte round (dit zou alleen voor de eerste round moeten gebeuren).
        Turn turn;

        if(game.getRounds().size() > 0) {
            System.out.println("Komt in de game.getRounds > 0!");
            ArrayList<Round> rounds = new ArrayList<>(game.getRounds());
            System.out.println("These are the rounds before the sort: ");
            System.out.println(rounds);
            Collections.sort(rounds);
            System.out.println("These are the rounds after the sort: ");
            System.out.println(rounds);
            Round lastRound = rounds.get(rounds.size() - 1);
            lastRound.setWord(Word.createValidWord(lastRound.getWordValue()));
            Word firstHint = Hint.createValidHint(String.valueOf(lastRound.getWordValue().charAt(0)));
            lastRound.setFirstHint(firstHint);
            if(lastRound.getTurns().size() == 0) {
                System.out.println("Komt in de game.getTurns == 0!");
                //Kijk naar de firstHint attribute van de round voor de Hint van de turn.
                //Kijk naar de Word attribuut van Round voor de word
                //Zet de meegekregen guess als guess.
                turn = new Turn(lastRound.getFirstHint(), guess ,lastRound.getWord());
                System.out.println("Gaat nu naar de returnFeedbackCurrentTurn in TrainerService guess! if getRounds size = 0");

//                turn.setFeedback(turn.returnFeedbackCurrentTurn());
            }

            else {
                System.out.println("Komt in de game.getTurns > 0 (else)!");
                //1.2 Er zijn wel turns gemaakt in de laatste round het spel.
                //Kijk wat er uit de returnHint voor next turn komt van de vorige turn en gebruik deze hint.
                //Kijk naar de Word attribuut van Round voor de word.
                //Zet de meegegeven guess als guess.
                ArrayList<Turn> turns = new ArrayList<>(lastRound.getTurns());
                Collections.sort(turns);
                Turn lastTurn = turns.get(turns.size() - 1);
                lastTurn.setRound(lastRound);
                //Set the feedback, so we can generate a new hint for the next turn.
                System.out.println("Gaat nu naar de returnFeedbackCurrentTurn in TrainerService guess! else");
                Feedback feedback = lastTurn.returnFeedbackCurrentTurn();
                lastTurn.setFeedback(feedback);
                Word hint = Hint.createValidHint(lastTurn.getHintString());
                lastTurn.setHint(hint);
                turn = new Turn(lastTurn.returnHintForNextTurn(), guess, lastRound.getWord());
            }
            System.out.println("Komt hier, maar op 1-of-andere manier gaat het fout nu");
            //2. Kijk nu of je de Turn toe kan voegen aan de laatste ronde van het spel, dit kan namelijk zo foutgaan.
            turn.setTurnRound(lastRound.getTurns().size() + 1);
            turn.setRound(lastRound);
            lastRound.addTurn(turn);
            turn.setFeedback(turn.returnFeedbackCurrentTurn());
            System.out.println("This is now the round after the turn got added: ");
            System.out.println(lastRound);
            //1. Hoog eventueel de score op
            //2. Maak van rounds weer een set
            //3. Set de set als rounds in de game
            //4. Retourneer de game

            ArrayList<Turn> lastRoundTurns = new ArrayList<>(lastRound.getTurns());
            Collections.sort(lastRoundTurns);
            lastRound.setTurns(lastRoundTurns);
            System.out.println("This is now the round after a second sort: ");
            System.out.println(lastRound);

            //Als het woord goed wordt geraden, van de huidige turn!
            System.out.println("This is the guess of the turn of te lastRound: ");
            System.out.println(lastRoundTurns.get(lastRoundTurns.size() - 1).getGuess());
            System.out.println("This is the word of the lastRound: ");
            System.out.println(lastRound.getWord());
            System.out.println("It comes in the if-statement to make the score higher: ");
            System.out.println(lastRoundTurns.get(lastRoundTurns.size() - 1).getGuess().equals(lastRound.getWord()));
            if(lastRoundTurns.get(lastRoundTurns.size() - 1).getGuess().equals(lastRound.getWord())) {
                int increment = 5 * (5 - lastRoundTurns.size()) + 5;
                game.setScore(game.getScore() + increment);
            }

            Set set = new LinkedHashSet<>(rounds);
            game.setRounds(set);
            springGameRepository.save(game);
            return Optional.of(game);
        }
        System.out.println("Retourneerd Optional.empty!");
        return Optional.empty();
    }

    public Optional<Game> startNewRound(Game game) {
        Word word;
        if(game.getRounds().size() > 0) {
            System.out.println("Komt in de game.getRounds > 0, zou hier in moeten komen");
            List<Round> roundsGame = new ArrayList<>(game.getRounds());
            Collections.sort(roundsGame);
            List<Turn> turnsLastRound = new ArrayList<>(roundsGame.get(roundsGame.size() - 1).getTurns());
            Collections.sort(turnsLastRound);

            //Kijk of het woord al geraden is.
            Word lastRoundWord = roundsGame.get(roundsGame.size() - 1).getWord();
            System.out.println("This is the lastRoundWord in the startNewRound function: ");
            System.out.println(lastRoundWord);
            Word lastTurnGuess = turnsLastRound.get(turnsLastRound.size() - 1).getGuess();
            System.out.println("This is the lastTurnGuess in the startNewRound function: ");
            System.out.println(lastTurnGuess);

            if(lastRoundWord.equals(lastTurnGuess)) {
                System.out.println("Zou ook hier in moeten komen, in de laatste ronde is het woord geraden.");
                //Als het woord geraden is, maak een nieuwe ronde aan en voeg deze toe aan de game.
                //Voeg aan de hand van de vorige lengte van het word van de round een nieuw word aan de nieuwe round toe.
                if(lastRoundWord.getLength() == 5) {
                    word = new Word(wordService.provideRandomWord(6));
                }
                else if(lastRoundWord.getLength() == 6) {
                    System.out.println("Zou hier in moeten komen.");
                    word = new Word(wordService.provideRandomWord(7));
                }
                else {
                    word = new Word(wordService.provideRandomWord(5));
                }
                Round round = new Round(word, game.getRounds().size() + 1);
                System.out.println("De nieuwe ronde is aangemaakt, en dit is nu de nieuwe ronde: ");
                System.out.println(round);
                round.setGame(game);
                game.addRound(round);
                springGameRepository.save(game);
                return Optional.of(game);
            }
            //Als het woord niet geraden is, dan moeten er nog turns gespeeld worden of er moet een nieuw spel gestart worden.
            System.out.println("Retourneerd geen optional");
            return Optional.empty();
        }
        //Als er nog geen rondes in het spel zijn, voeg dan een round toe.
        word = new Word(wordService.provideRandomWord(5));
        Round round = new Round(word, game.getRounds().size() + 1);
        round.setGame(game);
        game.addRound(round);

        springGameRepository.save(game);
        return Optional.of(game);
    }

    public Optional<Game> getGameById(int id) {
        Optional<Game> optionalGame = springGameRepository.findById(id);
        return optionalGame;
    }
}