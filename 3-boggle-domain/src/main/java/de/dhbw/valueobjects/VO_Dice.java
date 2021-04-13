package de.dhbw.valueobjects;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class VO_Dice {
    private static long idCounter = 0;
    private final long id;

    private final VO_Dice_Side[] diceSides = new VO_Dice_Side[6];

    private final Random randomGenerator;

    public VO_Dice(VO_Dice_Side[] diceSides){
        if(isValid(diceSides)) {
            System.arraycopy(diceSides, 0, this.diceSides, 0, 6);
            this.id = idCounter++;
            randomGenerator = new Random();
        } else {
            throw new IllegalArgumentException("Dice side Array must have a length of 6! Length: " + diceSides.length);
        }
    }

    public VO_Dice_Side getRandomDiceSide() {
        return diceSides[randomGenerator.nextInt(6)];
    }

    public VO_Dice_Side[] getAllDiceSides() {
        return this.diceSides;
    }

    public long getId() {
        return this.id;
    }

    private boolean isValid(VO_Dice_Side[] diceSides) {
        return diceSides.length == 6;
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
