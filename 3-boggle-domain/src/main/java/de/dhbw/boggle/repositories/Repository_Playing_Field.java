package de.dhbw.boggle.repositories;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.entities.Entity_Player;

import java.util.List;

public interface Repository_Playing_Field {

    void addPlayingField(Aggregate_Playing_Field playingField);

    Aggregate_Playing_Field getPlayingFieldByID(String uuid);

    List<Aggregate_Playing_Field> getAllPlayingFields();
    List<Aggregate_Playing_Field> getPlayingFieldsByPlayer(Entity_Player player);

}
