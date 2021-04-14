package de.dhbw.boggle.aggregates;

import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.valueobjects.VO_Dice;
import de.dhbw.boggle.valueobjects.VO_Dice_Side;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import de.dhbw.boggle.entities.Entity_Letter_Salad;

import java.util.UUID;

public class Aggregate_Playing_Field {

    private final String uuid;

    private final VO_Field_Size playingFieldSize;
    private final Entity_Letter_Salad letterSalad;
    private final Entity_Player player;

    private VO_Dice[] dices;

    public Aggregate_Playing_Field(VO_Dice[] dices, VO_Field_Size playingFieldSize, Entity_Player player) {
        if(dices.length != (playingFieldSize.getSize() * playingFieldSize.getSize())) {
            throw new IllegalArgumentException("Number of dices must correspond to the number of field elements of the playing Field! There were passed " + dices.length + " dices to a " + playingFieldSize.getSize() + "x" + playingFieldSize.getSize() + " playing field.");
        }

        this.dices = dices;
        this.playingFieldSize = playingFieldSize;
        this.player = player;

        this.letterSalad = new Entity_Letter_Salad(this.playingFieldSize);
        this.letterSalad.throwTheDices(this.dices);

        this.uuid = UUID.randomUUID().toString();
    }

    public Entity_Player getPlayer() { return this.player; }

    public VO_Dice_Side[][] getCurrentLetterSalad() {
        return letterSalad.getDiceSideMatrix();
    }

    public void generateNewLetterSalad() {
        this.letterSalad.throwTheDices(this.dices);
    }

    public void changeDices(VO_Dice[] newDices) {
        if(newDices.length != (this.playingFieldSize.getSize() * this.playingFieldSize.getSize())) {
            throw new RuntimeException("Number of dices must correspond to the number of field elements of the playing Field! There were passed " + dices.length + " dices to a " + playingFieldSize.getSize() + "x" + playingFieldSize.getSize() + " playing field.");
        }

        this.dices = newDices;
    }
}
