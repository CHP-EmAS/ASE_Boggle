package de.dhbw.boggle.entities;

import de.dhbw.boggle.valueobjects.VO_Date;
import de.dhbw.boggle.valueobjects.VO_Field_Size;
import de.dhbw.boggle.valueobjects.VO_Points;

import java.util.Objects;
import java.util.UUID;

public class Entity_Ranking_Entry {

    private final String uuid;

    private final String playerName;
    private final VO_Points scored_points;
    private final VO_Field_Size fieldSize;
    private final VO_Date date;

    public Entity_Ranking_Entry(String playerName, VO_Points scored_points, VO_Field_Size fieldSize, VO_Date date) {

        this.playerName = playerName;
        this.scored_points = scored_points;
        this.fieldSize = fieldSize;
        this.date = date;

        this.uuid = UUID.randomUUID().toString();
    }

    public VO_Points getScoredPoints() {
        return this.scored_points;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public VO_Field_Size getFieldSize() {
        return this.fieldSize;
    }

    public VO_Date getDate() {
        return this.date;
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
