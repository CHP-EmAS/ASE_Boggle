package de.dhbw.boggle.entities;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.valueobjects.VO_Matrix_Index_Pair;
import de.dhbw.boggle.valueobjects.VO_Points;
import de.dhbw.boggle.valueobjects.VO_Word;

import java.util.*;

public class Entity_Player_Guess {

    public enum Guess_Flag{
        NOT_EXAMINED,
        EXAMINED_IMPOSSIBLE,
        EXAMINED_WRONG,
        EXAMINED_CORRECT
    }

    private final long id;
    private static long idCounter;

    private final Aggregate_Playing_Field assignedPlayingField;

    private final VO_Word word;

    private VO_Points calculatedPoints;
    private Guess_Flag guessFlag;
    private List<VO_Matrix_Index_Pair> usedLetterList;

    public Entity_Player_Guess(VO_Word word, Aggregate_Playing_Field assignedPlayingField) {
        this.word = word;

        this.assignedPlayingField = assignedPlayingField;

        guessFlag = Guess_Flag.NOT_EXAMINED;
        this.id = idCounter++;
    }

    public void setCorrect(VO_Points points, List<VO_Matrix_Index_Pair> usedLetterList) {
        if(guessFlag != Guess_Flag.NOT_EXAMINED)
            throw new RuntimeException("Guesses that have already been examined cannot be examined again!");

        guessFlag = Guess_Flag.EXAMINED_CORRECT;
        this.usedLetterList = usedLetterList;

        calculatedPoints = points;
    }

    public void setWrong(List<VO_Matrix_Index_Pair> usedLetterList) {
        if(guessFlag != Guess_Flag.NOT_EXAMINED)
            throw new RuntimeException("Guesses that have already been examined cannot be examined again!");

        guessFlag = Guess_Flag.EXAMINED_WRONG;
        this.usedLetterList = usedLetterList;

        calculatedPoints = new VO_Points(0);
    }

    public void setImpossible() {
        if(guessFlag != Guess_Flag.NOT_EXAMINED)
            throw new RuntimeException("Guesses that have already been examined cannot be examined again!");

        guessFlag = Guess_Flag.EXAMINED_IMPOSSIBLE;
        this.usedLetterList = new ArrayList<>();

        calculatedPoints = new VO_Points(0);
    }

    public VO_Points getPoints() {
        if(guessFlag == Guess_Flag.NOT_EXAMINED)
            throw new RuntimeException("Points of a player Guess were requested, but they were not calculated yet!");

        return calculatedPoints;
    }

    public List<VO_Matrix_Index_Pair> getUsedLetterList() {
        if(guessFlag == Guess_Flag.NOT_EXAMINED)
            throw new RuntimeException("Points of a player Guess were requested, but they were not calculated yet!");

        return List.copyOf(this.usedLetterList);
    }

    public Aggregate_Playing_Field getAssignedPlayingField() {
        return assignedPlayingField;
    }

    public Guess_Flag getGuessFlag() {
        return guessFlag;
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
