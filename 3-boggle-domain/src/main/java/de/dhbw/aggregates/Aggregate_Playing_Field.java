package de.dhbw.aggregates;

import de.dhbw.entities.Entity_Letter_Salad;
import de.dhbw.valueobjects.VO_Dice;
import de.dhbw.valueobjects.VO_Dice_Side;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Aggregate_Playing_Field {

    private final String uuid;

    private VO_Dice[] dices;
    private final short playingFieldSize;

    private Entity_Letter_Salad letterSalad;

    public Aggregate_Playing_Field(VO_Dice[] dices, short playingFieldSize) {
        if(dices.length != (playingFieldSize * playingFieldSize)) {
            throw new IllegalArgumentException("Number of dices must correspond to the number of field elements of the playing Field! There were passed " + dices.length + " dices to a " + playingFieldSize + "x" + playingFieldSize + " playing field.");
        }

        this.dices = dices;
        this.playingFieldSize = playingFieldSize;

        this.letterSalad = new Entity_Letter_Salad(this.playingFieldSize);
        this.letterSalad.throwTheDices(this.dices);

        this.uuid = UUID.randomUUID().toString();
    }

    public VO_Dice_Side[][] getCurrentLetterSalad() {
        return letterSalad.getDiceSideMatrix();
    }

    public void generateNewLetterSalad() {
        this.letterSalad.throwTheDices(this.dices);
    }

    public void changeDices(VO_Dice[] newDices) {
        if(newDices.length != (this.playingFieldSize * playingFieldSize)) {
            throw new IllegalArgumentException("Number of dices must correspond to the number of field elements of the playing Field! There were passed " + dices.length + " dices to a " + playingFieldSize + "x" + playingFieldSize + " playing field.");
        }

        this.dices = newDices;
    }
}
