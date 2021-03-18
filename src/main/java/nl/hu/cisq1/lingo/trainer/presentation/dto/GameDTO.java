package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class GameDTO {
    private int id;
    private Person person;
    private int score;
    private Set<RoundDTO> roundsDTOS = new LinkedHashSet<>();

    public GameDTO(int id, int score, Person person, Set<RoundDTO> roundDTOSet) {
        this.id = id;
        this.score = score;
        this.person = person;
    }

    public static GameDTO createGameDTO(Game game) {
        Set<Round> rounds = game.getRounds();
        Set<RoundDTO> roundDTOSet = new LinkedHashSet<>();
        for(Round round : rounds) {
            roundDTOSet.add(RoundDTO.createRoundDTO(round));
        }
        GameDTO gameDTO = new GameDTO(game.getId(), game.getScore(), game.getPerson(), roundDTOSet);
        return gameDTO;
    }

    public int getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public int getScore() {
        return score;
    }

    public Set<RoundDTO> getRoundsDTOS() {
        return roundsDTOS;
    }
}
