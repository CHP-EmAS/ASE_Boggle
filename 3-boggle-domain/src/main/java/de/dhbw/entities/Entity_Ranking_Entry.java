package de.dhbw.entities;

import java.util.Objects;
import java.util.UUID;

public class Entity_Ranking_Entry {

    private final String uuid;

    private final String name;
    private final int scored_points;

    public Entity_Ranking_Entry(String name, int scored_points) {
        this.name = name;
        this.scored_points = scored_points;
        this.uuid = UUID.randomUUID().toString();
    }

    public int getScoredPoints() {
        return this.scored_points;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entity_Ranking_Entry ranking_entry) {
            return this.uuid.equals(ranking_entry.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid);
    }
}
