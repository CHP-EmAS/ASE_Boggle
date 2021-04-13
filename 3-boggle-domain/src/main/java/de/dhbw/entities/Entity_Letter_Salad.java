package de.dhbw.entities;

import de.dhbw.valueobjects.VO_Dice;
import de.dhbw.valueobjects.VO_Dice_Side;

import java.util.*;

public class Entity_Letter_Salad {
    private final String uuid;

    private VO_Dice_Side[][] diceSideMatrix;
    private final int matrixSize;

    private boolean theDicesAreCast = false;

    private final Random randomGenerator;

    public Entity_Letter_Salad(int matrixSize) {
        if(isValidFieldSize(matrixSize)) {
            this.matrixSize = matrixSize;
            diceSideMatrix = new VO_Dice_Side[matrixSize][matrixSize];

            this.uuid = UUID.randomUUID().toString();

            randomGenerator = new Random();
        } else {
            throw new IllegalArgumentException("Matrix size for the letter salad must be greater than 0: Given size was: " + matrixSize);
        }
    }

    public VO_Dice_Side[][] getDiceSideMatrix() {
        return this.diceSideMatrix;
    }

    public int getMatrixSize() {
        return this.matrixSize;
    }

    public boolean haveTheDicesBeenCast() {
        return this.theDicesAreCast;
    }

    public String getId() {
        return this.uuid;
    }

    public void throwTheDices(VO_Dice[] dices) {
        if(dices.length != this.matrixSize * this.matrixSize)
            throw new RuntimeException("Number of dices must correspond to the number of matrix elements in the letter salad! There were passed " + dices.length + " dices to a " + this.matrixSize + "x" + this.matrixSize + " letter salad.");

        List<VO_Dice> diceList = Arrays.asList(dices);

        for(int x = 0; x < matrixSize; x++) {
            for(int y = 0; y < matrixSize; y++) {
                int randomDiceIndex = randomGenerator.nextInt(diceList.size());
                VO_Dice randomDice = diceList.get(randomDiceIndex);

                diceSideMatrix[x][y] = randomDice.getRandomDiceSide();

                diceList.remove(randomDiceIndex);
            }
        }

        theDicesAreCast = true;
    }

    private boolean isValidFieldSize(int matrixSize) {
        return matrixSize > 0;
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
