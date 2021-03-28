package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.*;

import java.util.ArrayList;

public class RoundDTO {
    private int roundOfGame;
    private Word firstHint;
    private ArrayList<TurnDTO> turnDTOS;

    public RoundDTO(int roundOfGame, Word firstHint, ArrayList<TurnDTO> turnDTOS) {
        this.roundOfGame = roundOfGame;
        this.firstHint = firstHint;
        this.turnDTOS = turnDTOS;
    }

    public static RoundDTO createRoundDTO(Round round) {
        int roundOfGame = round.getRoundOfGame();
        Word firstHint = round.getFirstHint();
        ArrayList<TurnDTO> turnDTOS = new ArrayList<TurnDTO>();
        for(Turn turn : round.getTurns()) {
            TurnDTO turnDTO = new TurnDTO(turn);
            turnDTOS.add(turnDTO);
        }
        RoundDTO roundDTO = new RoundDTO(roundOfGame, firstHint, turnDTOS);
        return roundDTO;
    }

    public int getRoundOfGame() {
        return roundOfGame;
    }

    public Word getFirstHint() {
        return firstHint;
    }

    public ArrayList<TurnDTO> getTurnDTOS() {
        return turnDTOS;
    }

    @Override
    public String toString() {
        return "RoundDTO{" +
                "roundOfGame=" + roundOfGame +
                ", firstHint=" + firstHint +
                ", turnDTOS=" + turnDTOS +
                '}';
    }
}