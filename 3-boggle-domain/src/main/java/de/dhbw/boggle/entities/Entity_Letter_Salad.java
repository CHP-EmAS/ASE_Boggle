package de.dhbw.boggle.entities;

import de.dhbw.boggle.valueobjects.VO_Dice;
import de.dhbw.boggle.valueobjects.VO_Dice_Side;
import de.dhbw.boggle.valueobjects.VO_Field_Size;

import java.util.*;

public class Entity_Letter_Salad {
    private final String uuid;

    private final VO_Dice_Side[][] diceSideMatrix;

    private final VO_Field_Size fieldSize;

    private boolean theDicesAreCast = false;

    private final Random randomGenerator;

    public Entity_Letter_Salad(VO_Field_Size fieldSize) {

        this.fieldSize = fieldSize;
        diceSideMatrix = new VO_Dice_Side[ this.fieldSize.getSize() ][ this.fieldSize.getSize() ];

        this.uuid = UUID.randomUUID().toString();
        randomGenerator = new Random();

    }

    public VO_Dice_Side[][] getDiceSideMatrix() {
        return this.diceSideMatrix;
    }

    public boolean haveTheDicesBeenCast() {
        return this.theDicesAreCast;
    }

    public VO_Field_Size getMatrixSize() {
        return fieldSize;
    }

    public String getId() {
        return this.uuid;
    }

    public void throwTheDices(VO_Dice[] dices) {
        if(dices.length != this.fieldSize.getSize() * this.fieldSize.getSize())
            throw new RuntimeException("Number of dices must correspond to the number of matrix elements in the letter salad! There were passed " + dices.length + " dices to a " + this.fieldSize.getSize() + "x" + this.fieldSize.getSize() + " letter salad.");



        LinkedList<VO_Dice> diceList = new LinkedList<>(Arrays.asList(Arrays.copyOf(dices, dices.length)));

        for(int i = 0; i < this.fieldSize.getSize(); i++) {
            for(int j = 0; j < this.fieldSize.getSize(); j++) {
                int randomDiceIndex = randomGenerator.nextInt(diceList.size());
                VO_Dice randomDice = diceList.get(randomDiceIndex);

                diceSideMatrix[i][j] = randomDice.getRandomDiceSide();

                diceList.remove(randomDiceIndex);
            }
        }

        theDicesAreCast = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entity_Letter_Salad letter_salad) {
            return this.getId().equals(letter_salad.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid);
    }
}
