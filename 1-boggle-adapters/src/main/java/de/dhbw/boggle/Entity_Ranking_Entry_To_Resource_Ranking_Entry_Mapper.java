package de.dhbw.boggle;

import de.dhbw.boggle.entities.Entity_Ranking_Entry;

public class Entity_Ranking_Entry_To_Resource_Ranking_Entry_Mapper {
    public Resource_Ranking_Entry map(Entity_Ranking_Entry ranking_entry) {
        return new Resource_Ranking_Entry(ranking_entry.getPlayerName(), ranking_entry.getScoredPoints().getPoints(), ranking_entry.getFieldSize().getSize(), ranking_entry.getDate().getDateSting());
    }
}
