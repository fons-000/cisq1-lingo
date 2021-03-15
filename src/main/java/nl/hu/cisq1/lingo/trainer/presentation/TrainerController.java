//package nl.hu.cisq1.lingo.trainer.presentation;
//
//import lombok.RequiredArgsConstructor;
//import nl.hu.cisq1.lingo.trainer.application.TrainerService;
//import nl.hu.cisq1.lingo.trainer.domain.Account;
//import nl.hu.cisq1.lingo.trainer.domain.Game;
//import nl.hu.cisq1.lingo.trainer.domain.exception.ApiRequestException;
//import nl.hu.cisq1.lingo.trainer.presentation.dto.GameDTO;
//import nl.hu.cisq1.lingo.words.application.WordService;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.HttpServerErrorException;
//
//import javax.swing.text.html.Option;
//
//import static org.graalvm.compiler.phases.common.DeadCodeEliminationPhase.Optionality.Optional;
//
//@RestController
//@RequestMapping("trainer")
//@RequiredArgsConstructor
//public class TrainerController {
//    private final TrainerService trainerService;
//
//    @PostMapping("/games")
//    public GameDTO startNewGame() {
//        try {
//            //Later wordt authentican gebruikt, voor nu even achterwege laten
//            Game game = trainerService.startNewGame();
//            GameDTO gameDTO = GameDTO.createGameDTO(game);
//            return gameDTO;
//        } catch (NullPointerException nullPointerException) {
//            throw new ApiRequestException("Couldn't create game object, returns an empty object.");
//        } catch (HttpServerErrorException.InternalServerError internalServerError) {
//            throw new ApiRequestException("Couldn't create game!", HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            throw new ApiRequestException("Error!");
//        }
//    }
//
//    @PostMapping("/games/{id}")
//    public GameDTO startNewRound(Authentication authentication, @RequestBody int id) {
//        //Later wordt authentication + id gebruikt, voor nu even achterwege laten.
//        try {
//            Game game = trainerService.getGameById(id);
//            Game game = trainerService.startNewRound();
//            GameDTO gameDTO = GameDTO.createGameDTO(game);
//            return gameDTO;
//        } catch (NullPointerException nullPointerException) {
//            throw new ApiRequestException("Kon geen nieuwe ronde aanmaken, retournd een leeg spel");
//        } catch (HttpServerErrorException.InternalServerError internalServerError) {
//            throw new ApiRequestException("Spel kon niet goed gestart worden!", HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            throw new ApiRequestException("Error!");
//        }
//    }
//
//
//}
