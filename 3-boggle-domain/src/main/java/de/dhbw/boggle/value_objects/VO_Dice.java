package de.dhbw.boggle.value_objects;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public final class VO_Dice {

    private final VO_Dice_Side[] diceSides = new VO_Dice_Side[6];

    public VO_Dice(VO_Dice_Side[] diceSides){
        if(isValid(diceSides)) {
            System.arraycopy(diceSides, 0, this.diceSides, 0, 6);
        } else {
            throw new IllegalArgumentException("Dice side Array must have a length of 6! Length: " + diceSides.length);
        }
    }

    public VO_Dice(String diceLetters){
        if(isValid(diceLetters)) {

            for(int i = 0; i < 6; i++) {
                diceSides[i] = new VO_Dice_Side(diceLetters.charAt(i));
            }

        } else {
            throw new IllegalArgumentException("Dice letter String must have a length of 6! Length: " + diceLetters.length());
        }
    }

    public VO_Dice_Side getRandomDiceSide(Random randomGenerator) {
        return diceSides[randomGenerator.nextInt(6)];
    }

    public VO_Dice_Side[] getAllDiceSides() {
        return this.diceSides;
    }

    private boolean isValid(VO_Dice_Side[] diceSides) {
        return diceSides.length == 6;
    }

    private boolean isValid(String diceLetters) {
        return diceLetters.length() == 6;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VO_Dice vo_dice) {
            return Arrays.equals(this.diceSides, vo_dice.getAllDiceSides());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash((Object) this.diceSides);
    }
}
