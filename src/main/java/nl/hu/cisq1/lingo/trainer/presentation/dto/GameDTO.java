package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class GameDTO {
    private int id;
    private PersonDTO personDTO;
    private int score;
    private Set<RoundDTO> roundsDTOS;

    public GameDTO(int id, int score, PersonDTO personDTO, Set<RoundDTO> roundDTOSet) {
        this.id = id;
        this.score = score;
        this.personDTO = personDTO;
        this.roundsDTOS = roundDTOSet;
    }

    public static GameDTO createGameDTO(Game game) {
        Set<Round> rounds = game.getRounds();
        Set<RoundDTO> roundDTOSet = new LinkedHashSet<>();
        for(Round round : rounds) {
            RoundDTO roundDTO = RoundDTO.createRoundDTO(round);
            roundDTOSet.add(roundDTO);
        }
        GameDTO gameDTO = new GameDTO(game.getId(), game.getScore(), PersonDTO.createPersonDTO(game.getPerson()), roundDTOSet);
        return gameDTO;
    }

    public int getId() {
        return id;
    }

    public PersonDTO getPerson() {
        return personDTO;
    }

    public int getScore() {
        return score;
    }

    public Set<RoundDTO> getRoundsDTOS() {
        return roundsDTOS;
    }
}