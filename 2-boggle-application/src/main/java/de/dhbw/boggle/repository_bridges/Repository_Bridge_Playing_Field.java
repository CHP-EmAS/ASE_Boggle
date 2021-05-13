package de.dhbw.boggle.repository_bridges;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.repositories.Repository_Playing_Field;
import de.dhbw.boggle.value_objects.VO_Dice;
import de.dhbw.boggle.value_objects.VO_Field_Size;

import java.util.ArrayList;
import java.util.List;

public class Repository_Bridge_Playing_Field implements Repository_Playing_Field {

    private final List<Aggregate_Playing_Field> playingFields = new ArrayList<>();

    @Override
    public Aggregate_Playing_Field addPlayingField( VO_Dice[] dices, VO_Field_Size playingFieldSize, Entity_Player assignedPlayer) {

        Aggregate_Playing_Field newPlayingField = new Aggregate_Playing_Field(dices, playingFieldSize, assignedPlayer);

        playingFields.add(newPlayingField);
        return newPlayingField;
    }

    @Override
    public Aggregate_Playing_Field getPlayingFieldByID(String uuid) {
        return playingFields.stream()
                .filter(playingField -> playingField.getId().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Aggregate_Playing_Field> getAllPlayingFields() {
        return playingFields;
    }
}
