package nl.hu.cisq1.lingo.trainer.presentation;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.ApiRequestException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GameDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.swing.text.html.Option;

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
        } catch (HttpServerErrorException.InternalServerError internalServerError) {
            throw new ApiRequestException("Couldn't create game!", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new ApiRequestException("Error!");
        }
    }

//    @PostMapping("/games/{id}/round")
//    public GameDTO startNewRound(@PathVariable int id) {
//        //Later wordt authentication + id gebruikt, voor nu even achterwege laten.
//        try {
//            Optional<Game> optionalGame = trainerService.getGameById(id);
//            Game game = optionalGame.orElseThrow();
//            Optional<Game> newOptionalGame = trainerService.startNewRound(game);
//            Game newGame = newOptionalGame.orElseThrow();
//            GameDTO gameDTO = GameDTO.createGameDTO(newGame);
//            return gameDTO;
//        } catch (NoSuchElementException noSuchElementException) {
//            throw new ApiRequestException("Opgegeven game bestaat niet!");
//        } catch (HttpServerErrorException.InternalServerError internalServerError) {
//            throw new ApiRequestException("Spel kon niet goed gestart worden!", HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            throw new ApiRequestException("Error!");
//        }
//    }
//
//    @PostMapping("/games/{id}")
//    public GameDTO guess(@PathVariable int id, @RequestBody String guess) {
//        //Later wordt authentication + id gebruikt, voor nu even achterwege laten.
//        try {
//            Optional<Game> optionalGame = trainerService.getGameById(id);
//            Game game = optionalGame.orElseThrow();
//
//            Optional<Game> newOptionalGame = trainerService.guess(game, new Guess(guess));
//            Game newGame = newOptionalGame.orElseThrow();
//            GameDTO gameDTO = GameDTO.createGameDTO(newGame);
//            return gameDTO;
//        } catch (NoSuchElementException noSuchElementException) {
//            throw new ApiRequestException("Opgegeven game bestaat niet!");
//        } catch (HttpServerErrorException.InternalServerError internalServerError) {
//            throw new ApiRequestException("Spel kon niet goed gestart worden!", HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            throw new ApiRequestException("Error!");
//        }
//    }
//
//    @PostMapping("/games/{id}")
//    public GameDTO getGameById(@PathVariable int id) {
//        //Later wordt authentication + id gebruikt, voor nu even achterwege laten.
//        try {
//            Optional<Game> optionalGame = trainerService.getGameById(id);
//            Game game = optionalGame.orElseThrow();
//            GameDTO gameDTO = GameDTO.createGameDTO(game);
//            return gameDTO;
//        } catch (NoSuchElementException noSuchElementException) {
//            throw new ApiRequestException("Opgegeven game bestaat niet!");
//        } catch (HttpServerErrorException.InternalServerError internalServerError) {
//            throw new ApiRequestException("Spel kon niet goed gestart worden!", HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            throw new ApiRequestException("Error!");
//        }
//    }

//    @DeleteMapping("/games/{id}")
//    public void verwijder(@PathVariable int id) {
//        try {
//            Optional<Game> optionalGame = trainerService.getGameById(id);
//            Game game = optionalGame.orElseThrow();
//            trainerService.deleteGame(game);
//        } catch (NoSuchElementException noSuchElementException) {
//            throw new ApiRequestException("Opgegeven game bestaat niet!");
//        }  catch (HttpServerErrorException.InternalServerError internalServerError) {
//            throw new ApiRequestException("Spel kon niet goed gestart worden!", HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            throw new ApiRequestException("Error!");
//        }
//    }
}