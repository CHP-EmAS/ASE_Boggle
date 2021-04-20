package de.dhbw.boggle.entities;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.valueobjects.VO_Points;
import de.dhbw.boggle.valueobjects.VO_Word;

import java.util.Objects;

public class Entity_Player_Guess {

    public enum Guess_Flag{
        NOT_EXAMINED,
        EXAMINED_WRONG,
        EXAMINED_CORRECT
    }

    private final long id;
    private static long idCounter;

    private final VO_Word word;
    private final Aggregate_Playing_Field assignedPlayingField;
    private final Entity_Player assignedPlayer;

    private VO_Points calculatedPoints;
    private Guess_Flag guessFlag;

    public Entity_Player_Guess(VO_Word word, Aggregate_Playing_Field assignedPlayingField, Entity_Player assignedPlayer) {

        this.assignedPlayingField = assignedPlayingField;
        this.assignedPlayer = assignedPlayer;
        this.word = word;

        guessFlag = Guess_Flag.NOT_EXAMINED;
        this.id = idCounter++;
    }

    public void setCorrect(VO_Points points) {
        if(guessFlag != Guess_Flag.NOT_EXAMINED)
            throw new RuntimeException("Guesses that have already been examined cannot be examined again!");

        guessFlag = Guess_Flag.EXAMINED_CORRECT;
        calculatedPoints = points;
    }

    public void setWrong() {
        if(guessFlag != Guess_Flag.NOT_EXAMINED)
            throw new RuntimeException("Guesses that have already been examined cannot be examined again!");

        guessFlag = Guess_Flag.EXAMINED_WRONG;
        calculatedPoints = new VO_Points(0);
    }

    public VO_Points getPoints() {
        if(guessFlag == Guess_Flag.NOT_EXAMINED)
            throw new RuntimeException("Points of a player Guess were requested, but they were not calculated yet!");

        return calculatedPoints;
    }

    public Guess_Flag getGuessFlag() {
        return guessFlag;
    }

    public Aggregate_Playing_Field getAssignedPlayingField() {
        return this.assignedPlayingField;
    }

    public Entity_Player getAssignedPlayer() {
        return this.assignedPlayer;
    }

    public VO_Word getWord() {
        return this.word;
    }

    public long getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entity_Player_Guess guess) {
            return (this.getId() == guess.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
