package de.dhbw.valueobjects;

import java.util.Objects;

public class VO_Dice_Side {
    private static long idCounter = 0;
    private final long id;

    private final char letter;

    public VO_Dice_Side(char letter){
        if(isValid(letter)) {
            this.letter = letter;
            this.id = idCounter++;
        } else {
            throw new IllegalArgumentException("Char '" + letter + "' is invalid for a dice letter!");
        }
    }

    public char getLetter() {
        return this.letter;
    }

    public long getId() {
        return this.id;
    }

    private boolean isValid(char letter) {
        return (letter >= 'A' && letter <= 'Z');
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VO_Dice_Side vo_dice_side) {
            return this.letter == vo_dice_side.getLetter();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.letter);
    }
}
