package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This test is a controller integration test as it
 * integrates between all layers and the framework.
 * In a dev environment, we test against the actual database.
 *
 * In continuous integration pipelines, we should not
 * use the actual database as we don't have one.
 * We want to replace it with an in-memory database.
 *
 * Set the profile to CI, so that application-ci.properties is loaded
 * and an import script is run.
 */
@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
@Sql({"/data/lingo_words.sql"})
class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Always starts a new game")
    void startNewGame() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/trainer/games");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score", is(100)))
                .andExpect(jsonPath("$.roundsDTOS", hasSize(1)))
                .andExpect(jsonPath("$.roundsDTOS[0].roundOfGame", is(1)))
                .andExpect(jsonPath("$.roundsDTOS[0].firstHint.length", is(1)))
                .andExpect(jsonPath("$.person.name", is("ControllerPerson")))
                .andExpect(jsonPath("$.person.account.password", is("897521")))
                .andExpect(jsonPath("$.person.account.name", is("ControllerTester001")))
                .andExpect(jsonPath("$.person.role", is("PLAYER")));
    }

    //Later wanneer de guess werkt, kijk ook of het toevoegen vaan een round werkt als er een afgeronde ronde inzit.
    @Test
    @DisplayName("startNewRound: Start a new round, if the game has no rounds")
    void startNewRound1() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/trainer/games/3/round");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.score", is(300)))
                .andExpect(jsonPath("$.roundsDTOS", hasSize(1)))
                .andExpect(jsonPath("$.roundsDTOS[0].roundOfGame", is(1)))
                .andExpect(jsonPath("$.roundsDTOS[0].firstHint.length", is(1)))
                .andExpect(jsonPath("$.person.id", is(1)))
                .andExpect(jsonPath("$.person.name", is("Fons Thijssen")))
                .andExpect(jsonPath("$.person.account.id", is(1)))
                .andExpect(jsonPath("$.person.account.password", is("1234")))
                .andExpect(jsonPath("$.person.account.name", is("FS Fons")))
                .andExpect(jsonPath("$.person.role", is("PLAYER")));
    }

    @Test
    @DisplayName("startNewRound: Throws if the game doesn't exist")
    void startNewRound2() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/trainer/games/200/round");
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Given game does not exist!")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")));
    }

    @Test
    @DisplayName("startNewRound: Throws if the game doesn't exist")
    void startNewRound3() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/trainer/games/200/round");
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Given game does not exist!")))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")));
    }

    @Test
    @DisplayName("startNewRound: Throws if you should finish the last round first")
    void startNewRound4() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/trainer/games/1/round");
        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", is("Finish the last round of the game first!")))
                .andExpect(jsonPath("$.httpStatus", is("FORBIDDEN")));
    }

    @Test
    @DisplayName("startNewRound: Throws if you use a wrong id that won't cast to an int")
    void startNewRound5() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/trainer/games/489gkjg4/round");
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Given game does not exist, enter a valid id!")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @Test
    @DisplayName("startNewRound: Throws if you use a wrong id that won't cast to an int #2")
    void startNewRound6() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/trainer/games/30000000000/round");
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Given game does not exist, enter a valid id!")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @Test
    @DisplayName("guess: Take a guess in the last round with no turns yet")
    void guess1() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/trainer/games/1/guess")
                .content("YOLO");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.score", is(100)))
                .andExpect(jsonPath("$.roundsDTOS", hasSize(3)))
                .andExpect(jsonPath("$.roundsDTOS[2].roundOfGame", is(3)))
                .andExpect(jsonPath("$.roundsDTOS[2].firstHint.value", is("N")))
                .andExpect(jsonPath("$.roundsDTOS[2].firstHint.length", is(1)))
                .andExpect(jsonPath("$.roundsDTOS[2].turnDTOS", hasSize(1)))
                .andExpect(jsonPath("$.roundsDTOS[2].turnDTOS[0].feedback.feedbackItems[0]", is("INVALID")))
                .andExpect(jsonPath("$.roundsDTOS[2].turnDTOS[0].feedback.feedbackItems[1]", is("INVALID")))
                .andExpect(jsonPath("$.roundsDTOS[2].turnDTOS[0].feedback.feedbackItems[2]", is("INVALID")))
                .andExpect(jsonPath("$.roundsDTOS[2].turnDTOS[0].feedback.feedbackItems[3]", is("INVALID")))
                .andExpect(jsonPath("$.roundsDTOS[2].turnDTOS[0].hint.value", is("N")))
                .andExpect(jsonPath("$.roundsDTOS[2].turnDTOS[0].hint.length", is(1)))
                .andExpect(jsonPath("$.roundsDTOS[2].turnDTOS[0].guess.value", is("YOLO")))
                .andExpect(jsonPath("$.roundsDTOS[2].turnDTOS[0].guess.length", is(4)))
                .andExpect(jsonPath("$.person.id", is(1)))
                .andExpect(jsonPath("$.person.name", is("Fons Thijssen")))
                .andExpect(jsonPath("$.person.account.id", is(1)))
                .andExpect(jsonPath("$.person.account.password", is("1234")))
                .andExpect(jsonPath("$.person.account.name", is("FS Fons")))
                .andExpect(jsonPath("$.person.role", is("PLAYER")));
    }

    @Test
    @DisplayName("guess: Throws because a new round has to be started/has no rounds yet")
    void guess2() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/trainer/games/3/guess")
                .content("BOEM");

        mockMvc.perform(request)
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", is("1. Given game doesn't exist, 2. word has already been guessed/there are no rounds - so start new round.")))
                .andExpect(jsonPath("$.httpStatus", is("INTERNAL_SERVER_ERROR")));
    }

    //De vorige hint "VUURGOD", is evenlang als het te raden Word "WEDERGA". Dit betekent dat wanneer mijn applicatie een
    //generateNextHint doet, dat het dan kijkt naar de vorige hint van de huidige turn en ziet dat deze gelijk is aan het Word;
    //wat betekent dat het al geraden is volgens de applicatie. Zonder dummy inserts van tevoren in de DB, is het ook daadwerkelijk zo dat
    //de vorige hint ook hetzelfde is als het Word van de turnl; dus de foutmelding is goed.
    @Test
    @DisplayName("guess: Throws because Word has already been guessed")
    void guess3() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/trainer/games/2/guess")
                .content("WAGGONS");

        mockMvc.perform(request)
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", is("1. Given game doesn't exist, 2. word has already been guessed/there are no rounds - so start new round.")))
                .andExpect(jsonPath("$.httpStatus", is("INTERNAL_SERVER_ERROR")));
    }

    @Test
    @DisplayName("guess: Take a guess and add a turn to the last round. 1. Has previous rounds and a previous turn, word's being guessed wrong. ")
    void guess4() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch("/trainer/games/4/guess")
                .content("BOKKEN");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                //Score was al 150 in de DB, is hetzelfde gebleven want het Word is niet geraden.
                .andExpect(jsonPath("$.score", is(150)))
                .andExpect(jsonPath("$.roundsDTOS", hasSize(2)))
                .andExpect(jsonPath("$.roundsDTOS[0].roundOfGame", is(1)))
                .andExpect(jsonPath("$.roundsDTOS[0].firstHint", is(null)))
                .andExpect(jsonPath("$.roundsDTOS[0].turnDTOS", hasSize(1)))
                .andExpect(jsonPath("$.roundsDTOS[0].turnDTOS[0].feedback.feedbackItems[0]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[0].turnDTOS[0].feedback.feedbackItems[1]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[0].turnDTOS[0].feedback.feedbackItems[2]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[0].turnDTOS[0].feedback.feedbackItems[3]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[0].turnDTOS[0].feedback.feedbackItems[4]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[0].turnDTOS[0].hint.value", is("B")))
                .andExpect(jsonPath("$.roundsDTOS[0].turnDTOS[0].hint.length", is(1)))
                .andExpect(jsonPath("$.roundsDTOS[0].turnDTOS[0].guess.value", is("BIEST")))
                .andExpect(jsonPath("$.roundsDTOS[0].turnDTOS[0].guess.length", is(5)))

                .andExpect(jsonPath("$.roundsDTOS[1].roundOfGame", is(2)))
                .andExpect(jsonPath("$.roundsDTOS[1].firstHint.value", is("B")))
                .andExpect(jsonPath("$.roundsDTOS[1].firstHint.length", is("1")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS", hasSize(2)))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[0].feedback.feedbackItems[0]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[0].feedback.feedbackItems[1]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[0].feedback.feedbackItems[2]", is("ABSENT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[0].feedback.feedbackItems[3]", is("ABSENT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[0].feedback.feedbackItems[4]", is("ABSENT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[0].feedback.feedbackItems[5]", is("PRESENT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[0].hint.value", is("B")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[0].hint.length", is(1)))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[0].guess.value", is("BOARDS")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[0].guess.length", is(6)))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[1].feedback.feedbackItems[0]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[1].feedback.feedbackItems[1]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[1].feedback.feedbackItems[2]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[1].feedback.feedbackItems[3]", is("ABSENT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[1].feedback.feedbackItems[4]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[1].feedback.feedbackItems[5]", is("CORRECT")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[1].hint.value", is("BO....")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[1].hint.length", is(6)))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[1].guess.value", is("BOKKEN")))
                .andExpect(jsonPath("$.roundsDTOS[1].turnDTOS[1].guess.length", is(6)))

                .andExpect(jsonPath("$.person.id", is(2)))
                .andExpect(jsonPath("$.person.name", is("Fons Thijssen")))
                .andExpect(jsonPath("$.person.account.id", is(2)))
                .andExpect(jsonPath("$.person.account.password", is("5678")))
                .andExpect(jsonPath("$.person.account.name", is("fons-001")))
                .andExpect(jsonPath("$.person.role", is("ADMINISTRATOR")));
    }

//    @Test
//    @DisplayName("Start a new round, if the game has no rounds")
//    void startNewRound1() throws Exception {
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/trainer/games/3/round");
//        mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(3)))
//                .andExpect(jsonPath("$.score", is(300)))
//                .andExpect(jsonPath("$.roundsDTOS", hasSize(1)))
//                .andExpect(jsonPath("$.roundsDTOS[0].roundOfGame", is(1)))
//                .andExpect(jsonPath("$.roundsDTOS[0].firstHint.length", is(1)))
//                .andExpect(jsonPath("$.person.id", is(1)))
//                .andExpect(jsonPath("$.person.name", is("Fons Thijssen")))
//                .andExpect(jsonPath("$.person.account.id", is(1)))
//                .andExpect(jsonPath("$.person.account.password", is("1234")))
//                .andExpect(jsonPath("$.person.account.name", is("FS Fons")))
//                .andExpect(jsonPath("$.person.role", is("PLAYER")));
//    }
}