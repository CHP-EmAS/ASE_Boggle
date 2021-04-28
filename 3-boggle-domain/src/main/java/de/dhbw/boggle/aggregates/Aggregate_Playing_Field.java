package de.dhbw.boggle.aggregates;

import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.value_objects.VO_Dice;
import de.dhbw.boggle.value_objects.VO_Field_Size;
import de.dhbw.boggle.entities.Entity_Letter_Salad;

import java.util.Objects;
import java.util.UUID;

public class Aggregate_Playing_Field {

    private final String uuid;

    private final VO_Field_Size playingFieldSize;
    private final Entity_Letter_Salad letterSalad;
    private final Entity_Player assignedPlayer;

    public Aggregate_Playing_Field(VO_Dice[] dices, VO_Field_Size playingFieldSize, Entity_Player assignedPlayer) {
        if(dices.length != (playingFieldSize.getSize() * playingFieldSize.getSize())) {
            throw new IllegalArgumentException("Number of dices must correspond to the number of field elements of the playing Field! There were passed " + dices.length + " dices to a " + playingFieldSize.getSize() + "x" + playingFieldSize.getSize() + " playing field.");
        }

        this.playingFieldSize = playingFieldSize;

        this.assignedPlayer = assignedPlayer;

        this.letterSalad = new Entity_Letter_Salad(this.playingFieldSize);
        this.letterSalad.throwTheDices(dices);

        this.uuid = UUID.randomUUID().toString();
    }

    public Entity_Letter_Salad getLetterSalad() {
        return this.letterSalad;
    }

    public Entity_Player getAssignedPlayer() {
        return this.assignedPlayer;
    }

    public VO_Field_Size getPlayingFieldSize() {
        return this.playingFieldSize;
    }

    public String getId() { return this.uuid; }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Aggregate_Playing_Field playingField) {
            return this.getId().equals(playingField.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid);
    }
}
