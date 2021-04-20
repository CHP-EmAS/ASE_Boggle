package de.dhbw.boggle.repository_bridges;

import de.dhbw.boggle.aggregates.Aggregate_Playing_Field;
import de.dhbw.boggle.repositories.Repository_Playing_Field;

import java.util.ArrayList;
import java.util.List;

public class Repository_Bridge_Playing_Field implements Repository_Playing_Field {

    private final List<Aggregate_Playing_Field> playingFields = new ArrayList<Aggregate_Playing_Field>();

    @Override
    public void addPlayingField(Aggregate_Playing_Field playingField) {
        playingFields.add(playingField);
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
