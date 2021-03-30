package nl.hu.cisq1.lingo.trainer.presentation;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.ApiRequestException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GameDTO;
import nl.hu.cisq1.lingo.trainer.presentation.dto.RoundDTO;
import nl.hu.cisq1.lingo.trainer.presentation.dto.TurnDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.swing.text.html.Option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;

    @PostMapping("/games")
    public GameDTO startNewGame() {
        try {
            //Voor nu laten we het gebruik van authenticatie achterwege.
            Person person = new Person("ControllerTester001", "897521", "ControllerPerson", Role.PLAYER);
            Game game = trainerService.startNewGame(person);
            GameDTO gameDTO = GameDTO.createGameDTO(game);
            return gameDTO;
        } catch (Exception e) {
            throw new ApiRequestException("Error!");
        }
    }

    @PatchMapping("/games/{id}/round")
    public GameDTO startNewRound(@PathVariable String id) {
        //Later wordt authentication + id gebruikt, voor nu even achterwege laten.
        try {
            int idNumber = Integer.parseInt(id);
            Optional<Game> optionalGame = trainerService.getGameById(idNumber);
            Game game = optionalGame.orElseThrow();
            Optional<Game> newOptionalGame = trainerService.startNewRound(game);
            Game newGame = newOptionalGame.orElseThrow();
            ArrayList<Round> rounds = new ArrayList<>(newGame.getRounds());
            //Verander deze arraylist round
            Round lastRound = rounds.get(rounds.size() - 1);
            Word firstHint = Hint.createValidHint(String.valueOf(lastRound.getWord().getValue().charAt(0)));
            lastRound.setFirstHint(firstHint);

            //Set nu de arraylist als newGame property
            newGame.setRounds(rounds);

            GameDTO gameDTO = GameDTO.createGameDTO(newGame);
            return gameDTO;
        } catch (NoSuchElementException noSuchElementException) {
            throw new ApiRequestException("Given game does not exist!", HttpStatus.NOT_FOUND);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ApiRequestException("Finish the last round of the game first!", HttpStatus.FORBIDDEN);
        } catch (NumberFormatException numberFormatException) {
            throw new ApiRequestException("Given game does not exist, enter a valid id!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ApiRequestException("Error!");
        }
    }

    @PatchMapping("/games/{id}/guess")
    public GameDTO guess(@PathVariable int id, @RequestBody String guess) {
        //Later wordt authentication + id gebruikt, voor nu even achterwege laten.
        try {
            Optional<Game> optionalGame = trainerService.getGameById(id);
            Game game = optionalGame.orElseThrow();
            System.out.println("This is the game from the DB: ");
            System.out.println(game.showGame());
            Optional<Game> newOptionalGame = trainerService.guess(game, new Guess(guess));
            Game newGame = newOptionalGame.orElseThrow();
            ArrayList<Round> rounds = new ArrayList<>(newGame.getRounds());
            Collections.sort(rounds);
            newGame.setRounds(rounds);
            GameDTO gameDTO = GameDTO.createGameDTO(newGame);
            return gameDTO;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

//    catch (NoSuchElementException noSuchElementException) {
//        throw new ApiRequestException("1. Given game doesn't exist, 2. word has already been guessed/there are no rounds - so start new round.");
//    }

//    @RequestMapping("/games/{id}")
//    public GameDTO getGameById(@PathVariable int id) {
//        //Later wordt authentication + id gebruikt, voor nu even achterwege laten.
//        try {
//            Optional<Game> optionalGame = trainerService.getGameById(id);
//            Game game = optionalGame.orElseThrow();
//            GameDTO gameDTO = GameDTO.createGameDTO(game);
//            return gameDTO;
//        } catch (NoSuchElementException noSuchElementException) {
//            throw new ApiRequestException("Given game does not exist!", HttpStatus.NOT_FOUND);
//        } catch (NumberFormatException numberFormatException) {
//            throw new ApiRequestException("Given game does not exist, enter a valid id!", HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            System.out.println(e);
//            throw e;
//        }
//    }
}