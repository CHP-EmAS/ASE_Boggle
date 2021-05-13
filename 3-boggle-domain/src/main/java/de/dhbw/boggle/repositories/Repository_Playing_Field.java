package de.dhbw.boggle.repositories;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.entities.Entity_Player;
import de.dhbw.boggle.value_objects.VO_Dice;
import de.dhbw.boggle.value_objects.VO_Field_Size;

import java.util.List;

public interface Repository_Playing_Field {

    Aggregate_Playing_Field addPlayingField(VO_Dice[] dices, VO_Field_Size playingFieldSize, Entity_Player assignedPlayer);

    Aggregate_Playing_Field getPlayingFieldByID(String uuid);

    List<Aggregate_Playing_Field> getAllPlayingFields();

}
