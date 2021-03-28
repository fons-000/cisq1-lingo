package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
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

//    @Test
//    @DisplayName("Always starts a new game")
//    void startNewRound() throws Exception {
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/trainer/games/1/round");
//        mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.score", is(100)))
//                .andExpect(jsonPath("$.roundsDTOS", hasSize(1)))
//                .andExpect(jsonPath("$.roundsDTOS[0].roundOfGame", is(1)))
//                .andExpect(jsonPath("$.roundsDTOS[0].firstHint.length", is(1)))
//                .andExpect(jsonPath("$.person.name", is("ControllerPerson")))
//                .andExpect(jsonPath("$.person.account.password", is("897521")))
//                .andExpect(jsonPath("$.person.account.name", is("ControllerTester001")))
//                .andExpect(jsonPath("$.person.role", is("PLAYER")));
//    }
}